package com.chintec.ikks.process.event;

import com.alibaba.fastjson.JSONObject;
import com.chintec.ikks.common.enums.NodeStateChangeEnum;
import com.chintec.ikks.common.enums.NodeStateEnum;
import com.chintec.ikks.process.entity.po.FlowTaskStatus;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/9/23 16:14
 */
@Component
public class NodeMachinePersister implements StateMachinePersist<NodeStateEnum, NodeStateChangeEnum, FlowTaskStatus> {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 节点状态机ID
     */
    private static final String NODE_STATE_MACHINE_ID = "nodeStateMachine";

    @Override
    public void write(StateMachineContext<NodeStateEnum, NodeStateChangeEnum> context, FlowTaskStatus contextObj)  {
        redisTemplate.opsForValue().set(contextObj.getId(), context.getState());
    }

    @Override
    public StateMachineContext<NodeStateEnum, NodeStateChangeEnum> read(FlowTaskStatus contextObj) {
        return redisTemplate.hasKey(contextObj.getId()) ? new DefaultStateMachineContext<>(JSONObject.parseObject(JSONObject.toJSONString(redisTemplate.opsForValue().get(contextObj.getId())), NodeStateEnum.class), null, null, null, null, NODE_STATE_MACHINE_ID) : new DefaultStateMachineContext<>(contextObj.getStatus(), null, null, null, null, NODE_STATE_MACHINE_ID);
    }
}
