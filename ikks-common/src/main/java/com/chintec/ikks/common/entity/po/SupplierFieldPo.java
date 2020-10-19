package com.chintec.ikks.common.entity.po;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 供应商属性字段表
 * </p>
 *
 * @author jeff·Tang
 * @since 2020-10-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SupplierFieldPo {

    private Integer id;

    /**
     * 英文字段
     */
    @NotBlank(message = "请输入正确的英文字段名字")
    private String field;

    /**
     * 中文字段
     */
    @NotBlank(message = "请输入正确的中文字段属性名字")
    private String fieldName;

    /**
     * 字段属性，如：文本,图片,视频
     */
    @NotBlank(message = "请选择正确的字段属性类型")
    private String fieldType;

    /**
     * 字段说明
     */
    private String fieldExplain;


    /**
     * 所属节点
     */
    private Integer nodeId;

    /**
     * 更新人Id
     */
    private Integer updateById;

    /**
     * 更新人名称
     */
    private String updateByName;

}
