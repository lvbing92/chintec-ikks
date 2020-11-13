package com.chintec.ikks.process.event;

import com.alibaba.fastjson.JSONObject;
import com.chintec.ikks.common.entity.FlowTask;
import com.chintec.ikks.common.entity.FlowTaskStatus;
import com.chintec.ikks.common.entity.po.FlowTaskStatusPo;
import com.chintec.ikks.common.entity.vo.NodeFunctionVo;
import com.chintec.ikks.common.enums.NodeStateChangeEnum;
import com.chintec.ikks.common.enums.NodeStateEnum;
import com.chintec.ikks.process.event.function.NodeEventFunction;
import com.chintec.ikks.process.feign.IRabbitMqService;
import com.chintec.ikks.process.service.IFlowNodeService;
import com.chintec.ikks.process.service.IFlowTaskService;
import com.chintec.ikks.process.service.IFlowTaskStatusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.Message;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * 事件类
 * 根绝@OnTransition注解来实现对应的状态驱动
 * 分为:进行中事件 通过事件 和 拒绝事件三个方法
 *
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/9/22 14:23
 */
@WithStateMachine(id = "nodeStateMachine")
public class NodeEvent {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private IRabbitMqService iRabbitMqService;
    @Autowired
    private IFlowTaskService iFlowTaskService;
    @Autowired
    private IFlowTaskStatusService iFlowTaskStatusService;
    @Autowired
    private IFlowNodeService iFlowNodeService;
    @Autowired
    private SendEvent sendEvent;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;


    /**
     * 进行中事件
     *
     * @param message 消息
     */
    @OnTransition(source = "PENDING", target = "GOING")
    public void going(Message<NodeStateChangeEnum> message) {
        logger.info("---节点审核中---节点::{}", message);
        //消息体取出信息
        FlowTaskStatusPo flowTaskStatusPo = message.getHeaders().get("flowTaskStatusPo", FlowTaskStatusPo.class);
        assert flowTaskStatusPo != null;
        flowTaskStatusPo.setStatus(NodeStateEnum.GOING);
        FlowTaskStatus flowTaskStatus = flowTaskStatusPo.getData();
        //更新任务状态
        logger.info("更新任务状态");
        flowTaskStatus.setStatusId(flowTaskStatusPo.getId());
        NodeEventFunction.updateTaskStatus(flowTaskStatus, NodeStateEnum.GOING.getCode().toString(), iFlowTaskStatusService);
        logger.info("自动完成");
        NodeEventFunction.autoFinishTask(flowTaskStatusPo, iFlowNodeService, iFlowTaskService, iRabbitMqService, iFlowTaskStatusService);
    }

    /**
     * 通过事件
     *
     * @param message 消息
     */
    @OnTransition(source = "GOING", target = "CHOICE_ONE")
    public void choiceOne(Message<NodeStateChangeEnum> message) {
        logger.info("节点选择时间:{}", message);
    }

    /**
     * 拒绝事件
     *
     * @param message 消息
     */
    @OnTransition(source = "REFUSE", target = "CHOICE_TWO")
    public void choiceTwo(Message<NodeStateChangeEnum> message) {
        logger.info("---节点审核不过---::{}", message);
        FlowTaskStatusPo flowTaskStatusPo = JSONObject.parseObject(JSONObject.toJSONString(message.getHeaders().get("flowTaskStatusPo")), FlowTaskStatusPo.class);
        FlowTaskStatus flowTaskStatus = flowTaskStatusPo.getData();
        NodeEventFunction.updateTaskStatus(flowTaskStatus, NodeStateEnum.REFUSE.getCode().toString(), iFlowTaskStatusService);
//        FlowTask byId = iFlowTaskService.getById(flowTaskStatus.getTaskId());
//        if (StringUtils.isEmpty(flowTaskStatusPo.getRejectNode())) {
//            finishTask(byId, NodeStateEnum.REFUSE.getCode().toString(), flowTaskStatus.getUpdataBy());
//        } else {
//            nextTask(flowTaskStatusPo.getRejectNode(), byId.getFollowInfoId(), flowTaskStatus.getTaskId());
//        }
//        removeTaskMachine(flowTaskStatusPo.getId());
    }

}

