package com.chintec.ikks.rabbitmq;

import com.chintec.ikks.common.entity.po.MessageReq;
import com.chintec.ikks.rabbitmq.mq.MqSendMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class SendMessageTest {
    @Autowired
    private MqSendMessage mqSendMessage;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    void setMqSendMessage() {
        MessageReq messageReq = new MessageReq();
        messageReq.setUuid(UUID.randomUUID().toString());
        messageReq.setMessageMsg("my is 10s");
        mqSendMessage.delaySend(messageReq, "0");

    }

    @Test
    void redisTest() {
        redisTemplate.opsForValue().set("lvbin", "haha", 20, TimeUnit.SECONDS);
    }
}
