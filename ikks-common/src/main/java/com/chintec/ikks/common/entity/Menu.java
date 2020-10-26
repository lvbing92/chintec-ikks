package com.chintec.ikks.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author rubIn·lv
 * @since 2020-09-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "TMenu", description = "菜单信息")
public class Menu extends Model<Menu> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "菜单Id", hidden = true)
    private Integer id;

    /**
     * 名称
     */
    @ApiModelProperty(value = "菜单名称")
    private String menuName;

    /**
     * url
     */
    @ApiModelProperty(value = "菜单url")
    private String url;
    /**
     * 图片
     */
    @ApiModelProperty(value = "菜单图片")
    private String icon;

    /**
     * 模块类型
     */
    @ApiModelProperty(value = "模块类型", hidden = true)
    private String modelType;

    /**
     * 父Id
     */
    @ApiModelProperty(value = "父Id", hidden = true)
    private String parentId;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", hidden = true)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间", hidden = true)
    private LocalDateTime updateTime;

    /**
     * 更新人Id
     */
    @ApiModelProperty(value = "更新人Id", hidden = true)
    private Integer updateById;

    /**
     * 更新人名称
     */
    @ApiModelProperty(value = "更新人名称", hidden = true)
    private String updateByName;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
