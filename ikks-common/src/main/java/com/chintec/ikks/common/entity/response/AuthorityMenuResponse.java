package com.chintec.ikks.common.entity.response;

import lombok.Data;

import java.util.List;

/**
 * @author rubin·lv
 * @version 1.0
 * @date 2020/10/19 17:48
 */
@Data
public class AuthorityMenuResponse {
    /**
     * 角色Id
     */
    private Long authorityId;

    /**
     * 角色名称
     */
    private String authority;

    /**
     * 菜单Id
     */
    private Long menuId;
    /**
     * 父菜单Id
     */
    private Long parentId;

    /**
     * 菜单名称
     *
     */
    private String menuName;
    /**
     * 菜单icon
     */
    private String menuIcon;


    private List<AuthorityMenuResponse> childList;

}
