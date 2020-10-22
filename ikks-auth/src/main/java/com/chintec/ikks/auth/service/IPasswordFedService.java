package com.chintec.ikks.auth.service;

import com.chintec.ikks.common.util.ResultResponse;

/**
 * @author rubin
 * @version 1.0
 * @date 2020/8/27 10:39
 */
public interface IPasswordFedService {

    /**
     * logout
     *
     * @param token
     * @return ResultResponse
     */
    ResultResponse logout(String token);

    /**
     * 登入
     *
     * @param userName 用户名
     * @param passWord 密码
     * @return ResultResponse
     */
    ResultResponse userLogin(String userName, String passWord);

    /**
     * 客户端登入
     *
     * @param email 用户名
     * @param passWord 密码
     * @return ResultResponse
     */
    ResultResponse companyUserLogin(String email, String passWord);
}
