package com.chintec.ikks.message.entity.dto;

import lombok.Data;

/**
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/8/25 10:01
 */
@Data
public class MailSendDTO {
    private String to;
    private String subject;
    private String content;
    private Integer type;
}
