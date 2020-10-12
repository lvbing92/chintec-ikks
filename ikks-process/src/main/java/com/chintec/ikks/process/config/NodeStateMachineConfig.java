package com.chintec.ikks.process.config;

import com.chintec.ikks.common.enums.NodeStateChangeEnum;
import com.chintec.ikks.common.enums.NodeStateEnum;
import com.chintec.ikks.process.entity.po.FlowTaskStatusPo;
import com.chintec.ikks.process.event.action.NodeRefuseFinishAction;
import com.chintec.ikks.process.event.action.NodeReturnChoiceAction;
import com.chintec.ikks.process.event.guard.NodeReturnChoiceGuard;
import com.chintec.ikks.process.event.persister.NodeMachinePersister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.persist.DefaultStateMachinePersister;
import org.springframework.statemachine.persist.StateMachinePersister;

import java.util.EnumSet;

/**
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/9/21 15:13
 */
@Configuration
@EnableStateMachineFactory(name = "nodeStateMachineFactory")
public class NodeStateMachineConfig extends StateMachineConfigurerAdapter<NodeStateEnum, NodeStateChangeEnum> {
    @Autowired
    private NodeMachinePersister nodeMachinePersister;

    @Bean
    public StateMachinePersister<NodeStateEnum, NodeStateChangeEnum, FlowTaskStatusPo> stateMachinePersist() {
        return new DefaultStateMachinePersister<>(nodeMachinePersister);
    }

    @Override
    public void configure(StateMachineStateConfigurer<NodeStateEnum, NodeStateChangeEnum> states) throws Exception {
        states
                .withStates()
                .initial(NodeStateEnum.PENDING)
                .choice(NodeStateEnum.REFUSE)
                .states(EnumSet.allOf(NodeStateEnum.class));
    }

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
                .withChoice().source(NodeStateEnum.REFUSE)
                .first(NodeStateEnum.PENDING, new NodeReturnChoiceGuard(), new NodeReturnChoiceAction())
                .last(NodeStateEnum.REFUSE_FINISH, new NodeRefuseFinishAction());
    }
}
