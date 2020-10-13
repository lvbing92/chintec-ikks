package com.chintec.ikks.erp.controller;


import com.chintec.ikks.common.util.ResultResponse;
import com.chintec.ikks.erp.request.CredentialsRequest;
import com.chintec.ikks.erp.service.ICredentialsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/8/17 17:00
 */
@RestController
@RequestMapping("/v1")
@Api(value = "User", tags = {"后台用户管理"})
public class AuthController {
    @Autowired
    private ICredentialsService iCredentialsService;

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
     * */
    @ApiOperation(value = "查询用户列表")
    @GetMapping("/users")
    public ResultResponse getUserList(@RequestParam(value = "pageSize", required = false) Integer pageSize,
                                      @RequestParam(value = "currentPage", required = true) Integer currentPage,
                                      @RequestParam(value = "role", required = false) String role,
                                      @RequestParam(value = "status", required = false) String status,
                                      @RequestParam(value = "searchValue", required = false) String searchValue,
                                      @RequestParam(value = "sorted", required = false) String sorted) {
        return iCredentialsService.getUserList(pageSize, currentPage, role, status, searchValue, sorted);
    }

    /**
     * 新增用户
     *
     * @return
     */
    @ApiOperation(value = "新增用户")
    @GetMapping("/user/add")
    public ResultResponse addUser(@RequestBody CredentialsRequest credentialsRequest) {

        return iCredentialsService.addUser(credentialsRequest);
    }

    /**
     * 通过Id查询用户
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "用户详情")
    @GetMapping("/user/{id}")
    public ResultResponse queryUser(@PathVariable String id) {
        return iCredentialsService.queryUser(id);
    }
    /**
     * 删除用户
     *
     * @return
     */
    @ApiOperation(value = "删除用户")
    @DeleteMapping("/user/{id}")
    public ResultResponse deleteUser(@PathVariable String id) {
        return iCredentialsService.deleteUser(id);
    }


   /* @GetMapping("/welcome")
    public String welcome() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return "Welcome " + authentication.getName();
    }*/
}
