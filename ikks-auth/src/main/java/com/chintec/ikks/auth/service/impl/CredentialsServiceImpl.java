package com.chintec.ikks.auth.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chintec.ikks.auth.feign.ISupplierService;
import com.chintec.ikks.auth.mapper.CredentialsMapper;
import com.chintec.ikks.auth.service.*;
import com.chintec.ikks.common.entity.*;
import com.chintec.ikks.common.entity.response.AuthorityMenuResponse;
import com.chintec.ikks.common.entity.response.CredentialsResponse;
import com.chintec.ikks.common.entity.vo.CredentialsRequest;
import com.chintec.ikks.common.enums.UserTypeEnum;
import com.chintec.ikks.common.util.*;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户服务实现类
 * </p>
 *
 * @author ruBIn·lv
 * @since 2020-08-26
 */
@Service
public class CredentialsServiceImpl extends ServiceImpl<CredentialsMapper, Credentials> implements ICredentialsService {

    //    private static final String SORT_A = "A";
    private static final String SORT_D = "D";
    @Autowired
    private ICredentialsAuthoritiesService iCredentialsAuthoritiesService;
    @Autowired
    private IAuthorityMenuService iAuthorityMenuService;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private IAuthorityService iAuthorityService;
    @Autowired
    private ICompanyUserService iCompanyUserService;
    @Autowired
    private IUserAuthoritiesService iUserAuthoritiesService;
    @Autowired
    private ISupplierService iSupplierService;
    @Autowired
    private IMenuFunctionService iMenuFunctionService;

    @Override
    public ResultResponse getUserList(Integer pageSize, Integer currentPage, String role, String status, String searchValue, String sorted) {
        if (StringUtils.isEmpty(pageSize)) {
            pageSize = 10;
        }
        if (StringUtils.isEmpty(sorted)) {
            sorted = SORT_D;
        }
        //
        IPage<Credentials> page = new Page<>(currentPage, pageSize);
        //分页查询
        IPage<Credentials> credentialsPage = this.page(page, new QueryWrapper<Credentials>().lambda()
                .eq(Credentials::getEnabled, 1)
//                .and(!StringUtils.isEmpty(searchValue), s -> s.like(Credentials::getUserName, searchValue).
//                        or().like(Credentials::getCellphone, searchValue).
//                        or().like(Credentials::getEmail, searchValue).
//                        or().like(Credentials::getUserName, searchValue))
//                .orderByAsc(SORT_A.equals(sorted), Credentials::getName)
                .orderByDesc(SORT_D.equals(sorted), Credentials::getCreateTime));
        //返回结果
        PageResultResponse<Credentials> paginationResultDto = new PageResultResponse<>(credentialsPage.getTotal(), currentPage, pageSize);
        paginationResultDto.setTotalPages(credentialsPage.getPages());
        paginationResultDto.setResults(credentialsPage.getRecords());
        return ResultResponse.successResponse(paginationResultDto);
    }

    @Override
    @Transactional
    public ResultResponse addUser(CredentialsRequest credentialsRequest) {
        System.out.println("当前客户信息:" + JSONObject.toJSON(credentialsRequest));
        //判断当前客户是否存在
        Credentials user = this.getOne(new QueryWrapper<Credentials>().lambda()
                .eq(Credentials::getName, credentialsRequest.getName())
                .eq(Credentials::getCompanyName, credentialsRequest.getCompanyName()));
        if (ObjectUtils.isEmpty(user)) {
            Credentials credentials = new Credentials();
            BeanUtils.copyProperties(credentialsRequest, credentials);
            credentials.setPassword(EncryptionUtil.passWordEnCode(credentialsRequest.getPassword(), BCryptPasswordEncoder.class));
            credentials.setEnabled(true);
            credentials.setCreateTime(LocalDateTime.now());

            //添加用户
            boolean creFlag = this.save(credentials);
            AssertsUtil.isTrue(!creFlag, "添加用户失败！");
            //添加用户角色关系表信息
            CredentialsAuthorities credentialsAuthorities = new CredentialsAuthorities();
            credentialsAuthorities.setAuthoritiesId(5);
            credentialsAuthorities.setCredentialsId(credentials.getId());
            boolean creAuthFlag = iCredentialsAuthoritiesService.save(credentialsAuthorities);
            AssertsUtil.isTrue(!creAuthFlag, "添加用户角色失败！");
        } else {
            return ResultResponse.failResponse("当前客户已存在！");
        }
        return ResultResponse.successResponse("添加用户成功！");
    }

    /**
     * 添加登录信息
     *
     * @param credentials 用户对象
     * @return ResultResponse
     */
    @Override
    public boolean addLoginMsg(Credentials credentials) {
        boolean flag;
        credentials.setPassword(EncryptionUtil.passWordEnCode(credentials.getPassword(), BCryptPasswordEncoder.class));
        flag = this.save(credentials);
        if (UserTypeEnum.THREE.getCode().equals(credentials.getUserType())) {
            //添加用户角色关系表信息
            CredentialsAuthorities credentialsAuthorities = new CredentialsAuthorities();
            credentialsAuthorities.setAuthoritiesId(7);
            credentialsAuthorities.setCredentialsId(credentials.getUserId());
            flag &= iCredentialsAuthoritiesService.save(credentialsAuthorities);
        }
        return flag;
    }

