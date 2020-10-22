package com.chintec.ikks.erp.controller;

import com.chintec.ikks.common.util.ResultResponse;
import com.chintec.ikks.erp.service.IPasswordFedService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author RuBin
 */

@RestController
@Data
@Validated
@RequestMapping("/v1")
@Api(value = "User Login", tags = {"后台用户登入登出"})
public class UserLoginController {

    @Autowired
    private IPasswordFedService iPasswordFedService;

    private static final Logger logger = LogManager.getLogger(UserLoginController.class);

    /**
     * 登出
     *
     * @param token token
     * @return ResultResponse
     */
    @ApiOperation(value = "BackOffice登出")
    @GetMapping(value = "/logout", produces = "application/json;charset=utf-8")
    public ResultResponse logout(@RequestHeader(value = "access_token") String token){
        return iPasswordFedService.logout(token);
    }


    /**
     * 登录
     *
     * @param userName 用户名
     * @param passWord 密码
     * @return ResultResponse
     */
    @ApiOperation(value = "BackOffice登录")
    @GetMapping(value = "/login", produces = "application/json;charset=utf-8")
    @ResponseStatus(HttpStatus.OK)
    public ResultResponse login(@RequestParam String userName,@RequestParam String passWord) {
        return iPasswordFedService.userLogin(userName,passWord);
    }

    /**
     * 登录
     *
     * @param email 用户名
     * @param passWord 密码
     * @return ResultResponse
     */
    @ApiOperation(value = "客户端登录")
    @GetMapping(value = "/userLogin", produces = "application/json;charset=utf-8")
    @ResponseStatus(HttpStatus.OK)
    public ResultResponse companyUserLogin(@RequestParam String email,@RequestParam String passWord) {
        return iPasswordFedService.companyUserLogin(email,passWord);
    }
}
