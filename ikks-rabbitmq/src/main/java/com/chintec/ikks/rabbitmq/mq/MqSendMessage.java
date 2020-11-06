package com.chintec.ikks.rabbitmq.mq;

import com.alibaba.fastjson.JSONObject;
import com.chintec.ikks.common.entity.po.MessageReq;
import com.chintec.ikks.common.util.AssertsUtil;
import com.chintec.ikks.common.util.MqVariableUtil;
import com.chintec.ikks.common.util.ResultResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.UUID;

/**
 * @author rubin·lv
 * @version 1.0
 * @date 2020年9月17日14
 */
@Slf4j
@Component
public class MqSendMessage {

    @Autowired
    public  RabbitTemplate rabbitTemplate;



    /**
     * 延迟消息发送
     *
     * @param msg       消息内容
     * @param timeMills
     */
    public  ResultResponse delaySend(MessageReq msg, String timeMills) {

        MessageProperties messageProperties = new MessageProperties();
        if (!StringUtils.isEmpty(timeMills)) {
            messageProperties.setExpiration(timeMills);
        }
        messageProperties.setCorrelationId(Arrays.toString(UUID.randomUUID().toString().getBytes()));
        Message message = new Message(JSONObject.toJSONBytes(msg), messageProperties);
        log.info("message {}",message);
        rabbitTemplate.setReturnCallback((backMessage, replyCode, replyText, exchange, routingKey) -> {
            log.info("被退回的消息为：{}", backMessage);
            log.info("replyCode：{}", replyCode);
            log.info("replyText：{}", replyText);
            log.info("exchange：{}", exchange);
            log.info("routingKey：{}", routingKey);
        });
        rabbitTemplate.convertAndSend(MqVariableUtil.DELAY_EXCHANGE_NAME, MqVariableUtil.DELAY_ROUTING_KEY, message);
        confirmCallback();
        return ResultResponse.successResponse();
    }

    /**
     * 邮件发送
     *
     * @param msg 消息体
     * @return ResultResponse
     */
    public  ResultResponse sendEmail(MessageReq msg) {
        rabbitTemplate.setMandatory(true);
        CorrelationData correlationId = new CorrelationData(String.valueOf(UUID.randomUUID()));
        rabbitTemplate.convertAndSend(MqVariableUtil.TOPIC_EXCHANGE_NAME, MqVariableUtil.TOPIC_WITH_ROUTING_KEY, JSONObject.toJSONString(msg), correlationId);
        /*
        returnCallBack 当前队列无效或是被解绑的时候执行里面操作
         */
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            log.info(Arrays.toString(message.getBody()));
        });

        /*
        确认回调机制当 ack为false的时候会再次向mq发送消息
         */
        confirmCallback();
        return ResultResponse.successResponse();
    }

    /**
     * 模块和流程交互信息
     *
     * @param msg 消息体
     * @return ResultResponse
     */
    public  ResultResponse modelMsg(MessageReq msg) {
        rabbitTemplate.setMandatory(true);
        CorrelationData correlationId = new CorrelationData(String.valueOf(UUID.randomUUID()));
        rabbitTemplate.convertAndSend(MqVariableUtil.TOPIC_EXCHANGE_NAME, MqVariableUtil.MESSAGE_MODEL_ROUTING_KEY, JSONObject.toJSONString(msg), correlationId);
        /*
        returnCallBack 当前队列无效或是被解绑的时候执行里面操作
         */
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            log.info(Arrays.toString(message.getBody()));
        });

        /*
        确认回调机制当 ack为false的时候会再次向mq发送消息
         */
        confirmCallback();
        return ResultResponse.successResponse();
    }

    private void confirmCallback(){
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                log.info("Ack status : " + ack);
                log.info("Cause content : " + cause);
                AssertsUtil.isTrue(!ack, "消息发送失败" + cause);
                if (ack) {
                    log.info("消息成功发送");
                }
            }
        });
    }
}