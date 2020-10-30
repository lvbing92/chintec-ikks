package com.chintec.ikks.erp.controller;

import com.chintec.ikks.common.entity.vo.AuthorityRequest;
import com.chintec.ikks.common.entity.vo.MenuRequest;
import com.chintec.ikks.common.util.ResultResponse;
import com.chintec.ikks.erp.annotation.PermissionAnnotation;
import com.chintec.ikks.erp.feign.IAuthorityService;
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
@Api(value = "auth", tags = "角色管理")
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
    @PermissionAnnotation(code ="200901")
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
    @PostMapping("/role/add")
    @PermissionAnnotation(code ="200902")
    public ResultResponse addRole(AuthorityRequest authorityRequest) {

        return iAuthorityService.addRole(authorityRequest);
    }

    /**
     * 新增角色菜单数据
     *
     * @param menuRequest 角色对象
     * @return ResultResponse
     */
    @ApiOperation(value = "新增角色菜单")
    @PostMapping("/role/addMenu")
    @PermissionAnnotation(code ="200903")
    public ResultResponse addRoleMenu(MenuRequest menuRequest) {
        return iAuthorityService.addRoleMenu(menuRequest);
    }

    /**
     * 更新角色
     *
     * @param authorityRequest 角色信息
     * @return ResultResponse
     */
    @ApiOperation(value = "更新角色")
    @PutMapping("/role/update")
    @PermissionAnnotation(code ="200904")
    public ResultResponse updateRole(AuthorityRequest authorityRequest) {

        return iAuthorityService.updateRole(authorityRequest);
    }

    /**
     * 编辑角色菜单数据
     *
     * @param menuRequest 菜单对象
     * @return ResultResponse
     */
    @ApiOperation(value = "编辑角色菜单")
    @PostMapping("/role/updateMenu")
    @PermissionAnnotation(code ="200905")
    public ResultResponse updateRoleMenu(MenuRequest menuRequest) {
        return iAuthorityService.updateRoleMenu(menuRequest);
    }

    /**
     * 通过Id查询角色
     *
     * @param id 角色Id
     * @return ResultResponse
     */
    @ApiOperation(value = "角色详情")
    @GetMapping("/role/{id}")
    @PermissionAnnotation(code ="200906")
    public ResultResponse queryRole(@PathVariable Integer id) {

        return iAuthorityService.queryRole(id);
    }

    /**
     * 删除角色
     *
     * @param id 角色Id
     * @return ResultResponse
     */
    @ApiOperation(value = "删除角色")
    @DeleteMapping("/role/{id}")
    @PermissionAnnotation(code ="200907")
    public ResultResponse deleteRole(@PathVariable Integer id) {
        return iAuthorityService.deleteRole(id);
    }
}
