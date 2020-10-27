package com.chintec.ikks.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chintec.ikks.common.entity.AuthorityMenu;

import java.util.List;

/**
 * <p>
 * 角色菜单关系表 服务类
 * </p>
 *
 * @author rubIn·lv
 * @since 2020-09-01
 */
public interface IAuthorityMenuService extends IService<AuthorityMenu> {

    /**
     * 根据角色Id查询菜单Id
     */
    List<Integer> getMenuIByRoleId(Integer roleId);

    /**
     * 删除
     *
     * @param roleId 角色Id
     */
    void deleteByRoleId(Integer roleId);
    /**
     * 删除
     *
     * @param authorityMenuList 角色菜单信息
     */
    boolean saveBatchAuthMenu(List<AuthorityMenu> authorityMenuList);
}
