package com.chintec.ikks.process.entity.po;

import com.chintec.ikks.process.entity.vo.NodeFunction;
import lombok.Data;

import java.util.List;

/**
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/9/17 10:38
 */
@Data
public class FlowNodeFunctionPo  {
    /**
     * 流程节点id
     */
    private Integer flowId;

    /**
     * 流程类型：0顺序 1 boolean 2 enum
     */
    private Boolean functionType;

    /**
     * 流程方法对应的类型和节点t
     */
    private List<NodeFunction> nodeFunctions;
}
