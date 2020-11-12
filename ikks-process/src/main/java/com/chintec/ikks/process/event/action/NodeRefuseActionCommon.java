package com.chintec.ikks.process.event.action;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chintec.ikks.common.entity.FlowNode;
import com.chintec.ikks.common.entity.FlowTask;
import com.chintec.ikks.common.entity.FlowTaskStatus;
import com.chintec.ikks.common.entity.po.FlowTaskStatusPo;
import com.chintec.ikks.common.enums.NodeStateChangeEnum;
import com.chintec.ikks.common.enums.NodeStateEnum;
import com.chintec.ikks.process.event.SendEvent;
import com.chintec.ikks.process.event.function.NodeEventFunction;
import com.chintec.ikks.process.service.IFlowNodeService;
import com.chintec.ikks.process.service.IFlowTaskService;
import com.chintec.ikks.process.service.IFlowTaskStatusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateContext;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * 同样的一个action 公共的方法
 * 用于解耦的
 * 提供了两个action'方法
 *  execute 拒绝后 直接完成
 * executeReturn 拒绝后 返回规定的节点的方法
 *
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/10/12 11:19
 */

@Slf4j
@Component
public class NodeRefuseActionCommon {
    @Autowired
    private SendEvent sendEvent;
    @Autowired
    private IFlowNodeService iFlowNodeService;
    @Autowired
    private IFlowTaskService iFlowTaskService;
    @Autowired
    private IFlowTaskStatusService iFlowTaskStatusService;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void execute(StateContext<NodeStateEnum, NodeStateChangeEnum> context) {
        //拒绝直接完成任务,改变整个任务状态为完成状态
        log.info("finishAction:{}", context);
        FlowTaskStatusPo flowTaskStatusPo = context.getMessage().getHeaders().get("flowTaskStatusPo", FlowTaskStatusPo.class);
        assert flowTaskStatusPo != null;
        FlowTask taskServiceById = iFlowTaskService.getById(flowTaskStatusPo.getData().getTaskId());
        NodeEventFunction.finishTask(taskServiceById, NodeStateEnum.REFUSE.getCode().toString(), "", iFlowTaskService);
        NodeEventFunction.removeTaskMachine(flowTaskStatusPo.getId(), redisTemplate);
    }

    public void executeReturn(StateContext<NodeStateEnum, NodeStateChangeEnum> context) {
        //拒绝->驳回后,改变任务状态为待审核,返回上一个节点,改变上一个节点的任务状态为待审核状态
        log.info("returnAction:{}", context);
        FlowTaskStatusPo flowTaskStatusPo = context.getMessage().getHeaders().get("flowTaskStatusPo", FlowTaskStatusPo.class);
        assert flowTaskStatusPo != null;
        Integer rejectNode = flowTaskStatusPo.getRejectNode();
        NodeEventFunction.removeTaskMachine(flowTaskStatusPo.getId(), redisTemplate);
        log.info("删除当前节点状态机成功{}", "success");
        FlowTaskStatus one = iFlowTaskStatusService.getOne(new QueryWrapper<FlowTaskStatus>()
                .lambda()
                .eq(FlowTaskStatus::getTaskId, flowTaskStatusPo.getData().getTaskId())
                .eq(FlowTaskStatus::getNodeId, rejectNode));
        FlowTask taskServiceById = iFlowTaskService.getById(flowTaskStatusPo.getData().getTaskId());
        FlowNode flowNode = iFlowNodeService.getOne(new QueryWrapper<FlowNode>()
                .lambda()
                .eq(FlowNode::getFlowInformationId, taskServiceById.getFollowInfoId())
                .eq(FlowNode::getNodeId, rejectNode));
        FlowTaskStatus data = flowTaskStatusPo.getData();
        for (int i = rejectNode; i <= data.getNodeId(); i++) {
            //更新返回节点到当前拒绝节点的状态
            if (rejectNode != i && i != data.getNodeId()) {
                FlowTaskStatus flowTaskStatus = iFlowTaskStatusService.getOne(new QueryWrapper<FlowTaskStatus>().lambda().eq(FlowTaskStatus::getNodeId, i).eq(FlowTaskStatus::getTaskId, data.getTaskId()));
                flowTaskStatus.setStatusId(UUID.randomUUID().toString());
                NodeEventFunction.updateTaskStatus(flowTaskStatus, NodeStateEnum.PENDING.getCode().toString(), iFlowTaskStatusService);
                log.info("更新返回节点到当前节点状态成功{}", i);
            } else if (i == data.getNodeId()) {
                data.setStatusId(UUID.randomUUID().toString());
                NodeEventFunction.updateTaskStatus(data, NodeStateEnum.PENDING.getCode().toString(), iFlowTaskStatusService);
                log.info("更新当前节点状态成功{}", data.getNodeId());
            }
        }
        one.setStatusId(UUID.randomUUID().toString());
        flowTaskStatusPo.setData(one);
        flowTaskStatusPo.setIsFinish(Integer.parseInt(flowNode.getNodeType()));
        flowTaskStatusPo.setId(one.getStatusId());
        flowTaskStatusPo.setTime(flowNode.getDelayTime().toString());
        flowTaskStatusPo.setName(one.getName());
        flowTaskStatusPo.setStatus(NodeStateEnum.PENDING);
        Message<NodeStateChangeEnum> message = MessageBuilder.withPayload(NodeStateChangeEnum.GOING).setHeader("flowTaskStatusPo", flowTaskStatusPo).build();
        //返送给上一个节点
        sendEvent.sendEvents(message, flowTaskStatusPo);
        log.info("返回上一个节点成功{}", "success");
    }
}
