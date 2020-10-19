package com.chintec.ikks.rabbitmq.service;

import com.chintec.ikks.common.entity.po.MessageReq;
import com.chintec.ikks.common.util.ResultResponse;

/**
 * @author rubin·lv
 * @version 1.0
 * @date 2020年9月17日14
 */
public interface ISendMessageService {

    /**
     * 发送消息
     *
     * @param msg 消息体
     * @param timeMills 延迟时间
     * @return ResultResponse
     */
    ResultResponse sendMsg(MessageReq msg, String timeMills);
    /**
     * 发送邮件
     *
     * @param msg 消息体
     * @return ResultResponse
     */
    ResultResponse sendEmail(MessageReq msg);

    /**
     *模块和流程信息交互
     *
     * @param msg 消息体
     * @return ResultResponse
     */
    ResultResponse modelMsg(MessageReq msg);
}
