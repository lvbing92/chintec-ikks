package com.chintec.ikks.erp.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 *
 * </p>
 *
 * @author ruBIn·lv
 * @since 2020-08-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "Authority", description = "角色信息")
public class Authority  {

    private static final long serialVersionUID = 1L;

    private Long id;

    @ApiModelProperty(value = "角色类型")
    private String authority;

    @ApiModelProperty(value = "是否可用")
    private String enabled;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "创建时间")
    private String createTime;

    @ApiModelProperty(value = "更新时间")
    private String updateTime;

    @ApiModelProperty(value = "更新人Id")
    private String updateById;

    @ApiModelProperty(value = "更新人名称")
    private String updateByName;


}
