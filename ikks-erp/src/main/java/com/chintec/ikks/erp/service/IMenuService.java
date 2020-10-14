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
@FeignClient(value = "ikks-auth",path = "v1")
public interface IMenuService{
    /**
     *菜单列表查询
     *
     * @return ResultResponse
     */
    @ApiOperation(value = "菜单列表查询")
    @GetMapping(value = "/menus/")
    ResultResponse getMenuList();

    /**
     * 新增或修改菜单保存
     *
     * @param menu 菜单
     * @return ResultResponse
     */
    @ApiOperation(value = "新增或修改菜单保存")
    @GetMapping("/menu/save")
    ResultResponse addOrUpdateMenu(@RequestBody Menu menu);

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
