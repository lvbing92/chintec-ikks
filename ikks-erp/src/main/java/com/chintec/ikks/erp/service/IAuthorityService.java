package com.chintec.ikks.erp.service;

import com.chintec.ikks.common.util.ResultResponse;
import com.chintec.ikks.erp.request.AuthorityRequest;
import com.chintec.ikks.erp.request.MenuRequest;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author ruBIn·lv
 * @since 2020-08-26
 */
@FeignClient(value = "ikks-auth", path = "v1")
public interface IAuthorityService {
    /**
     * 角色列表查询
     *
     * @param pageSize    页数
     * @param currentPage 当前页
     * @param searchValue 查询条件
     * @param sorted      排序
     * @return ResultResponse
     */
    @ApiOperation(value = "查询角色列表")
    @GetMapping("/roles")
    ResultResponse getRoleList(@RequestParam(value = "pageSize", required = false) Integer pageSize,
                               @RequestParam(value = "currentPage", required = true) Integer currentPage,
                               @RequestParam(value = "searchValue", required = false) String searchValue,
                               @RequestParam(value = "sorted", required = false) String sorted);

    /**
     * 新增角色
     *
     * @param authorityRequest 角色信息
     * @return
     */
    @ApiOperation(value = "新增角色")
    @PostMapping("/role/add")
    ResultResponse addRole(@RequestBody AuthorityRequest authorityRequest);

    /**
     * 新增角色菜单数据
     *
     * @param menuRequest 角色对象
     * @return ResultResponse
     */
    @ApiOperation(value = "新增角色菜单")
    @PostMapping("/role/addMenu")
    ResultResponse addRoleMenu(@RequestBody MenuRequest menuRequest);

    /**
     * 更新角色
     *
     * @param authorityRequest 角色信息
     * @return ResultResponse
     */
    @ApiOperation(value = "更新角色")
    @PutMapping("/role/update")
    ResultResponse updateRole(@RequestBody AuthorityRequest authorityRequest);

    /**
     * 编辑角色菜单数据
     *
     * @param menuRequest 菜单对象
     * @return ResultResponse
     */
    @ApiOperation(value = "编辑角色菜单")
    @PostMapping("/role/updateMenu")
    ResultResponse updateRoleMenu(@RequestBody MenuRequest menuRequest);

    /**
     * 通过Id查询角色
     *
     * @param id 角色Id
     * @return ResultResponse
     */
    @ApiOperation(value = "角色详情")
    @GetMapping("/role/{id}")
    ResultResponse queryRole(@PathVariable Long id);

    /**
     * 删除角色
     *
     * @param id 角色Id
     * @return ResultResponse
     */
    @ApiOperation(value = "删除角色")
    @DeleteMapping("/role/{id}")
    ResultResponse deleteRole(@PathVariable Long id);
}
