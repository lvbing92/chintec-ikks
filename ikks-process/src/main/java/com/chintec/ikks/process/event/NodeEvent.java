package com.chintec.ikks.process.event;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chintec.ikks.common.enums.NodeStateChangeEnum;
import com.chintec.ikks.common.enums.NodeStateEnum;
import com.chintec.ikks.common.util.AssertsUtil;
import com.chintec.ikks.process.entity.FlowNode;
import com.chintec.ikks.process.entity.FlowTask;
import com.chintec.ikks.process.entity.FlowTaskStatus;
import com.chintec.ikks.process.entity.po.FlowTaskStatusPo;
import com.chintec.ikks.process.entity.po.MessageReq;
import com.chintec.ikks.process.entity.vo.NodeFunctionVo;
import com.chintec.ikks.process.feign.IRabbitMqService;
import com.chintec.ikks.process.service.IFlowNodeService;
import com.chintec.ikks.process.service.IFlowTaskService;
import com.chintec.ikks.process.service.IFlowTaskStatusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
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
        FlowTaskStatusPo flowTaskStatusPo = JSONObject.parseObject(JSONObject.toJSONString(message.getHeaders().get("flowTaskStatusPo")), FlowTaskStatusPo.class);
        FlowTaskStatus flowTaskStatus = JSONObject.parseObject(JSONObject.toJSONString(flowTaskStatusPo.getData()), FlowTaskStatus.class);
        //更新任务装填
        updateTaskStatus(flowTaskStatus, NodeStateEnum.GOING.getCode().toString());
        sendMessage(flowTaskStatusPo);
    }

    /**
     * 通过事件
     *
     * @param message 消息
     */
    @OnTransition(source = "GOING", target = "PASS")
    public void pass(Message<NodeStateChangeEnum> message) {
        logger.info("---节点审核通过---节点::{}", message);
        FlowTaskStatusPo flowTaskStatusPo = JSONObject.parseObject(JSONObject.toJSONString(message.getHeaders().get("flowTaskStatusPo")), FlowTaskStatusPo.class);
        FlowTaskStatus flowTaskStatus = flowTaskStatusPo.getData();
        //更新节点任务状态
        updateTaskStatus(flowTaskStatus, NodeStateEnum.PASS.getCode().toString());
        FlowTask byId = iFlowTaskService.getById(flowTaskStatus.getTaskId());
        List<NodeFunctionVo> nodeIds = flowTaskStatusPo.getNodeIds();
        if (CollectionUtils.isEmpty(nodeIds)) {
            //没有下一个节点方法直接完成
            logger.info("完成任务,改变任务状态::{}", flowTaskStatusPo.getName());
            finishTask(byId, NodeStateEnum.PASS.getCode().toString(), flowTaskStatus.getUpdataBy());
        } else {
            //根据上一个任务返回的结果 过滤出符合目标的下一个节点
            nodeIds.stream()
                    .filter(nodeFunctionVo -> nodeFunctionVo.getStatus().equals(flowTaskStatusPo.getTaskStatus()))
                    .forEach(nodeFunctionVo -> {
                        //有下一个节点进入下一个节点
                        logger.info("进入下一个节点::{}", nodeFunctionVo.getNextNode());
                        nextTask(nodeFunctionVo.getNextNode(), byId.getFollowInfoId(), flowTaskStatus.getTaskId());
                    });
        }
        removeTaskMachine(flowTaskStatusPo.getId());
    }

    /**
     * 拒绝事件
     *
     * @param message 消息
     */
    @OnTransition(source = "GOING", target = "REFUSE")
    public void refuse(Message<NodeStateChangeEnum> message) {
        logger.info("---节点审核不过---::{}", message);
        FlowTaskStatusPo flowTaskStatusPo = JSONObject.parseObject(JSONObject.toJSONString(message.getHeaders().get("flowTaskStatusPo")), FlowTaskStatusPo.class);
        FlowTaskStatus flowTaskStatus = flowTaskStatusPo.getData();
        updateTaskStatus(flowTaskStatus, NodeStateEnum.REFUSE.getCode().toString());
        FlowTask byId = iFlowTaskService.getById(flowTaskStatus.getTaskId());
        if (StringUtils.isEmpty(flowTaskStatusPo.getRejectNode())) {
            finishTask(byId, NodeStateEnum.REFUSE.getCode().toString(), flowTaskStatus.getUpdataBy());
        } else {
            nextTask(flowTaskStatusPo.getRejectNode(), byId.getFollowInfoId(), flowTaskStatus.getTaskId());
        }
        removeTaskMachine(flowTaskStatusPo.getId());
    }

    /**
     * 发送消息给对应的人员
     * 以及修改 task属性为可见
     *
     * @param flowTaskStatus 任务状态
     */
    private void sendMessage(FlowTaskStatusPo flowTaskStatus) {
        MessageReq messageReq = new MessageReq();
        messageReq.setUuid(UUID.randomUUID().toString());
        messageReq.setMessageMsg(flowTaskStatus);
        messageReq.setSuccess(true);
        iRabbitMqService.sendMsg(messageReq, StringUtils.isEmpty(flowTaskStatus.getTime()) ? "" : flowTaskStatus.getTime());
    }

    /**
     * 当任务完成或是返回上一个节点的时候,删除该条记录缓存
     * 并将此次执行结果记录到数据库中去
     *
     * @param id 任务缓存id
     */
    private void removeTaskMachine(String id) {
        //TODO 执行结果记录到 数据库中 然后删除缓存

        Boolean delete = redisTemplate.delete(id);
        AssertsUtil.isTrue(!(delete == null ? false : delete), "删除失败任务缓存失败");
    }

    /**
     * 下个节点的公共方法
     *
     * @param nodeId 节点id
     */
    private void nextTask(Integer nodeId, Integer flowInfoId, Integer taskId) {
        FlowTaskStatusPo flowTaskStatusPo = new FlowTaskStatusPo();
        //根据节点信息查询节点id
        FlowNode node = iFlowNodeService.getOne(new QueryWrapper<FlowNode>()
                .lambda()
                .eq(FlowNode::getNodeId, nodeId)
                .eq(FlowNode::getFlowInformationId, flowInfoId));
        List<Integer> integers = JSONObject.parseArray(node.getProveNodes(), Integer.class);
        List<FlowTaskStatus> collect = null;
        //查看该节点的前置节点完成情况,过滤出进行中的
        if ("1".equals(node.getNodeExc())) {
            collect = iFlowTaskStatusService.list(new QueryWrapper<FlowTaskStatus>()
                    .lambda()
                    .in(FlowTaskStatus::getNodeId, integers)
                    .eq(FlowTaskStatus::getTaskId, taskId))
                    .stream()
                    .filter(flowTaskStatus -> flowTaskStatus.getStatus().equals(NodeStateEnum.GOING.getCode().toString()))
                    .collect(Collectors.toList());
        }
        //判断前置节点是否有进行中的 如果没有,开启该节点任务
        if (CollectionUtils.isEmpty(collect)) {
            flowTaskStatusPo.setTime(StringUtils.isEmpty(node.getDelayTime()) ? "" : node.getDelayTime() * 3600 * 1000 + "");
            flowTaskStatusPo.setName(node.getNodeName());
            flowTaskStatusPo.setIsFinish(StringUtils.isEmpty(node.getNodeType()) ? 2 : Integer.parseInt(node.getNodeType()));
            flowTaskStatusPo.setRejectNode(node.getRejectNode());
            flowTaskStatusPo.setStatus(NodeStateEnum.PENDING);
            FlowTaskStatus one = iFlowTaskStatusService.getOne(new QueryWrapper<FlowTaskStatus>()
                    .lambda()
                    .eq(FlowTaskStatus::getNodeId, nodeId)
                    .eq(FlowTaskStatus::getTaskId, taskId));
            flowTaskStatusPo.setId(one.getStatusId());
            flowTaskStatusPo.setData(one);
            Message<NodeStateChangeEnum> message1 = MessageBuilder.withPayload(NodeStateChangeEnum.GOING).setHeader("flowTaskStatusPo", flowTaskStatusPo).build();
            AssertsUtil.isTrue(!sendEvent.sendEvents(message1, flowTaskStatusPo), "节点任务开启失败:" + flowTaskStatusPo.getName());
        }
    }

    /**
     * 完成任务的公共方法
     *
     * @param byId   任务状态类
     * @param status 状态
     */
    private void finishTask(FlowTask byId, String status, String name) {
        byId.setStatus(status);
        byId.setAuditTime(LocalDateTime.now());
        byId.setUpdateTime(LocalDateTime.now());
        byId.setUpdataBy(name);
        AssertsUtil.isTrue(!iFlowTaskService.saveOrUpdate(byId), "完成任务失败");
    }

    /**
     * 更新节点任务状态
     *
     * @param flowTaskStatus f
     * @param status         s
     */
    private void updateTaskStatus(FlowTaskStatus flowTaskStatus, String status) {
        flowTaskStatus.setStatus(status);
        flowTaskStatus.setUpdateTime(LocalDateTime.now());
        AssertsUtil.isTrue(!iFlowTaskStatusService.saveOrUpdate(flowTaskStatus), "初始化失败");
    }

}

