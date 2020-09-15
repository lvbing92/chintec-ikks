package com.chintec.ikks.auth.service.impl;

import com.chintec.ikks.common.enums.CommonCodeEnum;
import com.chintec.ikks.common.util.ResultResponse;
import com.chintec.ikks.erp.entity.OAuth2Token;
import com.chintec.ikks.erp.service.IOauthClientDetailsService;
import com.chintec.ikks.erp.service.IPasswordFedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.Arrays;

/**
 * @author rubin
 * @version 1.0
 * @date 2020/8/27 10:49
 */
@Service
public class PasswordFedServiceImpl implements IPasswordFedService {

    private static final String URL = "http://localhost:7070/oauth/token";
    private static final String APP_KEY = "user_client";
    private static final String SECRET_KEY = "123456";
    @Autowired
    private IOauthClientDetailsService iOauthClientDetailsService;

    @Override
    public ResultResponse logout(String revokeToken) throws Exception {
        return null;
    }

    @Override
    public ResultResponse userLogin(HttpServletRequest request) {

        OAuth2Token tokenMsg = null;
        try {
            tokenMsg = getToken(request);
        } catch (Exception e) {
            return ResultResponse.failResponse(CommonCodeEnum.PARAMS_ERROR_CODE.getCode(), "用户名或密码错误");
        }
        //查询用户角色，菜单

        return ResultResponse.successResponse("登录成功！",tokenMsg);
    }

    /**
     * 获取token
     *
     * @return
     */
    public OAuth2Token getToken(HttpServletRequest request) {
        RestTemplate rest = new RestTemplate();
        RequestEntity<MultiValueMap<String, String>> requestEntity = new RequestEntity<>(
                getBody(request), getHeader(), HttpMethod.POST, URI.create(URL));

        ResponseEntity<OAuth2Token> responseEntity = rest.exchange(
                requestEntity, OAuth2Token.class);

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
    private MultiValueMap<String, String> getBody(HttpServletRequest request) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "password");
        //TODO 页面上输入的用户名和密码
        formData.add("username", "rubin");
        formData.add("password", "123456");
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
