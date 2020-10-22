package com.chintec.ikks.auth.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chintec.ikks.auth.mapper.CredentialsMapper;
import com.chintec.ikks.auth.service.IAuthorityMenuService;
import com.chintec.ikks.auth.service.IAuthorityService;
import com.chintec.ikks.auth.service.ICredentialsAuthoritiesService;
import com.chintec.ikks.auth.service.ICredentialsService;
import com.chintec.ikks.common.entity.Authority;
import com.chintec.ikks.common.entity.AuthorityMenu;
import com.chintec.ikks.common.entity.Credentials;
import com.chintec.ikks.common.entity.CredentialsAuthorities;
import com.chintec.ikks.common.entity.response.AuthorityMenuResponse;
import com.chintec.ikks.common.entity.response.CredentialsResponse;
import com.chintec.ikks.common.entity.vo.CredentialsRequest;
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

import java.util.List;
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
    private RedisTemplate<String,Object> redisTemplate;
    @Autowired
    private IAuthorityService iAuthorityService;

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
            BCryptPasswordEncoder b = new BCryptPasswordEncoder();
            Credentials credentials = new Credentials();
            BeanUtils.copyProperties(credentialsRequest, credentials);
            credentials.setPassword(EncryptionUtil.passWordEnCode(credentialsRequest.getPassword()));
            credentials.setEnabled(true);
            credentials.setCreateTime(LocalDateTime.now());
            credentials.setVersion(1);

            //添加用户
            boolean creFlag = this.save(credentials);
            AssertsUtil.isTrue(!creFlag, "添加用户失败！");
            //添加用户角色关系表信息
            CredentialsAuthorities credentialsAuthorities = new CredentialsAuthorities();
            credentialsAuthorities.setAuthoritiesId(4L);
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
        return save(credentials);
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
            credentials.setPassword(EncryptionUtil.passWordEnCode(credentialsRequest.getPassword()));
        }
        credentials.setUpdateTime(LocalDateTime.now());
        //更新客户信息
        boolean creFlag = this.saveOrUpdate(credentials);
        AssertsUtil.isTrue(!creFlag, "编辑失败！");
        return ResultResponse.successResponse("编辑成功！");
    }

    @Override
    public ResultResponse queryUser(Long id) {
        //查询用户
        Credentials credentials = this.getById(id);
        //查询当前用户
        return ResultResponse.successResponse("查询用户详情成功", credentials);
    }

    @Override
    public ResultResponse deleteUser(Long id) {
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
    public ResultResponse getRoleAndMenu(String token) {
        CredentialsResponse userMsg = new CredentialsResponse();
        //获取用户信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Credentials credentials = JSONObject.parseObject(JSONObject.toJSON(authentication.getCredentials()).toString(), Credentials.class);
        BeanUtils.copyProperties(credentials,userMsg);
        //查询用户角色关系
        CredentialsAuthorities one = iCredentialsAuthoritiesService.getOne(new QueryWrapper<CredentialsAuthorities>().lambda()
                .eq(CredentialsAuthorities::getCredentialsId, credentials.getId()));
        AssertsUtil.isTrue(ObjectUtils.isEmpty(one),"用户和角色关系为空!");
        //查询角色信息
        Authority authority = iAuthorityService.getOne(new QueryWrapper<Authority>().lambda().eq(Authority::getId, one.getAuthoritiesId()));
        AssertsUtil.isTrue(ObjectUtils.isEmpty(authentication),"当前用户无角色!");
        userMsg.setRoleName(authority.getAuthority());
        userMsg.setLevel(authority.getLevel());
        //查询菜单信息
        List<AuthorityMenu> authorityMenuList = iAuthorityMenuService.list(new QueryWrapper<AuthorityMenu>()
                .lambda().eq(AuthorityMenu::getAuthorityId, one.getAuthoritiesId()));
        if (authorityMenuList.size() != 0) {
            List<AuthorityMenuResponse> collect = authorityMenuList.stream().map(authorityMenu -> {
                AuthorityMenuResponse authorityMenuResponse = new AuthorityMenuResponse();
                BeanUtils.copyProperties(authorityMenu, authorityMenuResponse);
                return authorityMenuResponse;
            }).collect(Collectors.toList());
            userMsg.setMenuList(MenuTree.getMenuTrees(collect));
        }
        //保存到redis
        redisTemplate.opsForValue().set(token,userMsg);
        return ResultResponse.successResponse("查询成功",userMsg);
    }
}
