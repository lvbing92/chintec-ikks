package com.chintec.ikks.rabbitmq.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class MessageReq implements Serializable {
    /**
     * 消息唯一Id
     */
    private String uuid;
    /**
     * 过期时间（毫秒）
     */
    private String expiration;
    /**
     * 消息内容
     */
    private String messageMsg;

}
