package com.chintec.ikks.auth.controller;

import com.chintec.ikks.auth.request.AuthorityRequest;
import com.chintec.ikks.auth.service.IAuthorityService;
import com.chintec.ikks.common.util.ResultResponse;
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
@Api(value = "auth" ,tags = "角色管理")
public class RoleController {

    @Autowired
    private IAuthorityService iAuthorityService;
    /**
     * 查询角色列表
     *
     * @return
     */
    @ApiOperation(value = "查询角色列表")
    @GetMapping("/roles")
    public ResultResponse getUserList(@RequestParam(value = "pageSize", required = false) Integer pageSize,
                                      @RequestParam(value = "currentPage", required = true) Integer currentPage,
                                      @RequestParam(value = "searchValue", required = false) String searchValue,
                                      @RequestParam(value = "sorted", required = false) String sorted) {
        return iAuthorityService.getRoleList(pageSize, currentPage, searchValue, sorted);
    }

    /**
     * 新增角色
     *
     * @return
     */
    @ApiOperation(value = "新增角色")
    @GetMapping("/role/add")
    public ResultResponse addUser(@RequestBody AuthorityRequest authorityRequest) {

        return iAuthorityService.addRole(authorityRequest);
    }

    /**
     * 通过Id查询角色
     *
     * @param id 角色Id
     * @return ResultResponse
     */
    @ApiOperation(value = "角色详情")
    @GetMapping("/role/{id}")
    public ResultResponse queryUser(@PathVariable String id) {
        iAuthorityService.queryRole(id);
        return ResultResponse.successResponse("获取角色详情成功");
    }

    /**
     * 删除角色
     * @param id 角色Id
     * @return
     */
    @ApiOperation(value = "删除角色")
    @DeleteMapping("/role/{id}")
    public ResultResponse deleteUser(@PathVariable String id) {
        return ResultResponse.successResponse("删除角色成功");
    }
}
