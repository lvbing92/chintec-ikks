package com.chintec.ikks.common.entity.response;

import com.chintec.ikks.common.entity.Credentials;
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
public class CredentialsResponse extends Credentials {
    @ApiModelProperty(value = "角色Id")
    private Long roleId;
}
