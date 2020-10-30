package com.chintec.ikks.erp.controller;

import com.chintec.ikks.common.entity.Menu;
import com.chintec.ikks.common.util.ResultResponse;
import com.chintec.ikks.erp.annotation.PermissionAnnotation;
import com.chintec.ikks.erp.feign.IMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author rubin·lv
 * @version 1.0
 * @date 2020/9/25 11:05
 */
@RestController
@RequestMapping(value = "v1")
@Api(value = "auth", tags = "菜单管理")
public class MenuController {
    @Autowired
    private IMenuService iMenuService;

    /**
     * 菜单列表查询
     *
     * @return ResultResponse
     */
    @ApiOperation(value = "查询菜单列表")
    @GetMapping("/menus")
    @PermissionAnnotation(code ="200101")
    public ResultResponse getUserList() {
        return iMenuService.getMenuList();
    }

    /**
     * 新增或修改菜单
     *
     * @param menu 菜单信息
     * @return ResultResponse
     */
    @ApiOperation(value = "新增或修改菜单")
    @PostMapping("/menu/save")
    @PermissionAnnotation(code ="200102")
    public ResultResponse addOrUpdateMenu(Menu menu) {

        return iMenuService.addOrUpdateMenu(menu);
    }

    /**
     * 通过Id查询菜单
     *
     * @param id 部门Id
     * @return ResultResponse
     */
    @ApiOperation(value = "菜单详情")
    @GetMapping("/menu/{id}")
    @PermissionAnnotation(code ="200103")
    public ResultResponse queryUser(@PathVariable Integer id) {
        return iMenuService.queryMenu(id);
    }

    /**
     * 删除菜单
     *
     * @param id 部门Id
     * @return ResultResponse
     */
    @ApiOperation(value = "删除菜单")
    @DeleteMapping("/menu/{id}")
    @PermissionAnnotation(code ="200104")
    public ResultResponse deleteUser(@PathVariable Integer id) {
        return iMenuService.deleteMenu(id);
    }
}
