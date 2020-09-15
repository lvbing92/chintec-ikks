package com.chintec.ikks.auth.controller;

import com.chintec.ikks.auth.service.IPasswordFedService;
import com.chintec.ikks.common.util.ResultResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author RuBin
 */

@RestController
@Data
@Validated
@RequestMapping("/v1")
@Api(value = "Back Office User Login", tags = {"后台用户登入登出"})
public class UserLoginController {

  @Autowired
  private IPasswordFedService iPasswordFedService;

  private static final Logger logger = LogManager.getLogger(UserLoginController.class);

  /**
   *
   *
   * @param revokeToken
   * @return
   * @throws Exception
   */
  @ApiOperation(value = "BackOffice登出")
  @GetMapping(value = "logout", produces = "application/json;charset=utf-8")
  public ResultResponse logout(@RequestHeader(value = "token")  String revokeToken) throws Exception{
    return iPasswordFedService.logout(revokeToken);
  }


  /**
   *
   * @param request
   * @return
   * @throws Exception
   */
  @ApiOperation(value = "BackOffice登录")
  @GetMapping(value = "/login", produces = "application/json;charset=utf-8")
  @ResponseStatus(HttpStatus.OK)
  public ResultResponse login(HttpServletRequest request) {
      return iPasswordFedService.userLogin(request);
  }
}
