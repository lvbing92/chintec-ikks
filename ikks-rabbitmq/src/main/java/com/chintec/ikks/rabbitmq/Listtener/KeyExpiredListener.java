package com.chintec.ikks.rabbitmq.Listtener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/11/5 11:42
 */
@Component
@Slf4j
public class KeyExpiredListener implements MessageListener {
    @Resource
    private RedisTemplate<?, ?> redisTemplate;


    @Override
    public void onMessage(Message message, byte[] pattern) {
        log.info("message {} , pattern {} ", new String(message.getBody()), new String(pattern));

    }
}
