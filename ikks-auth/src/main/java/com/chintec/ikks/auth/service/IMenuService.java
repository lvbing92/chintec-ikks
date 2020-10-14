package com.chintec.ikks.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chintec.ikks.auth.entity.Menu;
import com.chintec.ikks.common.util.ResultResponse;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author rubIn·lv
 * @since 2020-09-01
 */
public interface IMenuService extends IService<Menu> {
    /**
     * 角色列表查询
     *
     * @return ResultResponse
     */
    ResultResponse getMenuList();

    /**
     * 新增菜单
     *
     * @param menu 菜单
     * @return ResultResponse
     */
    ResultResponse addOrUpdateMenu(Menu menu);

    /**
     * 通过Id查询菜单
     *
     * @param id 菜单Id
     * @return ResultResponse
     */
    ResultResponse queryMenu(String id);

    /**
     * 删除菜单
     *
     * @param id 菜单Id
     * @return ResultResponse
     */
    ResultResponse deleteMenu(String id);

}
