package com.chintec.ikks.process.event.action;

import com.alibaba.fastjson.JSONObject;
import com.chintec.ikks.common.entity.FlowTask;
import com.chintec.ikks.common.entity.FlowTaskStatus;
import com.chintec.ikks.common.entity.po.FlowTaskStatusPo;
import com.chintec.ikks.common.entity.vo.NodeFunctionVo;
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
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/11/13 16:09
 */
@Slf4j
@Component
public class NodeChoiceAction {
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

    public void pass(StateContext<NodeStateEnum, NodeStateChangeEnum> context) {
        log.info("---节点审核通过---节点::{}", context);
        FlowTaskStatusPo flowTaskStatusPo = JSONObject.parseObject(JSONObject.toJSONString(context.getMessage().getHeaders().get("flowTaskStatusPo")), FlowTaskStatusPo.class);
        FlowTaskStatus flowTaskStatus = flowTaskStatusPo.getData();
        //更新节点任务状态
        NodeEventFunction.updateTaskStatus(flowTaskStatus, NodeStateEnum.PASS.getCode().toString(), iFlowTaskStatusService);
        FlowTask byId = iFlowTaskService.getById(flowTaskStatus.getTaskId());
        List<NodeFunctionVo> nodeIds = flowTaskStatusPo.getNodeIds();
        log.info("next :{}", nodeIds);
        if (CollectionUtils.isEmpty(nodeIds)) {
            //没有下一个节点方法直接完成
            log.info("完成任务,改变任务状态::{}", flowTaskStatusPo.getName());
            NodeEventFunction.finishTask(byId, NodeStateEnum.PASS.getCode().toString(), flowTaskStatus.getUpdataBy(), iFlowTaskService);
        } else {
            //根据上一个任务返回的结果 过滤出符合目标的下一个节点
            nodeIds.stream()
                    .filter(nodeFunctionVo -> nodeFunctionVo.getStatus().equals(flowTaskStatusPo.getTaskStatus()))
                    .forEach(nodeFunctionVo -> {
                        //有下一个节点进入下一个节点
                        log.info("进入下一个节点::{}", nodeFunctionVo.getNextNode());
                        NodeEventFunction.nextTask(nodeFunctionVo.getNextNode(), byId.getFollowInfoId(), flowTaskStatus.getTaskId(), iFlowTaskStatusService, iFlowNodeService, sendEvent);
                    });
        }
    }

    public void refuse(StateContext<NodeStateEnum, NodeStateChangeEnum> context) {
        log.info("进入拒绝");
        FlowTaskStatusPo flowTaskStatusPo = JSONObject.parseObject(JSONObject.toJSONString(context.getMessage().getHeaders().get("flowTaskStatusPo")), FlowTaskStatusPo.class);
        Message<NodeStateChangeEnum> message = MessageBuilder.withPayload(NodeStateChangeEnum.REFUSE).setHeader("flowTaskStatusPo", flowTaskStatusPo).build();
        sendEvent.sendEvents(message, flowTaskStatusPo);
    }
}
