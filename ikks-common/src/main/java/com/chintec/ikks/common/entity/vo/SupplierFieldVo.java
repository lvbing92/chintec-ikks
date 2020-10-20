package com.chintec.ikks.common.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel
public class SupplierFieldVo {
    @ApiModelProperty("id,修改的时候传参")
    private Integer id;

    /**
     * 英文字段
     */
    @NotBlank(message = "请输入正确的英文字段名字")
    @ApiModelProperty("字段名-英文")
    private String field;

    /**
     * 中文字段
     */
    @NotBlank(message = "请输入正确的中文字段属性名字")
    @ApiModelProperty("字段名-中文")
    private String fieldName;

    /**
     * 字段属性，如：文本,图片,视频
     */
    @NotBlank(message = "请选择正确的字段属性类型")
    @ApiModelProperty("字段-类型")
    private String fieldType;

    /**
     * 字段说明
     */
    @ApiModelProperty("字段-说明")
    private String fieldExplain;


    /**
     * 所属节点
     */
    @ApiModelProperty(value = "字段-所属节点", hidden = true)
    private Integer nodeId;

    /**
     * 更新人Id
     */
    @ApiModelProperty(hidden = true)
    private Integer updateById;

    /**
     * 更新人名称
     */
    @ApiModelProperty(hidden = true)
    private String updateByName;

}