    @Override
    public ResultResponse updateUser(CredentialsRequest credentialsRequest) {
        //查询用户
        Credentials credentials = this.getOne(new QueryWrapper<Credentials>().lambda().eq(Credentials::getId, credentialsRequest.getId()));
        BeanUtils.copyProperties(credentialsRequest, credentials);
        credentials.setName(credentialsRequest.getName());
        credentials.setCompanyName(credentialsRequest.getCompanyName());
        credentials.setEmail(credentialsRequest.getEmail());
        if (!StringUtil.isNullOrEmpty(credentialsRequest.getPassword())) {
            credentials.setPassword(EncryptionUtil.passWordEnCode(credentialsRequest.getPassword(), BCryptPasswordEncoder.class));
        }
        credentials.setUpdateTime(LocalDateTime.now());
        //更新客户信息
        boolean creFlag = this.saveOrUpdate(credentials);
        AssertsUtil.isTrue(!creFlag, "编辑失败！");
        return ResultResponse.successResponse("编辑成功！");
    }

    @Override
    public ResultResponse queryUser(Integer id) {
        //查询用户
        Credentials credentials = this.getById(id);
        //查询当前用户
        return ResultResponse.successResponse("查询用户详情成功", credentials);
    }

    @Override
    public ResultResponse deleteUser(Integer id) {
        //查询用户
        Credentials credentials = this.getById(id);
        credentials.setEnabled(false);
        boolean flag = this.saveOrUpdate(credentials);
        if (!flag) {
            return ResultResponse.failResponse("删除失败！");
        }
        return ResultResponse.successResponse("删除成功");
    }

    /**
     * 查询当前登录人角色和菜单信息
     *
     * @param token 当前登录人token
     * @return ResultResponse
     */
    @Override
    public ResultResponse getRoleAndMenu(String token) {
        CredentialsResponse userMsg = new CredentialsResponse();
        //获取用户信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //根据用户名称查询用户信息
        Credentials credentials = getOne(new QueryWrapper<Credentials>().lambda().eq(Credentials::getName, authentication.getName()));
        AssertsUtil.isTrue(ObjectUtils.isEmpty(credentials), "当前用户不存在!");
        Integer roleId;
        if (UserTypeEnum.ONE.getCode().equals(credentials.getUserType())) {
            CompanyUser companyUser = iCompanyUserService.getOne(new QueryWrapper<CompanyUser>().lambda()
                    .eq(CompanyUser::getId, credentials.getUserId()));
            AssertsUtil.isTrue(ObjectUtils.isEmpty(companyUser), "当前客户不存在!");
            userMsg.setId(companyUser.getId());
            userMsg.setName(companyUser.getUserName());
            userMsg.setUserId(companyUser.getId());
            UserAuthorities userAuthorities = iUserAuthoritiesService.getOne(new QueryWrapper<UserAuthorities>()
                    .lambda().eq(UserAuthorities::getCompanyUserId, companyUser.getId()));
            roleId = userAuthorities.getAuthorityId();
        } else if (UserTypeEnum.THREE.getCode().equals(credentials.getUserType())) {
            ResultResponse response = iSupplierService.supplier(credentials.getUserId());
            Supplier supplier = JSONObject.parseObject(JSONObject.toJSON(response.getData()).toString(), Supplier.class);
            AssertsUtil.isTrue(supplier == null, "查无此供应商!");
//            userMsg.setId(supplier.getId());
            assert supplier != null;
            userMsg.setName(supplier.getCompanyName());
            userMsg.setUserId(supplier.getId());
            CredentialsAuthorities one = iCredentialsAuthoritiesService.getOne(new QueryWrapper<CredentialsAuthorities>().lambda()
                    .eq(CredentialsAuthorities::getCredentialsId, supplier.getId()));
            AssertsUtil.isTrue(ObjectUtils.isEmpty(one), "用户和角色关系为空!");
            roleId = Integer.valueOf(one.getAuthoritiesId().toString());
        } else {
            BeanUtils.copyProperties(credentials, userMsg);
            //查询用户角色关系
            CredentialsAuthorities one = iCredentialsAuthoritiesService.getOne(new QueryWrapper<CredentialsAuthorities>().lambda()
                    .eq(CredentialsAuthorities::getCredentialsId, credentials.getId()));
            AssertsUtil.isTrue(ObjectUtils.isEmpty(one), "用户和角色关系为空!");
            roleId = Integer.valueOf(one.getAuthoritiesId().toString());

        }
        //查询角色信息
        Authority authority = iAuthorityService.getOne(new QueryWrapper<Authority>().lambda().eq(Authority::getId, roleId));
        AssertsUtil.isTrue(ObjectUtils.isEmpty(authentication), "当前用户无角色!");
        userMsg.setRoleName(authority.getAuthority());
        //查询菜单信息
        List<AuthorityMenu> authorityMenuList = iAuthorityMenuService.list(new QueryWrapper<AuthorityMenu>()
                .lambda().eq(AuthorityMenu::getAuthorityId, roleId));
        if (authorityMenuList.size() != 0) {
            List<AuthorityMenuResponse> collect = authorityMenuList.stream().map(authorityMenu -> {
                AuthorityMenuResponse authorityMenuResponse = new AuthorityMenuResponse();
                BeanUtils.copyProperties(authorityMenu, authorityMenuResponse);
                return authorityMenuResponse;
            }).collect(Collectors.toList());
            userMsg.setMenuList(MenuTree.getMenuTrees(collect));

            List<MenuFunction> menuFunctions = new ArrayList<>();
            authorityMenuList.forEach(authorityMenu -> {
                List<MenuFunction> list = iMenuFunctionService.list(new QueryWrapper<MenuFunction>()
                        .lambda().eq(MenuFunction::getMenuId, authorityMenu.getMenuId()));
                menuFunctions.addAll(list);
            });
            redisTemplate.opsForHash().put(token, "menuFunction", menuFunctions);
        }
        //保存到redis
        redisTemplate.opsForHash().put(token, "userMsg", userMsg);
        redisTemplate.expire(token, 30, TimeUnit.MINUTES);
        return ResultResponse.successResponse("查询成功", userMsg);
    }
}
