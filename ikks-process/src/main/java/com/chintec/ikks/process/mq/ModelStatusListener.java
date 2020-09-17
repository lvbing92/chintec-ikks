package com.chintec.ikks.process.mq;

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
 * @date 2020/9/17 9:34
 */
@Slf4j
@Component
public class ModelStatusListener {

    @RabbitListener(queues = "message.model.queue")
    @RabbitHandler
    public void processListener(Message message, @Headers Map<String, Object> headers, Channel channel) {
        log.info(new String(message.getBody()));
        //TODO 监听业务流程状态，驱动流程进行，以及判断流程是否完成
    }
}
