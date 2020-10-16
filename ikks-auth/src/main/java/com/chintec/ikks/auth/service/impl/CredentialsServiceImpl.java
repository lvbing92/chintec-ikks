package com.chintec.ikks.auth.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chintec.ikks.auth.entity.Credentials;
import com.chintec.ikks.auth.entity.CredentialsAuthorities;
import com.chintec.ikks.auth.mapper.CredentialsMapper;
import com.chintec.ikks.auth.request.CredentialsRequest;
import com.chintec.ikks.auth.service.ICredentialsAuthoritiesService;
import com.chintec.ikks.auth.service.ICredentialsService;
import com.chintec.ikks.common.util.AssertsUtil;
import com.chintec.ikks.common.util.PageResultResponse;
import com.chintec.ikks.common.util.ResultResponse;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户服务实现类
 * </p>
 *
 * @author ruBIn·lv
 * @since 2020-08-26
 */
@Service
public class CredentialsServiceImpl extends ServiceImpl<CredentialsMapper, Credentials> implements ICredentialsService, Serializable {

    private static final String SORT_A = "A";
    private static final String SORT_D = "D";
    @Autowired
    private ICredentialsAuthoritiesService iCredentialsAuthoritiesService;

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
        Credentials user =this.getOne(new QueryWrapper<Credentials>().lambda()
                .eq(Credentials::getName,credentialsRequest.getName())
                .eq(Credentials::getCompanyName,credentialsRequest.getCompanyName()));
        if(!ObjectUtils.isEmpty(user)){
        BCryptPasswordEncoder b = new BCryptPasswordEncoder();
        Credentials credentials = new Credentials();
        BeanUtils.copyProperties(credentialsRequest, credentials);
        credentials.setPassword(passWordEnCode(credentialsRequest.getPassword()));
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
        }else{
            return ResultResponse.failResponse("当前客户已存在！");
        }
        return ResultResponse.successResponse("添加用户成功！");
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
            credentials.setPassword(passWordEnCode(credentialsRequest.getPassword()));
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
       if(!flag){
           return ResultResponse.failResponse("删除失败！");
       }
        return ResultResponse.successResponse("删除成功");
    }

    /**
     * 密码加密
     *
     * @param passWord 密码
     * @return String
     */
    private String passWordEnCode(String passWord) {
        BCryptPasswordEncoder b = new BCryptPasswordEncoder();
        return b.encode(passWord);
    }
}
