package com.chintec.ikks.common.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;


/**
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/10/20 10:49
 */
@Data
@ApiModel
public class SupplierTypeVo {

    private Integer id;

    /**
     * 供应商类型
     */
    @NotBlank(message = "名字不能为空")
    @ApiModelProperty("名称")
    private String name;

    /**
     * 描述
     */
    @NotBlank(message = "描述不能为空")
    @ApiModelProperty("描述")
    private String describe;

    /**
     * 节点id
     */
    @ApiModelProperty("节点id")
    private Integer flowId;

    /**
     * 节点名称
     */
    @ApiModelProperty("节点名称")
    private String flowName;

    /**
     * 创建人id
     */
    @ApiModelProperty(hidden =true)
    private String updateBy;

    /**
     * 创建人姓名
     */
    @ApiModelProperty(hidden =true)
    private String updateName;

}
