package com.chintec.ikks.process.config;

import com.chintec.ikks.common.enums.NodeStateChangeEnum;
import com.chintec.ikks.common.enums.NodeStateEnum;
import com.chintec.ikks.process.entity.po.FlowTaskStatus;
import com.chintec.ikks.process.event.NodeMachinePersister;
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
    public StateMachinePersister<NodeStateEnum, NodeStateChangeEnum, FlowTaskStatus> stateMachinePersist() {
        return new DefaultStateMachinePersister<>(nodeMachinePersister);
    }

    @Override
    public void configure(StateMachineStateConfigurer<NodeStateEnum, NodeStateChangeEnum> states) throws Exception {
        states
                .withStates()
                .initial(NodeStateEnum.PENDING)
                .states(EnumSet.allOf(NodeStateEnum.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<NodeStateEnum, NodeStateChangeEnum> transitions) throws Exception {
        transitions
                .withExternal()
                .source(NodeStateEnum.PENDING).target(NodeStateEnum.GOING).event(NodeStateChangeEnum.GOING)
                .and()
                .withExternal()
                .source(NodeStateEnum.GOING).target(NodeStateEnum.PASS).event(NodeStateChangeEnum.PASS)
                .and()
                .withExternal()
                .source(NodeStateEnum.GOING).target(NodeStateEnum.REFUSE).event(NodeStateChangeEnum.REFUSE);
    }


}
