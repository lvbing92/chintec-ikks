package com.chintec.ikks.process.event;

import com.chintec.ikks.common.enums.NodeStateChangeEnum;
import com.chintec.ikks.common.enums.NodeStateEnum;
import com.chintec.ikks.process.entity.po.FlowTaskStatusPo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/9/24 10:02
 */
@Slf4j
@Component
public class SendEvent {
    private static final String STATE_MACHINE_ID = "nodeStateMachine";

    @Autowired
    private StateMachineFactory<NodeStateEnum, NodeStateChangeEnum> nodeStateMachineFactory;

    @Autowired
    private StateMachinePersister<NodeStateEnum, NodeStateChangeEnum, FlowTaskStatusPo> persister;

    /**
     * 一个状态机触发事件的方法
     * 线程安全的
     *
     * @param message        消息体
     * @param flowTaskStatus 内容信息
     * @return boolean true 成功  false 失败
     */
    public boolean sendEvents(Message<NodeStateChangeEnum> message, FlowTaskStatusPo flowTaskStatus) {
        String s = UUID.randomUUID().toString();
        synchronized (s.intern()) {
            boolean result = false;
            StateMachine<NodeStateEnum, NodeStateChangeEnum> nodeStateMachine = nodeStateMachineFactory.getStateMachine(STATE_MACHINE_ID);
            log.info("id={},状态机 nodeStateMachine={}", s, nodeStateMachine);
            try {
                nodeStateMachine.start();
                //尝试恢复状态机状态
                persister.restore(nodeStateMachine, flowTaskStatus);
                log.info("消息id={},id={},状态机 nodeStateMachine id={}", flowTaskStatus.getId(), s, nodeStateMachine.getId());
                result = nodeStateMachine.sendEvent(message);
                //持久化状态机状态
                persister.persist(nodeStateMachine, flowTaskStatus);
                return result;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                nodeStateMachine.stop();
            }
            return !result;
        }
    }
}
