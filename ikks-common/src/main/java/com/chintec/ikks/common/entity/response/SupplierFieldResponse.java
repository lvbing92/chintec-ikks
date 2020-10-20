package com.chintec.ikks.common.entity.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
public class SupplierFieldResponse {


    private Integer id;

    /**
     * 英文字段
     */
    @ApiModelProperty("字段-英文名")
    private String field;

    /**
     * 中文字段
     */
    @ApiModelProperty("字段-中文名")
    private String fieldName;

    /**
     * 字段属性，如：文本,图片,视频
     */
    @ApiModelProperty("字段-类型,1文本2图片3视频")
    private String fieldType;

    /**
     * 字段说明
     */
    @ApiModelProperty("字段-说明")
    private String fieldExplain;

    /**
     * 创建时间
     */
    @ApiModelProperty("字段-创建时间")
    private String createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty("字段-更新时间")
    private String updateTime;

    /**
     * 更新人名称
     */
    @ApiModelProperty("字段-创建人名称")
    private String updateByName;

    /**
     * 所属节点
     */
    @ApiModelProperty("字段-所属节点")
    private Integer nodeId;

}
