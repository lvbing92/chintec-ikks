package com.chintec.ikks.common.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * @author rubin·lv
 * @version 1.0
 * @date 2020/10/13 17:11
 */
@Data
public class MenuRequest {

    /**
     * id
     */
    @ApiModelProperty(value = "id", hidden = true)
    private Integer id;

    /**
     * 角色Id
     */
    @ApiModelProperty(value = "角色Id", hidden = true)
    private Integer roleId;
    /**
     * 菜单名称
     */
    @ApiModelProperty(value = "菜单名称")
    private String menuName;

    /**
     * 父Id
     */
    @ApiModelProperty(value = "菜单地址", hidden = true)
    private Integer parentId;

    /**
     * 图片
     */
    @ApiModelProperty(value = "菜单地址")
    private String icon;

    /**
     * 菜单地址
     */
    @ApiModelProperty(value = "菜单地址", hidden = true)
    private String url;
}
