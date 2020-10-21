package com.chintec.ikks.common.entity.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/9/24 15:10
 */
@Data
public class FlowInfoVo {
    /**
     * 流程名字
     */
    @NotBlank(message = "流程名字不能为空")
    private String flowName;

    /**
     * 停用：0，启用：1
     */
    private String flowStatus;

    /**
     * 模块ID
     */
    private Integer moduleId;

    /**
     * 节点集合
     */
    private List<FlowNodeVo> flowNodes;
}
