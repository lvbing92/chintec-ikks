package com.chintec.ikks.rabbitmq.service.impl;

import com.chintec.ikks.common.util.ResultResponse;
import com.chintec.ikks.rabbitmq.entity.MessageReq;
import com.chintec.ikks.rabbitmq.mq.MqSendMessage;
import com.chintec.ikks.rabbitmq.service.ISendMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author rubin·lv
 * @version 1.0
 * @date 2020/9/17 14:22
 */
@Service
public class SendMessageServiceImpl implements ISendMessageService {
    @Autowired
    private MqSendMessage mqSendMessage;
    /**
     * 发送消息
     *
     * @param msg 消息体
     * @param timeMills 延迟时间
     * @return ResultResponse
     */
    @Override
    public ResultResponse sendMsg(MessageReq msg, String timeMills) {
        return mqSendMessage.delaySend(msg,timeMills);
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
     *模块和流程信息交互
     *
     * @param msg 消息体
     * @return ResultResponse
     */
    @Override
    public ResultResponse modelMsg(MessageReq msg) {
        return mqSendMessage.modelMsg(msg);
    }
}
