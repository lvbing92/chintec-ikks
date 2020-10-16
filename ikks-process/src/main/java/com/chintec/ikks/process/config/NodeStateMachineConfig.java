package com.chintec.ikks.process.config;

import com.chintec.ikks.common.enums.NodeStateChangeEnum;
import com.chintec.ikks.common.enums.NodeStateEnum;
import com.chintec.ikks.process.entity.po.FlowTaskStatusPo;
import com.chintec.ikks.process.event.SendEvent;
import com.chintec.ikks.process.event.action.NodeRefuseActionCommon;
import com.chintec.ikks.process.event.guard.NodeReturnChoiceGuard;
import com.chintec.ikks.process.event.persister.NodeMachinePersister;
import com.chintec.ikks.process.service.IFlowNodeService;
import com.chintec.ikks.process.service.IFlowTaskService;
import com.chintec.ikks.process.service.IFlowTaskStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.persist.DefaultStateMachinePersister;
import org.springframework.statemachine.persist.StateMachinePersister;

import java.util.EnumSet;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.LongStream;

/**
 * 状态机配置类 实现了 StateMachineConfigurerAdapter接口
 * 以及开启了EnableStateMachineFactory
 *
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/9/21 15:13
 */
@Configuration
@EnableStateMachineFactory(name = "nodeStateMachineFactory")
public class NodeStateMachineConfig extends StateMachineConfigurerAdapter<NodeStateEnum, NodeStateChangeEnum> {
    @Autowired
    private NodeMachinePersister nodeMachinePersister;

    @Autowired
    private NodeRefuseActionCommon nodeRefuseActionCommon;


    /**
     * 自定义状态机 持久化类
     *
     * @return StateMachinePersister
     */
    @Bean
    public StateMachinePersister<NodeStateEnum, NodeStateChangeEnum, FlowTaskStatusPo> stateMachinePersist() {
        return new DefaultStateMachinePersister<>(nodeMachinePersister);
    }

    /**
     * 状态机 状态配置方法
     * 包含 初始化 状态  choice和 state
     *
     * @param states 配置类
     * @throws Exception 异常
     */
    @Override
    public void configure(StateMachineStateConfigurer<NodeStateEnum, NodeStateChangeEnum> states) throws Exception {
        states
                .withStates()
                .initial(NodeStateEnum.PENDING)
                .choice(NodeStateEnum.REFUSE)
                .states(EnumSet.allOf(NodeStateEnum.class));
    }

    /**
     * 状态机绑定事件驱动的方法
     *
     * @param transitions 状态机事件驱动配置类
     * @throws Exception 异常
     */
    @Override
    public void configure(StateMachineTransitionConfigurer<NodeStateEnum, NodeStateChangeEnum> transitions) throws Exception {
        transitions
                //pending to going  事件:going事件
                .withExternal()
                .source(NodeStateEnum.PENDING).target(NodeStateEnum.GOING)
                .event(NodeStateChangeEnum.GOING)
                .and()
                //going to pass  事件:pass事件
                .withExternal()
                .source(NodeStateEnum.GOING).target(NodeStateEnum.PASS)
                .event(NodeStateChangeEnum.PASS)
                .and()
                //going to refuse  事件:refuse事件
                .withExternal()
                .source(NodeStateEnum.GOING).target(NodeStateEnum.REFUSE)
                .event(NodeStateChangeEnum.REFUSE)
                .and()
                .withChoice()
                .source(NodeStateEnum.REFUSE)
                .first(NodeStateEnum.PENDING, new NodeReturnChoiceGuard(), new NodeReturnChoiceAction())
                .last(NodeStateEnum.REFUSE_FINISH, new NodeRefuseFinishAction());
    }

    /**
     * first 走的 action 方法
     * 具体业务逻辑处理
     */
    private class NodeReturnChoiceAction implements org.springframework.statemachine.action.Action<NodeStateEnum, NodeStateChangeEnum> {
        @Override
        public void execute(StateContext<NodeStateEnum, NodeStateChangeEnum> context) {
            nodeRefuseActionCommon.executeReturn(context);
        }
    }

    /**
     * last 走的 action 方法
     * 具体的业务逻辑处理
     */
    private class NodeRefuseFinishAction implements org.springframework.statemachine.action.Action<NodeStateEnum, NodeStateChangeEnum> {
        @Override
        public void execute(StateContext<NodeStateEnum, NodeStateChangeEnum> context) {
            nodeRefuseActionCommon.execute(context);
        }
    }
}
