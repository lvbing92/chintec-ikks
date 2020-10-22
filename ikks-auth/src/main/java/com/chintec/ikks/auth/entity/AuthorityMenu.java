package com.chintec.ikks.auth.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
    private Long authorityId;

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

    @Override
    protected Serializable pkVal() {
        return null;
    }

}
