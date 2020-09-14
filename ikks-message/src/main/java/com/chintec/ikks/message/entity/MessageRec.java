package com.chintec.ikks.message.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/8/20 10:53
 */
@Data
public class MessageRec implements Serializable {
    /**
     * 消息类型0 是消息推送 1 为短信发送
     */
    private Integer type;
    /**
     * 消息内容
     */
    private String message;
    /**
     * 用户id集合
     */
    private List<Integer> userIds;

    private Object mailSendDTO;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Integer> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Integer> userIds) {
        this.userIds = userIds;
    }

    public Object getMailSendDTO() {
        return mailSendDTO;
    }

    public void setMailSendDTO(Object mailSendDTO) {
        this.mailSendDTO = mailSendDTO;
    }
}
