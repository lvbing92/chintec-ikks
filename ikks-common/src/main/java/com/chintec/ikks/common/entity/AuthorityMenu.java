package com.chintec.ikks.common.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

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
public class AuthorityMenu extends Model<AuthorityMenu> {

    private static final long serialVersionUID = 1L;

    /**
     * 角色Id
     */
    private Integer authorityId;

    /**
     * 菜单Id
     */
    private Integer menuId;
    /**
     * 父菜单Id
     */
    private Integer parentId;

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
     * 菜单地址
     */
    private String url;

    @Override
    protected Serializable pkVal() {
        return null;
    }

}
