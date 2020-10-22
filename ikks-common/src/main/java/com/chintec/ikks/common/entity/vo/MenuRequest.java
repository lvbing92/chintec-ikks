package com.chintec.ikks.common.entity.vo;

import lombok.Data;


/**
 * @author rubin·lv
 * @version 1.0
 * @date 2020/10/13 17:11
 */
@Data
public class MenuRequest {
    /**
     * 角色Id
     */
    private Long roleId;

    /**
     * id
     */
    private Long id;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 客户命名菜单名称
     */
    private String userMenuName;

    /**
     * 父Id
     */
    private Long parentId;

    /**
     * 图片
     */
    private String icon;

    /**
     * 客户命名图片
     */
    private String userIcon;
}
