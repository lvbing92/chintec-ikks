package com.chintec.ikks.auth.service.impl;

import com.chintec.ikks.auth.entity.OAuth2Token;
import com.chintec.ikks.auth.service.IPasswordFedService;
import com.chintec.ikks.common.enums.CommonCodeEnum;
import com.chintec.ikks.common.util.AssertsUtil;
import com.chintec.ikks.common.util.ResultResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;

/**
 * @author rubin
 * @version 1.0
 * @date 2020/8/27 10:49
 */
@Slf4j
@Service
public class PasswordFedServiceImpl implements
        IPasswordFedService {

    private static final String URL = "http://localhost:7070/oauth/token";

    @Autowired
    private LogoutSuccessHandlerImpl logoutSuccessHandler;

    @Override
    public ResultResponse logout(String token) {
        boolean flag = logoutSuccessHandler.onLogoutSuccess(token);
        AssertsUtil.isTrue(!flag, "退出失败!");
        return ResultResponse.successResponse("退出成功！");
    }

    @Override
    public ResultResponse userLogin(String userName, String passWord) {

        OAuth2Token tokenMsg = null;
        try {
            tokenMsg = getToken(userName, passWord);
            log.info("tokenMsg=" + tokenMsg);

        } catch (Exception e) {
            e.printStackTrace();
            return ResultResponse.failResponse(CommonCodeEnum.PARAMS_ERROR_CODE.getCode(), "用户名或密码错误");
        }
        //查询用户角色，菜单

        return ResultResponse.successResponse("登录成功！", tokenMsg);
    }

    /**
     * 获取token
     *
     * @return
     */
    private OAuth2Token getToken(String userName, String passWord) {
        RestTemplate rest = new RestTemplate();
        RequestEntity<MultiValueMap<String, String>> requestEntity = new RequestEntity<>(
                getBody(userName, passWord), getHeader(), HttpMethod.POST, URI.create(URL));

        ResponseEntity<OAuth2Token> responseEntity = rest.exchange(requestEntity, OAuth2Token.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return responseEntity.getBody();
        }
        throw new RuntimeException("error trying to retrieve access token");
    }

    /**
     * 组装请求参数
     *
     * @return
     */
    private MultiValueMap<String, String> getBody(String userName, String passWord) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "password");
        formData.add("username", userName);
        formData.add("password", passWord);
        //重定向地址
        formData.add("redirect_uri", "http://www.baidu.com");
        return formData;
    }

    /**
     * 构造Basic Auth认证头信息
     *
     * @return
     */
    private HttpHeaders getHeader() {
        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        httpHeaders.add("Authorization", "Basic " + "dXNlcl9jbGllbnQ6MTIzNDU2");
        return httpHeaders;
    }
}
