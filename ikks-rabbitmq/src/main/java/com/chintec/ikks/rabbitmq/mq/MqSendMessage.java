package com.chintec.ikks.rabbitmq.mq;

import com.alibaba.fastjson.JSONObject;
import com.chintec.ikks.rabbitmq.entity.MessageReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/8/20 11:12
 */
@Slf4j
@Component
public class MqSendMessage {
    public static final String DELAY_EXCHANGE_NAME = "delay.exchange";
    public static final String DELAY_ROUTING_KEY = "delay.routing.key";
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 延迟消息发送
     *
     * @param msg       消息内容
     * @param timeMills
     */
    public void delaySend(MessageReq msg, String timeMills) {

        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setExpiration(timeMills);
        messageProperties.setCorrelationId(String.valueOf(UUID.randomUUID().toString().getBytes()));
        Message message = new Message(JSONObject.toJSONBytes(msg), messageProperties);
        rabbitTemplate.setReturnCallback((backMessage, replyCode, replyText, exchange, routingKey) -> {
            log.info("被退回的消息为：{}", backMessage);
            log.info("replyCode：{}", replyCode);
            log.info("replyText：{}", replyText);
            log.info("exchange：{}", exchange);
            log.info("routingKey：{}", routingKey);
        });
        rabbitTemplate.convertAndSend(DELAY_EXCHANGE_NAME, "delay.queue.routing.key", message);
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                log.info("CorrelationData content : " + correlationData);
                log.info("Ack status : " + ack);
                log.info("Cause content : " + cause);
                if (ack) {
                    log.info("消息成功发送");
                } else {
                    log.info("消息发送失败：" + correlationData + ", 出现异常：" + cause);
                }
            }
        });
    }

}