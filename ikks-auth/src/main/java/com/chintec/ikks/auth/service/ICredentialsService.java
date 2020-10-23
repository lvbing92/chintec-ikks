package com.chintec.ikks.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chintec.ikks.common.entity.Credentials;
import com.chintec.ikks.common.entity.vo.CredentialsRequest;
import com.chintec.ikks.common.util.ResultResponse;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.InvocationTargetException;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Jeff·Tang
 * @since 2020-08-26
 */
public interface ICredentialsService extends IService<Credentials> {

    /**
     * 用户列表查询
     *
     * @param pageSize    页数
     * @param currentPage 当前页
     * @param role        角色
     * @param status      状态
     * @param searchValue 查询条件
     * @param sorted      排序
     * @return ResultResponse
     */
    ResultResponse getUserList(Integer pageSize, Integer currentPage, String role,
                               String status, String searchValue, String sorted);

    /**
     * 新增用户
     *
     * @param credentialsRequest 用户对象
     * @return ResultResponse
     */
    ResultResponse addUser(CredentialsRequest credentialsRequest) ;



    /**
     * 添加登录信息
     *
     * @param credentials 用户信息
     * @return boolean
     */
    boolean addLoginMsg(Credentials credentials);

    /**
     * 更新用户
     *
     * @param credentialsRequest 更新用户
     * @return ResultResponse
     */
    ResultResponse updateUser(CredentialsRequest credentialsRequest) ;

    /**
     * 通过Id查询用户
     *
     * @param id 用户Id
     * @return ResultResponse
     */
    ResultResponse queryUser(Long id);

    /**
     * 删除用户
     *
     * @param id 用户Id
     * @return ResultResponse
     */
    ResultResponse deleteUser(Long id);

    /**
     * 查询当前登录人角色和菜单信息
     *
     * @param token 当前登录人token
     * @return ResultResponse
     */
    ResultResponse getRoleAndMenu(@RequestParam(value = "token") String token);
}
