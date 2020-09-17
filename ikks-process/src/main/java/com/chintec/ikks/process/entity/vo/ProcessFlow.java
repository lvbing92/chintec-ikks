package com.chintec.ikks.process.entity.vo;

import com.chintec.ikks.process.entity.FlowNode;
import com.chintec.ikks.process.entity.FlowNodeFunction;
import com.chintec.ikks.process.entity.po.FlowNodeFunctionPo;
import lombok.Data;

/**
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/9/17 9:28
 */
@Data
public class ProcessFlow extends FlowNode {
    private FlowNodeFunctionPo flowNodeFunctionPo;
}
