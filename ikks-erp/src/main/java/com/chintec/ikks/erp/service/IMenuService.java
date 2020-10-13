package com.chintec.ikks.erp.service;

import com.chintec.ikks.common.util.ResultResponse;
import com.chintec.ikks.erp.entity.Menu;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author rubIn·lv
 * @since 2020-09-01
 */
@FeignClient(value = "ikks-auth")
public interface IMenuService{
    /**
     * 角色列表查询
     *
     * @param pageSize    页数
     * @param currentPage 当前页
     * @param status      状态
     * @param searchValue 查询条件
     * @param sorted      排序
     * @return ResultResponse
     */
    @ApiOperation(value = "菜单列表查询")
    @GetMapping(value = "/v1/menus/")
    ResultResponse getMenuList(@RequestParam(value = "pageSize", required = false) Integer pageSize,
                               @RequestParam(value = "currentPage") Integer currentPage,
                               @RequestParam(value = "status", required = false) String status,
                               @RequestParam(value = "searchValue", required = false) String searchValue,
                               @RequestParam(value = "sorted", required = false) String sorted);

    /**
     * 新增菜单
     *
     * @param menu 菜单
     * @return ResultResponse
     */
    @ApiOperation(value = "新增菜单")
    @GetMapping("/menu/add")
    ResultResponse addMenu(@RequestBody Menu menu);

    /**
     * 通过Id查询菜单
     *
     * @param id 菜单Id
     * @return ResultResponse
     */
    @ApiOperation(value = "菜单详情")
    @GetMapping("/menu/{id}")
    ResultResponse queryMenu(@PathVariable String id);

    /**
     * 删除菜单
     *
     * @param id 菜单Id
     * @return ResultResponse
     */
    @ApiOperation(value = "删除菜单")
    @DeleteMapping("/menu/{id}")
    ResultResponse deleteMenu(@PathVariable String id);

}
