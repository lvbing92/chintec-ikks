package com.chintec.ikks.erp.response;

import com.chintec.ikks.erp.entity.Credentials;
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
