package com.chintec.ikks.rabbitmq.config;

import com.chintec.ikks.rabbitmq.Listtener.KeyExpiredListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;

@Configuration
public class RedisConf {
    @Bean
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory
                                                               redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        //为 string 类型 key 设置序列器
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //为 string 类型 value 设置序列器
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        //为 hash 类型 key 设置序列器
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        //为 hash 类型 value 设置序列器
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }

    @Resource
    private KeyExpiredListener keyExpiredListener;

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(
            @Autowired RedisConnectionFactory redisConnectionFactory) {
        RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
        redisMessageListenerContainer.setConnectionFactory(redisConnectionFactory);
        redisMessageListenerContainer.addMessageListener(keyExpiredListener, new PatternTopic("__keyevent@15__:expired"));
        return redisMessageListenerContainer;
    }

}
