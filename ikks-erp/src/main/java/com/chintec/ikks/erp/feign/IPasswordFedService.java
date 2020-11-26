package com.chintec.ikks.erp.feign;

import com.chintec.ikks.common.entity.vo.CredentialsRequest;
import com.chintec.ikks.common.util.ResultResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author rubin
 * @version 1.0
 * @date 2020/8/27 10:39
 */
@FeignClient(value = "ikks-auth")
public interface IPasswordFedService {

    /**
     * logout
     *
     * @param token token
     * @return ResultResponse
     */
    @ApiOperation(value = "erp登出")
    @GetMapping(value = "/v1/logout", produces = "application/json;charset=utf-8")
    ResultResponse logout(@RequestHeader(value = "access_token") String token);

    /**
     * 登入
     *
     * @param userName 用户名
     * @param passWord 密码
     * @return ResultResponse
     */
    @ApiOperation(value = "erp登入")
    @GetMapping(value = "/v1/login", produces = "application/json;charset=utf-8")
    ResultResponse userLogin(@RequestParam String userName, @RequestParam String passWord);

    @ApiOperation(value = "客户端登入")
    @GetMapping(value = "/v1/userLogin", produces = "application/json;charset=utf-8")
    ResultResponse companyUserLogin(@RequestParam String email,@RequestParam String passWord);
    /**
     * 注册用户
     *
     * @param credentialsRequest 用户对象
     * @return ResultResponse
     */
    @ApiOperation(value = "注册用户")
    @PostMapping(value = "/v1/userRegister")
    ResultResponse userRegister(@RequestBody CredentialsRequest credentialsRequest);
}
