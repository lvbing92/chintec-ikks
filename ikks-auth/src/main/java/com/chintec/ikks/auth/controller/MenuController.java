package com.chintec.ikks.auth.controller;

import com.chintec.ikks.auth.entity.Menu;
import com.chintec.ikks.auth.request.DepartmentRequest;
import com.chintec.ikks.auth.request.MenuRequest;
import com.chintec.ikks.auth.service.IDepartmentService;
import com.chintec.ikks.auth.service.IMenuService;
import com.chintec.ikks.common.util.ResultResponse;
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
    public ResultResponse getUserList() {
        return iMenuService.getMenuList();
    }

    /**
     * 新增或修改菜单
     *
     * @param menu 菜单信息2
     * @return ResultResponse
     */
    @ApiOperation(value = "新增或修改菜单")
    @PostMapping("/menu/save")
    public ResultResponse addOrUpdateMenu(@RequestBody Menu menu) {

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
    public ResultResponse queryUser(@PathVariable String id) {
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
    public ResultResponse deleteUser(@PathVariable String id) {
        return iMenuService.deleteMenu(id);
    }
}
