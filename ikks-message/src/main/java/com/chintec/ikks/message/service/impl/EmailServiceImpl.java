package com.chintec.ikks.message.service.impl;

import com.chintec.ikks.common.entity.dto.MailSendDTO;
import com.chintec.ikks.common.enums.CommonCodeEnum;
import com.chintec.ikks.common.util.ResultResponse;
import com.chintec.ikks.message.service.IEmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Map;

/**
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/8/25 10:04
 */
@Slf4j
@Service
public class EmailServiceImpl implements IEmailService {
    @Value("${spring.mail.username}")
    private String sender;
    @Autowired
    private JavaMailSender mailSender;
    /**
     * 发送普通邮件
     *
     * @param mailSendDTO 邮件类
     * @return ResultResponse
     */
    @Override
    public ResultResponse sendSimpleMailMessage(MailSendDTO mailSendDTO) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sender);
        message.setTo(mailSendDTO.getTo());
        message.setSubject(mailSendDTO.getSubject());
        message.setText(mailSendDTO.getContent());
        try {
            mailSender.send(message);
            log.info("send success......");
            return ResultResponse.successResponse("send success......");
        } catch (Exception ex) {
            log.error("发送简单邮件时发生异常！", ex);
            return ResultResponse.failResponse(CommonCodeEnum.COMMON_FALSE_CODE.getCode(), ex.getMessage());
        }
    }
    /**
     * 发送 HTML 邮件
     *
     * @param to      收件人
     * @param subject 主题
     * @param content 内容
     * @return ResultResponse
     */
    @Override
    public ResultResponse sendMimeMessage(String to, String subject, String content) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            //true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(sender);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            mailSender.send(message);
            log.info("send success......");
            return ResultResponse.successResponse("发送邮件成功");
        } catch (MessagingException ex) {
            log.error("发送MimeMessage时发生异常！", ex);
            return ResultResponse.failResponse(CommonCodeEnum.COMMON_FALSE_CODE.getCode(), ex.getMessage());
        }
    }
    /**
     * 发送带附件的邮件
     *
     * @param to       收件人
     * @param subject  主题
     * @param content  内容
     * @param filePath 附件路径
     * @return ResultResponse
     */
    @Override
    public ResultResponse sendMimeMessage(String to, String subject, String content, String filePath) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(sender);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            FileSystemResource file = new FileSystemResource(new File(filePath));
            String fileName = file.getFilename();
            helper.addAttachment(fileName, file);
            mailSender.send(message);
            log.info("send success......");
            return ResultResponse.successResponse("发送邮件成功");
        } catch (MessagingException ex) {
            log.error("发送带附件的MimeMessage时发生异常！", ex);
            return ResultResponse.failResponse(CommonCodeEnum.COMMON_FALSE_CODE.getCode(), ex.getMessage());
        }
    }
    /**
     * 发送带静态文件的邮件
     *
     * @param to       收件人
     * @param subject  主题
     * @param content  内容
     * @param rscIdMap 需要替换的静态文件
     * @return ResultResponse
     */
    @Override
    public ResultResponse sendMimeMessage(String to, String subject, String content, Map<String, String> rscIdMap) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(sender);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            for (Map.Entry<String, String> entry : rscIdMap.entrySet()) {
                FileSystemResource file = new FileSystemResource(new File(entry.getValue()));
                helper.addInline(entry.getKey(), file);
            }
            mailSender.send(message);
            log.info("send success......");
            return ResultResponse.successResponse("发送邮件成功");
        } catch (MessagingException ex) {
            log.error("发送带静态文件的MimeMessage时发生异常！", ex);
            return ResultResponse.failResponse(CommonCodeEnum.COMMON_FALSE_CODE.getCode(), ex.getMessage());
        }
    }
}
