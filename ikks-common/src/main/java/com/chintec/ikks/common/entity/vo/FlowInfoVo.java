package com.chintec.ikks.common.entity.vo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/9/24 15:10
 */
@Data
@ApiModel
public class FlowInfoVo {
    /**
     * 流程名字
     */
    @NotBlank(message = "流程名字不能为空")
    @ApiModelProperty("流程名字")
    private String flowName;

    /**
     * 停用：0，启用：1
     */
    @ApiModelProperty(hidden = true)
    private String flowStatus;

    /**
     * 模块ID
     */
    @ApiModelProperty("模块id")
    private Integer moduleId;

    /**
     * 节点集合
     */
    @ApiModelProperty("节点结合")
    private List<FlowNodeVo> flowNodes;
}
