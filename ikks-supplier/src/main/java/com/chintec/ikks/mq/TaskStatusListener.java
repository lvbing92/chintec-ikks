package com.chintec.ikks.mq;

import com.alibaba.fastjson.JSONObject;
import com.chintec.ikks.common.entity.po.MessageReq;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/10/19 9:33
 */
@Slf4j
@Component
public class TaskStatusListener {
    @RabbitListener(queues = "message.queue")
    @RabbitHandler
    public void taskStatusListener(Message message, @Headers Map<String, Object> headers, Channel channel) {
        log.info(new String(message.getBody()));
        MessageReq messageReq = JSONObject.parseObject(new String(message.getBody()), MessageReq.class);

    }
}
