package com.chintec.ikks.common.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author rubin
 * @version 1.0
 * @date 2020/9/8 9:43
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class AuthorityRequest {
    /**
     * 主键Id
     */
    @ApiModelProperty(value = "主键Id", hidden = true)
    private Integer id;
    /**
     * 角色名称
     */
    @ApiModelProperty(value = "角色名称")
    private String authority;
    /**
     * 是否可用
     */
    @ApiModelProperty(value = "是否可用", hidden = true)
    private Boolean enabled;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注", hidden = true)
    private String remark;

    @ApiModelProperty(value = "角色等级", hidden = true)
    private String level;
    /**
     * 角色关联的菜单
     */
//    private List<Menu> menuList;
    /**
     * 角色关联的菜单Ids
     */
//    private String menuIds;
    /**
     * 角色关联的菜单
     */
//    private List<AuthorityMenuResponse> authorityMenuResponseList;
}
