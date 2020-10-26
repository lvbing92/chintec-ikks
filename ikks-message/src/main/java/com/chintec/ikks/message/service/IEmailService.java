package com.chintec.ikks.message.service;

import com.chintec.ikks.common.entity.dto.MailSendDTO;
import com.chintec.ikks.common.util.ResultResponse;

import java.util.Map;

/**
 * 此接口提供邮件发送
 *
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/8/25 9:13
 */
public interface IEmailService {
    /**
     * 发送普通邮件
     *
     * @param mailSendDTO 邮件类
     * @return ResultResponse
     */
    ResultResponse sendSimpleMailMessage(MailSendDTO mailSendDTO);

    /**
     * 发送 HTML 邮件
     *
     * @param to      收件人
     * @param subject 主题
     * @param content 内容
     * @return ResultResponse
     */
    ResultResponse sendMimeMessage(String to, String subject, String content);

    /**
     * 发送带附件的邮件
     *
     * @param to       收件人
     * @param subject  主题
     * @param content  内容
     * @param filePath 附件路径
     * @return ResultResponse
     */
    ResultResponse sendMimeMessage(String to, String subject, String content, String filePath);

    /**
     * 发送带静态文件的邮件
     *
     * @param to       收件人
     * @param subject  主题
     * @param content  内容
     * @param rscIdMap 需要替换的静态文件
     * @return ResultResponse
     */
    ResultResponse sendMimeMessage(String to, String subject, String content, Map<String, String> rscIdMap);
}

