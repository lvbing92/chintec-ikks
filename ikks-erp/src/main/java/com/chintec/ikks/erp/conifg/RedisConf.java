package com.chintec.ikks.erp.conifg;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

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

}
