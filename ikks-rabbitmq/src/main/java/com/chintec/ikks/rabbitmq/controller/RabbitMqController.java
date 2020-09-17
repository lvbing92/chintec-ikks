package com.chintec.ikks.rabbitmq.controller;

import com.chintec.ikks.common.util.ResultResponse;
import com.chintec.ikks.rabbitmq.entity.MessageReq;
import com.chintec.ikks.rabbitmq.service.ISendMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author rubin·lv
 * @version 1.0
 * @date 2020年9月17日14
 */
@RestController
@RequestMapping(value = "v1")
public class RabbitMqController {
    @Autowired
    private ISendMessageService iSendMessageService;

    @PostMapping("/send/msg")
    public ResultResponse sendMsg(@RequestParam MessageReq msg, @RequestParam String timeMills){
        iSendMessageService.sendMsg(msg,timeMills);
        return ResultResponse.successResponse();
    }

    @PostMapping("/send/email")
    public ResultResponse sendMsg(@RequestParam MessageReq msg){
        return iSendMessageService.sendEmail(msg);
    }
}
