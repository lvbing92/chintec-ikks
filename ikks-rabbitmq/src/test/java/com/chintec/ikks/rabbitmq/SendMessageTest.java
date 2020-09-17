package com.chintec.ikks.rabbitmq;

import com.chintec.ikks.rabbitmq.entity.MessageReq;
import com.chintec.ikks.rabbitmq.mq.MqSendMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
public class SendMessageTest {
    @Autowired
    private MqSendMessage mqSendMessage;
    @Test
    public void setMqSendMessage(){
        MessageReq messageReq = new MessageReq();
        messageReq.setExpiration("5s");
        messageReq.setUuid(UUID.randomUUID().toString());
        messageReq.setMessageMsg("5s letter message");
        mqSendMessage.delaySend(messageReq, "50000");
    }
}
