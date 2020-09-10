package com.chintec.ikks.erp.controller;

import com.chintec.ikks.common.util.ResultResponse;
import com.chintec.ikks.erp.request.AuthorityRequest;
import com.chintec.ikks.erp.service.IAuthorityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author rubin
 * @version 1.0
 * @date 2020/9/1 13:48
 */
@RestController
@RequestMapping(value = "v1")
@Api(value = "Back office" ,tags = "角色管理")
public class RoleController {
    @Autowired
    private IAuthorityService iAuthorityService;
    /**
     * 查询用户列表
     *
     * @return
     */
    @ApiOperation(value = "查询角色列表")
    @GetMapping("/roles")
    public ResultResponse getUserList(@RequestParam(value = "pageSize", required = false) Integer pageSize,
                                      @RequestParam(value = "currentPage", required = true) Integer currentPage,
                                      @RequestParam(value = "role", required = false) String role,
                                      @RequestParam(value = "status", required = false) String status,
                                      @RequestParam(value = "searchValue", required = false) String searchValue,
                                      @RequestParam(value = "sorted", required = false) String sorted) {
        return iAuthorityService.getRoleList(pageSize, currentPage, role, status, searchValue, sorted);
    }

    /**
     * 新增用户
     *
     * @return
     */
    @ApiOperation(value = "新增角色")
    @GetMapping("/role/add")
    public ResultResponse addUser(@RequestBody AuthorityRequest authorityRequest) {

        return iAuthorityService.addRole(authorityRequest);
    }

    /**
     * 通过Id查询用户
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "角色详情")
    @GetMapping("/role/{id}")
    public ResultResponse queryUser(@PathVariable String id) {
        return ResultResponse.successResponse("获取角色详情成功");
    }

    /**
     * 删除用户
     *
     * @return
     */
    @ApiOperation(value = "删除用户")
    @DeleteMapping("/role/{id}")
    public ResultResponse deleteUser(@PathVariable String id) {
        return ResultResponse.successResponse("删除角色成功");
    }
}
