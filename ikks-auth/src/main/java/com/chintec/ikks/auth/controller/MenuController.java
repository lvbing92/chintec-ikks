package com.chintec.ikks.auth.controller;

import com.chintec.ikks.auth.entity.Menu;
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
     * @param pageSize    页数
     * @param currentPage 当前页
     * @param status      状态
     * @param searchValue 查询条件
     * @param sorted      排序
     * @return ResultResponse
     */
    @ApiOperation(value = "查询部门列表")
    @GetMapping("/menus")
    public ResultResponse getUserList(@RequestParam(value = "pageSize", required = false) Integer pageSize,
                                      @RequestParam(value = "currentPage") Integer currentPage,
                                      @RequestParam(value = "status", required = false) String status,
                                      @RequestParam(value = "searchValue", required = false) String searchValue,
                                      @RequestParam(value = "sorted", required = false) String sorted) {
        return iMenuService.getMenuList(pageSize, currentPage, status, searchValue, sorted);
    }

    /**
     * 新增菜单
     *
     * @param departmentRequest 菜单信息
     * @return ResultResponse
     */
    @ApiOperation(value = "新增菜单")
    @GetMapping("/menu/add")
    public ResultResponse addUser(@RequestBody Menu departmentRequest) {

        return iMenuService.addMenu(departmentRequest);
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
