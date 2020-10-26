package com.chintec.ikks.common.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
public class CredentialsRequest {
    @ApiModelProperty(value = "用户Id", hidden = true)
    private Integer id;

    @ApiModelProperty(value = "是否可用", hidden = true)
    private Boolean enabled;

    @ApiModelProperty(value = "用户名")
    private String name;

    @ApiModelProperty(value = "公司名")
    private String companyName;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "用户类型", hidden = true)
    private String userType;
}

