package com.chintec.ikks.process.event;

import com.chintec.ikks.common.enums.NodeStateChangeEnum;
import com.chintec.ikks.common.enums.NodeStateEnum;
import com.chintec.ikks.process.entity.po.FlowTaskStatus;
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
    private StateMachinePersister<NodeStateEnum, NodeStateChangeEnum, FlowTaskStatus> persister;

    public boolean sendEvents(Message<NodeStateChangeEnum> message, FlowTaskStatus flowTaskStatus) {
        String s = UUID.randomUUID().toString();
        synchronized (s.intern()) {
            boolean result = false;
            StateMachine<NodeStateEnum, NodeStateChangeEnum> nodeStateMachine = nodeStateMachineFactory.getStateMachine(STATE_MACHINE_ID);
            log.info("id=" + s + " 状态机 nodeStateMachine" + nodeStateMachine);
            try {
                nodeStateMachine.start();
                //尝试恢复状态机状态
                persister.restore(nodeStateMachine, flowTaskStatus);
                log.info("消息id=" + flowTaskStatus.getId() + "id" + s + " 状态机 nodeStateMachine id=" + nodeStateMachine.getId());
                //添加延迟用于线程安全测试
                Thread.sleep(1000);
                result = nodeStateMachine.sendEvent(message);
                //持久化状态机状态
                persister.persist(nodeStateMachine, flowTaskStatus);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                nodeStateMachine.stop();
            }
            return !result;
        }
    }
}
