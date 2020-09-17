package com.chintec.ikks.process.entity.po;

import lombok.Data;

import java.io.Serializable;

/**
 * @author rubin·lv
 * @version 1.0
 * @date 2020年9月17日14
 */
@Data
public class MessageReq implements Serializable {
    /**
     * 消息唯一Id
     */
    private String uuid;
    /**
     * 消息内容
     */
    private Object messageMsg;

}
