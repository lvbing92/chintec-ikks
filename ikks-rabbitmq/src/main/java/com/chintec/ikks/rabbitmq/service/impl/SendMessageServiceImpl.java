package com.chintec.ikks.rabbitmq.service.impl;

import com.chintec.ikks.common.entity.po.MessageReq;
import com.chintec.ikks.common.util.ResultResponse;
import com.chintec.ikks.rabbitmq.mq.MqSendMessage;
import com.chintec.ikks.rabbitmq.service.ISendMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author rubin·lv
 * @version 1.0
 * @date 2020/9/17 14:22
 */
@Service
public class SendMessageServiceImpl implements ISendMessageService {
    @Autowired
    private MqSendMessage mqSendMessage;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 发送消息
     *
     * @param msg       消息体
     * @param timeMills 延迟时间
     * @return ResultResponse
     */
    @Override
    public ResultResponse sendMsg(MessageReq msg, String timeMills) {
        if (!StringUtils.isEmpty(timeMills) && !"0".equals(timeMills)) {
            redisTemplate.opsForValue().set(msg.getUuid(), "延时", Long.parseLong(timeMills), TimeUnit.MILLISECONDS);
            redisTemplate.opsForHash().put("mq", msg.getUuid(), msg);
            return ResultResponse.successResponse();
        }
        timeMills = "0";
        return mqSendMessage.delaySend(msg, timeMills);
    }

    /**
     * 发送邮件
     *
     * @param msg 消息体
     * @return ResultResponse
     */
    @Override
    public ResultResponse sendEmail(MessageReq msg) {
        return mqSendMessage.sendEmail(msg);
    }

    /**
     * 模块和流程信息交互
     *
     * @param msg 消息体
     * @return ResultResponse
     */
    @Override
    public ResultResponse modelMsg(MessageReq msg) {
        return mqSendMessage.modelMsg(msg);
    }
}
