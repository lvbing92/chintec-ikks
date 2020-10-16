package com.chintec.ikks.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chintec.ikks.auth.entity.Authority;
import com.chintec.ikks.auth.request.AuthorityRequest;
import com.chintec.ikks.auth.request.MenuRequest;
import com.chintec.ikks.common.util.ResultResponse;

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
     * 新增角色
     *
     * @param authorityRequest 角色对象
     * @return ResultResponse
     */
    ResultResponse addRole(AuthorityRequest authorityRequest);

    /**
     * 新增角色菜单数据
     *
     * @param menuRequest 角色对象
     * @return ResultResponse
     */
    ResultResponse addRoleMenu(MenuRequest menuRequest);

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
     * 通过Id查询角色详情
     *
     * @param id 角色Id
     * @return ResultResponse
     */
    ResultResponse queryRole(Long id);

    /**
     * 删除角色
     *
     * @param id 角色Id
     * @return ResultResponse
     */
    ResultResponse deleteRole(Long id);
}
