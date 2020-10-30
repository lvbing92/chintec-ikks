package com.chintec.ikks.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 菜单功能表
 * </p>
 *
 * @author rubIn·lv
 * @since 2020-10-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MenuFunction extends Model<MenuFunction> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键Id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 菜单Id
     */
    @ApiModelProperty(value = "菜单Id")
    private Integer menuId;

    /**
     * 菜单名称
     */
    @ApiModelProperty(value = "菜单名称")
    private String menuName;

    /**
     * 功能名称
     */
    @ApiModelProperty(value = "功能名称")
    private String functionName;

    /**
     * 功能编号
     */
    @ApiModelProperty(value = "功能编号")
    private String functionCode;

    /**
     * 功能url
     */
    @ApiModelProperty(value = "功能url")
    private String functionUrl;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
