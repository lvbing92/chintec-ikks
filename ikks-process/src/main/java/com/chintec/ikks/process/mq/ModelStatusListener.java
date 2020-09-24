package com.chintec.ikks.process.mq;

import com.alibaba.fastjson.JSONObject;
import com.chintec.ikks.common.enums.NodeStateChangeEnum;
import com.chintec.ikks.common.util.AssertsUtil;
import com.chintec.ikks.process.entity.FlowTaskStatus;
import com.chintec.ikks.process.entity.po.FlowTaskStatusPo;
import com.chintec.ikks.process.entity.po.MessageReq;
import com.chintec.ikks.process.event.SendEvent;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/9/17 9:34
 */
@Slf4j
@Component
public class ModelStatusListener {
    @Autowired
    private SendEvent sendEvent;

    @RabbitListener(queues = "message.model.queue")
    @RabbitHandler
    private void processListener(Message message, @Headers Map<String, Object> headers, Channel channel) {
        log.info(new String(message.getBody()));
        MessageReq messageReq = JSONObject.parseObject(new String(message.getBody()), MessageReq.class);
        FlowTaskStatusPo flowTaskStatusPo = JSONObject.parseObject(JSONObject.toJSONString(messageReq.getMessageMsg()), FlowTaskStatusPo.class);
        FlowTaskStatus flowTaskStatus = JSONObject.parseObject(JSONObject.toJSONString(flowTaskStatusPo.getData()), FlowTaskStatus.class);
        if ("1".equals(flowTaskStatus.getHandleStatus())) {
            org.springframework.messaging.Message<NodeStateChangeEnum> flowTaskStatusM = MessageBuilder.withPayload(NodeStateChangeEnum.PASS).setHeader("flowTaskStatusPo", flowTaskStatusPo).build();
            AssertsUtil.isTrue(sendEvent.sendEvents(flowTaskStatusM, flowTaskStatusPo), "任务通过失败");
        } else if ("0".equals(flowTaskStatus.getHandleStatus())) {
            org.springframework.messaging.Message<NodeStateChangeEnum> flowTaskStatusM = MessageBuilder.withPayload(NodeStateChangeEnum.REFUSE).setHeader("flowTaskStatusPo", flowTaskStatusPo).build();
            AssertsUtil.isTrue(sendEvent.sendEvents(flowTaskStatusM, flowTaskStatusPo), "任务驳回失败");
        } else {
            AssertsUtil.isTrue(true, "任务操作类型不存在");
        }
    }
}
