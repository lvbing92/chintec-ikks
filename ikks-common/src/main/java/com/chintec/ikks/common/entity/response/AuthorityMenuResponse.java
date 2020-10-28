package com.chintec.ikks.common.entity.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author rubin·lv
 * @version 1.0
 * @date 2020/10/19 17:48
 */
@Data
public class AuthorityMenuResponse implements Serializable {
    /**
     * 角色Id
     */
    private Integer authorityId;
    /**
     * 菜单Id
     */
    private Integer menuId;

    /**
     * 角色名称
     */
//    private String authority;

    /**
     * 父菜单Id
     */
    private Integer parentId;

    /**
     * 菜单url
     */
    private String url;
    /**
     * 菜单名称
     *
     */
    private String menuName;
    /**
     * 菜单icon
     */
    private String menuIcon;
    /**
     * 菜单编码
     */
    private String menuCode;


    private List<AuthorityMenuResponse> childList;

}
