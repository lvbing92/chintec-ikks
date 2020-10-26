package com.chintec.ikks.common.entity.response;

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
public class QualificationSupplierResponse {


    /**
     * 主键id
     */
    private Integer id;

    /**
     * 供应商id
     */
    @NotBlank(message = "供应商id信息不能为空")
    private Integer supplierId;

    /**
     * 资质id
     */
    @NotBlank(message = "资质文档id信息不能为空")
    private Integer qualificationId;

    /**
     * 该资质的内容
     */
    @NotBlank(message = "资质文档内容不能为空")
    private String content;


    /**
     * 数量
     */
    private Integer count;

    /**
     * 操作人名字
     */
    private String updateName;

}
