package com.chintec.ikks.rabbitmq.Listtener;

import com.alibaba.fastjson.JSONObject;
import com.chintec.ikks.common.entity.po.MessageReq;
import com.chintec.ikks.common.util.AssertsUtil;
import com.chintec.ikks.rabbitmq.mq.MqSendMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/11/5 11:42
 */
@Component
@Slf4j
public class KeyExpiredListener implements MessageListener {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private MqSendMessage mqSendMessage;


    @Override
    public void onMessage(Message message, byte[] pattern) {
        HashOperations<String, Object, Object> stringObjectObjectHashOperations = redisTemplate.opsForHash();
        String s = new String(message.getBody());
        log.info("message {} , pattern {} ", s, new String(pattern));
        Object mq = stringObjectObjectHashOperations.get("mq", s);
        mqSendMessage.delaySend(JSONObject.parseObject(JSONObject.toJSONString(mq), MessageReq.class), "0");
        Long delete = stringObjectObjectHashOperations.delete("mq", s);
        log.info("受影响的数据行数 {}", delete);
        AssertsUtil.isTrue(delete < 1, "删除失败");
        printRedisMessage(stringObjectObjectHashOperations);
    }

    private void printRedisMessage(HashOperations<String, Object, Object> stringObjectObjectHashOperations) {
        log.info("=============================================");
        log.info("剩余的数据 size: {}", stringObjectObjectHashOperations.size("mq"));
        Map<Object, Object> mq = stringObjectObjectHashOperations.entries("mq");
        mq.forEach((s, v) -> {
            Long expire = redisTemplate.getExpire(String.valueOf(s));
            if (expire > 0)
                log.info("剩余的数据: {}:{},剩余时效:{}", s, v, expire);
        });
        log.info("=============================================");
    }
}
