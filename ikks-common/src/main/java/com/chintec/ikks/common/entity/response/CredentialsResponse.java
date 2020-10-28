package com.chintec.ikks.common.entity.response;

import com.chintec.ikks.common.entity.Credentials;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author rubin·lv
 * @since 2020-08-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CredentialsResponse extends Credentials {

    /**
     * 用户Id
     */
    private Integer id;

    @ApiModelProperty(value = "用户Id")
    private Integer userId;

    @ApiModelProperty(value = "是否可用")
    private Boolean enabled;

    @ApiModelProperty(value = "用户名")
    private String name;

    @ApiModelProperty(value = "公司名")
    private String companyName;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "用户类型")
    private String userType;

    @ApiModelProperty(value = "角色Id")
    private Integer roleId;

    @ApiModelProperty(value = "角色等级")
    private String level;

    @ApiModelProperty(value = "角色名称")
    private String roleName;

    @ApiModelProperty(value = "菜单")
    private List<AuthorityMenuResponse> menuList;
}
