package com.chintec.ikks.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chintec.ikks.common.entity.Authority;
import com.chintec.ikks.common.entity.vo.AuthorityRequest;
import com.chintec.ikks.common.entity.vo.MenuRequest;
import com.chintec.ikks.common.util.ResultResponse;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author ruBIn·lv
 * @since 2020-08-26
 */

public interface IAuthorityService extends IService<Authority> {
    /**
     * 角色列表查询
     *
     * @param pageSize    页数
     * @param currentPage 当前页
     * @param searchValue 查询条件
     * @param sorted      排序
     * @return ResultResponse
     */
    ResultResponse getRoleList(Integer pageSize, Integer currentPage, String searchValue, String sorted);

    /**
     * 角色列表查询
     *
     * @return ResultResponse
     */
    List<Authority> getAllRoleList();

    /**
     * 新增角色
     *
     * @param authorityRequest 角色对象
     * @return ResultResponse
     */
    ResultResponse addRole(AuthorityRequest authorityRequest);

    /**
     * 新增角色菜单数据
     *
     * @param roleId  角色Id
     * @param menuIds 菜单Ids
     * @return ResultResponse
     */
    ResultResponse addRoleMenu(Integer roleId, String menuIds);

    /**
     * 更新角色
     *
     * @param authorityRequest 角色信息
     * @return ResultResponse
     */
    ResultResponse updateRole(AuthorityRequest authorityRequest);

    /**
     * 更新角色菜单
     *
     * @param menuRequest 菜单信息
     * @return ResultResponse
     */
    ResultResponse updateRoleMenu(MenuRequest menuRequest);

    /**
     * 删除角色菜单
     *
     * @param roleId 角色Id
     * @param menuId 菜单Id
     * @return ResultResponse
     */
    ResultResponse deleteRoleMenu(Integer roleId, Integer menuId);

    /**
     * 通过Id查询角色详情
     *
     * @param id 角色Id
     * @return ResultResponse
     */
    ResultResponse queryRole(Integer id);

    /**
     * 删除角色
     *
     * @param id 角色Id
     * @return ResultResponse
     */
    ResultResponse deleteRole(Integer id);
}
