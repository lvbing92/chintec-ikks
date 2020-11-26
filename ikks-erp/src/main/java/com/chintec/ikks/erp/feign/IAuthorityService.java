package com.chintec.ikks.erp.feign;

import com.chintec.ikks.common.entity.vo.AuthorityRequest;
import com.chintec.ikks.common.entity.vo.MenuRequest;
import com.chintec.ikks.common.util.ResultResponse;
import com.chintec.ikks.erp.annotation.PermissionAnnotation;
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
     * @param roleId  角色Id
     * @param menuIds 菜单Ids
     * @return ResultResponse
     */
    @ApiOperation(value = "新增角色菜单")
    @GetMapping("/role/addMenu")
    ResultResponse addRoleMenu(@RequestParam(value = "roleId") Integer roleId,
                               @RequestParam(value = "menuIds") String menuIds);

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
     * 删除角色菜单
     *
     * @param roleId 菜单对象
     * @param menuId 菜单Id
     * @return ResultResponse
     */
    @ApiOperation(value = "删除角色菜单")
    @DeleteMapping("/role/deleteMenu")
    ResultResponse deleteRoleMenu(@RequestParam Integer roleId,@RequestParam Integer menuId);

    /**
     * 通过Id查询角色
     *
     * @param id 角色Id
     * @return ResultResponse
     */
    @ApiOperation(value = "角色详情")
    @GetMapping("/role/{id}")
    ResultResponse queryRole(@PathVariable Integer id);

    /**
     * 删除角色
     *
     * @param id 角色Id
     * @return ResultResponse
     */
    @ApiOperation(value = "删除角色")
    @DeleteMapping("/role/{id}")
    ResultResponse deleteRole(@PathVariable Integer id);
}
