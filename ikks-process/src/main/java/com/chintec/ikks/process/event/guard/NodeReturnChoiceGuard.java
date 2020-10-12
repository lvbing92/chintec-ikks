package com.chintec.ikks.process.event.guard;

import com.chintec.ikks.common.enums.NodeStateChangeEnum;
import com.chintec.ikks.common.enums.NodeStateEnum;
import com.chintec.ikks.process.entity.po.FlowTaskStatusPo;
import org.springframework.statemachine.StateContext;
import org.springframework.util.StringUtils;

/**
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/10/12 11:22
 */
public class NodeReturnChoiceGuard implements org.springframework.statemachine.guard.Guard<NodeStateEnum, NodeStateChangeEnum>  {
    @Override
    public boolean evaluate(StateContext<NodeStateEnum, NodeStateChangeEnum> context) {
        FlowTaskStatusPo flowTaskStatusPo = context.getMessage().getHeaders().get("flowTaskStatusPo", FlowTaskStatusPo.class);
        assert flowTaskStatusPo != null;
        //有无驳回节点,有驳回节点进入驳回节点
        return !StringUtils.isEmpty(flowTaskStatusPo.getRejectNode());
    }
}
