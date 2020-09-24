package com.chintec.ikks.process.event;

import com.alibaba.fastjson.JSONObject;
import com.chintec.ikks.common.enums.NodeStateChangeEnum;
import com.chintec.ikks.common.util.AssertsUtil;
import com.chintec.ikks.common.util.ResultResponse;
import com.chintec.ikks.process.entity.po.FlowTaskStatus;
import com.chintec.ikks.process.entity.po.MessageReq;
import com.chintec.ikks.process.feign.IRabbitMqService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;
import org.springframework.util.StringUtils;

import java.util.UUID;

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

    @OnTransition(source = "PENDING", target = "GOING")
    public void going(Message<NodeStateChangeEnum> message) {
        logger.info("---节点审核中---");
        FlowTaskStatus flowTaskStatus = JSONObject.parseObject(JSONObject.toJSONString(message.getHeaders().get("flowTaskStatus")), FlowTaskStatus.class);
        AssertsUtil.isTrue(!sendMessage(flowTaskStatus).isSuccess(), "节点任务启动失败");
    }

    @OnTransition(source = "GOING", target = "PASS")
    public void pass(Message<NodeStateChangeEnum> message) {
        logger.info("---节点审核通过---");
        FlowTaskStatus flowTaskStatus = JSONObject.parseObject(JSONObject.toJSONString(message.getHeaders().get("flowTaskStatus")), FlowTaskStatus.class);
        Object data = flowTaskStatus.getData();

    }

    @OnTransition(source = "GOING", target = "REFUSE")
    public void refuse(Message<NodeStateChangeEnum> message) {
        logger.info("---节点审核不过---");
        FlowTaskStatus flowTaskStatus = JSONObject.parseObject(JSONObject.toJSONString(message.getHeaders().get("flowTaskStatus")), FlowTaskStatus.class);
        Object data = flowTaskStatus.getData();
    }

    private ResultResponse sendMessage(FlowTaskStatus flowTaskStatus) {
        MessageReq messageReq = new MessageReq();
        messageReq.setUuid(UUID.randomUUID().toString());
        messageReq.setMessageMsg(flowTaskStatus);
        messageReq.setSuccess(true);
        return iRabbitMqService.sendMsg(messageReq, StringUtils.isEmpty(flowTaskStatus.getTime()) ? "" : flowTaskStatus.getTime());
    }
}

