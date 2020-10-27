package com.chintec.ikks.common.entity.vo;

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
 * @author jeff·tang
 * @since 2020-10-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel
public class QualificationSupplierVo {


    /**
     * 主键id
     */
    private Integer id;

    /**
     * 供应商id
     */
    @NotBlank(message = "供应商id信息不能为空")
    @ApiModelProperty("商户id")
    private Integer supplierId;

    /**
     * 资质id
     */
    @NotBlank(message = "资质文档id信息不能为空")
    @ApiModelProperty("资质文档id")
    private Integer qualificationId;

    /**
     * 该资质的内容
     */
    @NotBlank(message = "资质文档内容不能为空")
    @ApiModelProperty("文档内容,是地址")
    private String content;


    /**
     * 操作人id
     */
    @ApiModelProperty(hidden = true)
    private Integer updateBy;

    /**
     * 操作人名字
     */
    @ApiModelProperty(hidden = true)
    private String updateName;

}
