package com.chintec.ikks.message.mq;

import com.alibaba.fastjson.JSONObject;
import com.chintec.ikks.common.entity.MessageRec;
import com.chintec.ikks.common.util.AssertsUtil;
import com.chintec.ikks.common.util.ResultResponse;
import com.chintec.ikks.message.service.IEmailService;
import com.chintec.ikks.message.service.ISendMessageService;
import com.chintec.ikks.message.service.ISmsServices;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Map;

/**
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/8/20 10:51
 */
@Slf4j
@Component
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
public class MessageMqListener {
    @Autowired
    private ISmsServices iSmsServices;
    @Autowired
    private ISendMessageService iSendMessageService;
    @Autowired
    private IEmailService iEmailService;

    @RabbitListener(queues = "message")
    @RabbitHandler
    public void process(Message message, @Headers Map<String, Object> headers, Channel channel) throws IOException {
        AssertsUtil.isTrue(StringUtils.isEmpty(message), "发送的信息不能为空");
        MessageRec messageRec = JSONObject.parseObject(message.getBody(), MessageRec.class);
        try {
            switch (messageRec.getType()) {
                case 1:
                    ResultResponse resultResponse = iSmsServices.sendSms(messageRec.getMessage());
                    AssertsUtil.isTrue(!resultResponse.isSuccess(), resultResponse.getMessage());
                    break;
                case 0:
                    ResultResponse resultResponse1 = iSendMessageService.sendMessages(messageRec);
                    AssertsUtil.isTrue(!resultResponse1.isSuccess(), resultResponse1.getMessage());
                    break;
                case 2:
                    Object mailSendDTO = messageRec.getMailSendDTO();
                    AssertsUtil.isTrue(mailSendDTO == null, "请填写发送的邮件内容");
                 //   ResultResponse resultResponse2 = iEmailService.sendSimpleMailMessage(JSONObject.parseObject(JSONObject.toJSONString(mailSendDTO), MailSendDTO.class));
                  //  AssertsUtil.isTrue(!resultResponse2.isSuccess(), "邮件发送失败");
                    break;
                default:
                    AssertsUtil.isTrue(true, "无此消息类型");
                    break;
            }
            // 手动签收消息,通知mq服务器端删除该消息
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            log.info(e.getMessage());
            long deliveryTag = message.getMessageProperties().getDeliveryTag();
            channel.basicNack(deliveryTag, false, false);
        }
    }
}

