package com.chintec.ikks.process.mq;

import com.alibaba.fastjson.JSONObject;
import com.chintec.ikks.common.entity.FlowTaskStatus;
import com.chintec.ikks.common.entity.po.FlowTaskStatusPo;
import com.chintec.ikks.common.entity.po.MessageReq;
import com.chintec.ikks.common.enums.NodeStateChangeEnum;
import com.chintec.ikks.common.util.AssertsUtil;
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

import java.io.IOException;
import java.util.Map;

/**
 * 队列监听类
 *
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
        FlowTaskStatus flowTaskStatus = flowTaskStatusPo.getData();
        try {
            if ("1".equals(flowTaskStatus.getHandleStatus())) {
                org.springframework.messaging.Message<NodeStateChangeEnum> flowTaskStatusM = MessageBuilder.withPayload(NodeStateChangeEnum.PASS).setHeader("flowTaskStatusPo", flowTaskStatusPo).build();
                log.info("进入通过::{}", flowTaskStatus);
                sendEvent.sendEvents(flowTaskStatusM, flowTaskStatusPo);
            } else if ("0".equals(flowTaskStatus.getHandleStatus())) {
                log.info("进入驳回::{}", flowTaskStatus);
                org.springframework.messaging.Message<NodeStateChangeEnum> flowTaskStatusM = MessageBuilder.withPayload(NodeStateChangeEnum.REFUSE).setHeader("flowTaskStatusPo", flowTaskStatusPo).build();
               sendEvent.sendEvents(flowTaskStatusM, flowTaskStatusPo);
            } else {
                AssertsUtil.isTrue(true, "任务操作类型不存在");
            }
            // 手动签收消息,通知mq服务器端删除该消息
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            log.info(e.getMessage());
            try {
                long deliveryTag = message.getMessageProperties().getDeliveryTag();
                channel.basicNack(deliveryTag, false, false);
            } catch (IOException ex) {
                log.info("取消消息失败::{}", ex.getMessage());
            }
        }
    }

}
