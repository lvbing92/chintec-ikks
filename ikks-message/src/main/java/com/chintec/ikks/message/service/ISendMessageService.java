package com.chintec.ikks.message.service;

import com.chintec.ikks.common.util.ResultResponse;
import com.chintec.ikks.message.entity.MessageRec;

/**
 * 此类提供消息提示，包含订单提示，后台消息推送等信息
 *
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/8/24 14:35
 */
public interface ISendMessageService {
    /**
     * 向某个用户推送消息
     *
     * @param messageRec 消息类型
     * @return 返回结果
     */
    ResultResponse sendMessages(MessageRec messageRec);
}
