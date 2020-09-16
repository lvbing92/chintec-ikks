package com.chintec.ikks.process.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
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
    public static final String DELAY_ROUTING_KEY = "delay_routing_key";
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(String phone) {

        String msg = "hello word";
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setExpiration("60000");
        messageProperties.setCorrelationId(String.valueOf(UUID.randomUUID().toString().getBytes()));
        Message message = new Message(msg.getBytes(), messageProperties);
        rabbitTemplate.convertAndSend(DELAY_EXCHANGE_NAME, DELAY_ROUTING_KEY,message);
    }

}