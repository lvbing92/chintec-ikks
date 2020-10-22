package com.chintec.ikks.common.entity.vo;

import com.chintec.ikks.common.entity.Authority;
import com.chintec.ikks.common.entity.Menu;
import com.chintec.ikks.common.entity.response.AuthorityMenuResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author rubin
 * @version 1.0
 * @date 2020/9/8 9:43
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class AuthorityRequest extends Authority {
    /**
     * 主键Id
     */
    private Long id;
    /**
     * 角色名称
     */
    @ApiModelProperty(value = "角色名称",required = true)
    private String authority;
    /**
     * 是否可用
     */
    @ApiModelProperty(value = "是否可用",hidden = true)
    private Boolean enabled;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注",hidden = true)
    private String remark;
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
