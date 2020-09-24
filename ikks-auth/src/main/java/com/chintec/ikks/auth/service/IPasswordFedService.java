package com.chintec.ikks.auth.service;

import com.chintec.ikks.common.util.ResultResponse;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author rubin
 * @version 1.0
 * @date 2020/8/27 10:39
 */
public interface IPasswordFedService {

    /**
     * logout
     *
     * @param httpServletRequest
     * @return
     * @throws Exception
     */
    ResultResponse logout(HttpServletRequest httpServletRequest,
                          HttpServletResponse httpServletResponse,
                          Authentication authentication) throws Exception;

    /**
     * 登入
     *
     * @param request
     * @return
     */
    ResultResponse userLogin(HttpServletRequest request);
}
