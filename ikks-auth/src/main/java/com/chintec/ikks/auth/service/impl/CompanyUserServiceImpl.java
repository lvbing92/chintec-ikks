package com.chintec.ikks.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chintec.ikks.auth.entity.CompanyUser;
import com.chintec.ikks.auth.entity.Department;
import com.chintec.ikks.auth.entity.UserAuthorities;
import com.chintec.ikks.auth.mapper.CompanyUserMapper;
import com.chintec.ikks.auth.request.CompanyUserRequest;
import com.chintec.ikks.auth.service.ICompanyUserService;
import com.chintec.ikks.auth.service.IUserAuthoritiesService;
import com.chintec.ikks.common.util.AssertsUtil;
import com.chintec.ikks.common.util.PageResultResponse;
import com.chintec.ikks.common.util.ResultResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 公司用户表 服务实现类
 * </p>
 *
 * @author rubIn·lv
 * @since 2020-10-16
 */
@Service
public class CompanyUserServiceImpl extends ServiceImpl<CompanyUserMapper, CompanyUser> implements ICompanyUserService, Serializable {

    @Autowired
    private IUserAuthoritiesService iUserAuthoritiesService;

    private static final String SORT_D = "D";
    @Override
    public ResultResponse getCompanyUserList(Integer pageSize, Integer currentPage, String searchValue, String sorted) {
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
                .and(!StringUtils.isEmpty(searchValue), s -> s.like(CompanyUser::getUserName, searchValue).
                        or().like(CompanyUser::getEmail, searchValue).
                        or().like(CompanyUser::getDepartmentId, searchValue).
                        or().like(CompanyUser::getUserName, searchValue))
//                .orderByAsc(SORT_A.equals(sorted), Credentials::getName)
                .orderByDesc(SORT_D.equals(sorted), CompanyUser::getCreateTime));
        //返回结果
        PageResultResponse<CompanyUser> paginationResultDto = new PageResultResponse<>(credentialsPage.getTotal(), currentPage, pageSize);
        paginationResultDto.setTotalPages(credentialsPage.getPages());
        paginationResultDto.setResults(credentialsPage.getRecords());
        return ResultResponse.successResponse("查询列表成功!",paginationResultDto);
    }

    @Override
    @Transactional
    public ResultResponse addCompanyUser(CompanyUserRequest companyUserRequest) {
        boolean flag;
        CompanyUser companyUser = new CompanyUser();
        if(StringUtils.isEmpty(companyUserRequest.getId())){
            BeanUtils.copyProperties(companyUserRequest,companyUser);
            //查询当前部门是否已存在
            CompanyUser isHave = this.getOne(new QueryWrapper<CompanyUser>().lambda()
                    .eq(CompanyUser::getUserName,companyUserRequest.getUserName()));
            AssertsUtil.isTrue(!ObjectUtils.isEmpty(isHave),"当前人员已存在!");
            companyUser.setCreateTime(LocalDateTime.now());
            flag = this.save(companyUser);
            //保存公司人员角色信息
            UserAuthorities userAuthorities = new UserAuthorities();
            userAuthorities.setCompanyUserId(companyUser.getId());
            userAuthorities.setAuthorityId(companyUserRequest.getRoleId());
             flag &= iUserAuthoritiesService.save(userAuthorities);
            if(!flag){
                return ResultResponse.successResponse("保存公司人员信息失败!");
            }
        }else{
            //g根据Id查询当前部门信息
            CompanyUser currentCompanyUser = this.getById(companyUserRequest.getId());
            BeanUtils.copyProperties(companyUserRequest,currentCompanyUser);
            currentCompanyUser.setUpdateTime(LocalDateTime.now());
            flag =this.updateById(currentCompanyUser);
            //更新角色信息
            flag &=iUserAuthoritiesService.remove(new QueryWrapper<UserAuthorities>()
                    .lambda().eq(UserAuthorities::getCompanyUserId,companyUserRequest.getId()));
            //保存公司人员角色信息
            UserAuthorities userAuthorities = new UserAuthorities();
            userAuthorities.setCompanyUserId(companyUserRequest.getId());
            userAuthorities.setAuthorityId(companyUserRequest.getRoleId());
            flag &=iUserAuthoritiesService.save(userAuthorities);
            if(!flag){
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

        return null;
    }

    @Override
    public ResultResponse deleteCompanyUser(Integer id) {
        boolean flag = this.removeById(id);
        AssertsUtil.isTrue(!flag,"删除失败!");
        return ResultResponse.successResponse("删除成功!");
    }
}
