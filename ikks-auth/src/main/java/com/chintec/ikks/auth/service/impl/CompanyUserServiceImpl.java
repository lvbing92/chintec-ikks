package com.chintec.ikks.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chintec.ikks.auth.mapper.CompanyUserMapper;
import com.chintec.ikks.auth.service.ICompanyUserService;
import com.chintec.ikks.auth.service.ICredentialsService;
import com.chintec.ikks.auth.service.IUserAuthoritiesService;
import com.chintec.ikks.common.entity.CompanyUser;
import com.chintec.ikks.common.entity.Credentials;
import com.chintec.ikks.common.entity.UserAuthorities;
import com.chintec.ikks.common.entity.response.CompanyUserResponse;
import com.chintec.ikks.common.entity.vo.CompanyUserRequest;
import com.chintec.ikks.common.util.AssertsUtil;
import com.chintec.ikks.common.util.EncryptionUtil;
import com.chintec.ikks.common.util.PageResultResponse;
import com.chintec.ikks.common.util.ResultResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 公司用户表 服务实现类
 * </p>
 *
 * @author rubIn·lv
 * @since 2020-10-16
 */
@Service
public class CompanyUserServiceImpl extends ServiceImpl<CompanyUserMapper, CompanyUser>
        implements ICompanyUserService {

    @Autowired
    private IUserAuthoritiesService iUserAuthoritiesService;
    @Autowired
    private ICredentialsService iCredentialsService;

    private static final String SORT_D = "D";

    @Override
    public ResultResponse getCompanyUserList(Integer pageSize, Integer currentPage, String searchValue, String sorted, Integer departmentId) {
        if (StringUtils.isEmpty(pageSize)) {
            pageSize = 10;
        }
        if (StringUtils.isEmpty(sorted)) {
            sorted = SORT_D;
        }
        //
        IPage<CompanyUser> page = new Page<>(currentPage, pageSize);
        //分页查询
        IPage<CompanyUser> credentialsPage = this.page(page, new QueryWrapper<CompanyUser>().lambda()
                .eq(CompanyUser::getDepartmentId, departmentId)
                .and(!StringUtils.isEmpty(searchValue), s -> s.like(CompanyUser::getUserName, searchValue).
                        or().like(CompanyUser::getEmail, searchValue).
                        or().like(CompanyUser::getUserName, searchValue))
//                .orderByAsc(SORT_A.equals(sorted), Credentials::getName)
                .orderByDesc(SORT_D.equals(sorted), CompanyUser::getCreateTime));
        //根据用户Id查询角色Id
        if (credentialsPage.getRecords().size() != 0) {
            List<CompanyUserResponse> companyUserResponseList = credentialsPage.getRecords().stream().map(companyUser -> {
                CompanyUserResponse companyUserResponse = new CompanyUserResponse();
                BeanUtils.copyProperties(companyUser, companyUserResponse);
                //查询角色Id
                UserAuthorities userAuthorities = iUserAuthoritiesService.getOne(new QueryWrapper<UserAuthorities>()
                        .lambda().eq(UserAuthorities::getCompanyUserId, companyUser.getId()));
                companyUserResponse.setRoleId(userAuthorities.getAuthorityId());
                return companyUserResponse;
            }).collect(Collectors.toList());

            //返回结果
            PageResultResponse<CompanyUserResponse> paginationResultDto = new PageResultResponse<>(credentialsPage.getTotal(), currentPage, pageSize);
            paginationResultDto.setTotalPages(credentialsPage.getPages());
            paginationResultDto.setResults(companyUserResponseList);
            return ResultResponse.successResponse("查询列表成功!", paginationResultDto);
        } else {
            return ResultResponse.successResponse(new PageResultResponse<CompanyUserResponse>(0L, currentPage, pageSize));
        }

    }

    @Override
    @Transactional
    public ResultResponse addCompanyUser(CompanyUserRequest companyUserRequest) {
        boolean flag;
        CompanyUser companyUser = new CompanyUser();
        if (StringUtils.isEmpty(companyUserRequest.getId())) {
            //保存当前人员到登陆客户表
            Credentials credentials = new Credentials();
            credentials.setName(companyUser.getEmail());
            credentials.setPassword(companyUser.getPassword());
            credentials.setUserType("2");
            credentials.setEnabled(true);
            flag = iCredentialsService.addLoginMsg(credentials);

            BeanUtils.copyProperties(companyUserRequest, companyUser);
            //根据用户名和邮箱查询当前人员是否已存在
            CompanyUser isHave = this.getOne(new QueryWrapper<CompanyUser>().lambda()
                    .eq(CompanyUser::getUserName, companyUserRequest.getUserName())
                    .eq(CompanyUser::getEmail, companyUserRequest.getEmail()));
            AssertsUtil.isTrue(!ObjectUtils.isEmpty(isHave), "当前人员已存在!");
            companyUser.setLoginId(credentials.getId());
            companyUser.setCreateTime(LocalDateTime.now());
            companyUser.setPassword(EncryptionUtil.passWordEnCode(companyUserRequest.getPassword(), BCryptPasswordEncoder.class));
            flag &= this.save(companyUser);
            //保存公司人员角色信息
            UserAuthorities userAuthorities = new UserAuthorities();
            userAuthorities.setCompanyUserId(companyUser.getId());
            userAuthorities.setAuthorityId(companyUserRequest.getRoleId());
            flag &= iUserAuthoritiesService.save(userAuthorities);
            if (!flag) {
                return ResultResponse.successResponse("保存公司人员信息失败!");
            }
        } else {
            //g根据Id查询当前部门信息
            CompanyUser currentCompanyUser = this.getById(companyUserRequest.getId());

            BeanUtils.copyProperties(companyUserRequest, currentCompanyUser);
            currentCompanyUser.setPassword(EncryptionUtil.passWordEnCode(companyUserRequest.getPassword(),BCryptPasswordEncoder.class));
            currentCompanyUser.setUpdateTime(LocalDateTime.now());
            flag = this.updateById(currentCompanyUser);
            //更新角色信息
            flag &= iUserAuthoritiesService.remove(new QueryWrapper<UserAuthorities>()
                    .lambda().eq(UserAuthorities::getCompanyUserId, companyUserRequest.getId()));
            //保存公司人员角色信息
            UserAuthorities userAuthorities = new UserAuthorities();
            userAuthorities.setCompanyUserId(companyUserRequest.getId());
            userAuthorities.setAuthorityId(companyUserRequest.getRoleId());
            flag &= iUserAuthoritiesService.save(userAuthorities);

            //更新登录信息表
            Credentials credentials = new Credentials();
            credentials.setId(currentCompanyUser.getLoginId());
            credentials.setName(currentCompanyUser.getEmail());
            credentials.setPassword(currentCompanyUser.getPassword());
            flag &= iCredentialsService.updateById(credentials);
            if (!flag) {
                return ResultResponse.successResponse("更新公司人员信息失败!");
            }
        }
        return ResultResponse.successResponse("保存成功！");
    }

    @Override
    public ResultResponse updateCompanyUser(CompanyUserRequest companyUserRequest) {
        //更新公司人员信息
        CompanyUser companyUser = this.getById(companyUserRequest.getId());
        companyUser.setUserName(companyUserRequest.getUserName());
        companyUser.setEmail(companyUserRequest.getEmail());
        companyUser.setPassword(companyUserRequest.getPassword());
        boolean b = this.updateById(companyUser);
        //更新公司用户和角色关系表
        return null;
    }

    @Override
    public ResultResponse queryCompanyUser(Integer id) {
        //查询公司人员信息
        CompanyUser companyUser = this.getById(id);
        CompanyUserRequest companyUserRequest = new CompanyUserRequest();
        BeanUtils.copyProperties(companyUser, companyUserRequest);
        //根据公司Id查询角色Id
        UserAuthorities userAuthorities = iUserAuthoritiesService.getOne(new QueryWrapper<UserAuthorities>().lambda().eq(UserAuthorities::getCompanyUserId, id));
        companyUserRequest.setRoleId(userAuthorities.getAuthorityId());
        return ResultResponse.successResponse("查询成功!", companyUserRequest);
    }

    @Override
    public ResultResponse deleteCompanyUser(Integer id) {
        boolean flag = this.removeById(id);
        AssertsUtil.isTrue(!flag, "删除失败!");
        return ResultResponse.successResponse("删除成功!");
    }
}
