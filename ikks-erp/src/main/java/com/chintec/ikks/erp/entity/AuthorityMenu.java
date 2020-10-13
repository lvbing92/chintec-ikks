package com.chintec.ikks.erp.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 角色菜单关系表
 * </p>
 *
 * @author rubIn·lv
 * @since 2020-09-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "AuthorityMenu", description = "角色与菜单关系信息")
public class AuthorityMenu {

    private static final long serialVersionUID = 1L;

    /**
     * 角色Id
     */
    private Integer authorityId;

    /**
     * 菜单Id
     */
    private Integer menuId;



}
