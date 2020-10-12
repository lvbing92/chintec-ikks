package com.chintec.ikks.process.event.action;

import com.chintec.ikks.common.enums.NodeStateChangeEnum;
import com.chintec.ikks.common.enums.NodeStateEnum;
import org.springframework.statemachine.StateContext;

/**
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/10/12 11:19
 */
public class NodeRefuseFinishAction  implements org.springframework.statemachine.action.Action<NodeStateEnum, NodeStateChangeEnum>  {
    @Override
    public void execute(StateContext<NodeStateEnum, NodeStateChangeEnum> context) {
        //拒绝直接完成任务,改变整个任务状态为完成状态
    }
}
