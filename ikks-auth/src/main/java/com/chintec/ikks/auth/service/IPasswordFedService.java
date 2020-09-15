package com.chintec.ikks.auth.service;

import com.chintec.ikks.common.util.ResultResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author rubin
 * @version 1.0
 * @date 2020/8/27 10:39
 */
public interface IPasswordFedService {

    /**
     * 获取access_token
     *
     * @param userName
     * @param password
     * @return
     * @throws Exception
     */
//    String getFedAccessToken(String userName, String password) throws Exception;

    /**
     * logout
     *
     * @param revokeToken
     * @return
     * @throws Exception
     */
    ResultResponse logout(String revokeToken) throws Exception;

    /**
     * 登入
     *
     * @param request
     * @return
     */
    ResultResponse userLogin(HttpServletRequest request);
}
