package com.chintec.ikks.process.event.action;

import com.chintec.ikks.common.enums.NodeStateChangeEnum;
import com.chintec.ikks.common.enums.NodeStateEnum;
import org.springframework.statemachine.StateContext;

/**
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/10/12 11:18
 */
public class NodeReturnChoiceAction implements org.springframework.statemachine.action.Action<NodeStateEnum, NodeStateChangeEnum> {
    @Override
    public void execute(StateContext<NodeStateEnum, NodeStateChangeEnum> context) {
        //拒绝->驳回后,改变任务状态为待审核,返回上一个节点,改变上一个节点的任务状态为待审核状态
    }
}
