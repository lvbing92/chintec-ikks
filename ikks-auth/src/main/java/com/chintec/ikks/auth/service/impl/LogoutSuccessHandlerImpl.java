package com.chintec.ikks.auth.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author rubin
 * @version 1.0
 * @date 2020/8/26 13:59
 */
@Component
public class LogoutSuccessHandlerImpl {
    private static String BEARER_AUTHENTICATION = "Bearer";

    private static String HEADER_AUTHENTICATION = "authorization";

    @Autowired
    private TokenStore tokenStore;

    boolean onLogoutSuccess(String token) {
//        String auth = httpServletRequest.getHeader(HEADER_AUTHENTICATION);
//        String token = httpServletRequest.getParameter("access_token");
        System.out.println("access_token:"+token);
//        if (auth != null && auth.startsWith(BEARER_AUTHENTICATION)) {
//            token = token.split(" ")[0];
//        }

        if (token != null) {
            OAuth2AccessToken accessToken = tokenStore.readAccessToken(token);
            if (accessToken != null) {
                tokenStore.removeAccessToken(accessToken);
            }
            return true;
        }else{
            return false;
        }
    }
}
