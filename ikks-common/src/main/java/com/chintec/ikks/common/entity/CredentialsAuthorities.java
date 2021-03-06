package com.chintec.ikks.common.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author Jeff·Tang
 * @since 2020-08-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "CredentialsAuthorities", description = "用户角色关系信息")
public class CredentialsAuthorities extends Model<CredentialsAuthorities> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户Id")
    private Integer credentialsId;

    @ApiModelProperty(value = "角色Id")
    private Integer authoritiesId;
}
