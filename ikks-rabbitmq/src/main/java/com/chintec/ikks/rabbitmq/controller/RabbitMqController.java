package com.chintec.ikks.rabbitmq.controller;

import com.chintec.ikks.common.entity.po.MessageReq;
import com.chintec.ikks.common.util.ResultResponse;
import com.chintec.ikks.rabbitmq.service.ISendMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public ResultResponse sendMsg(@RequestBody MessageReq msg, @RequestParam String timeMills) {
        return iSendMessageService.sendMsg(msg, timeMills);
    }

    @PostMapping("/send/email")
    public ResultResponse sendEmail(@RequestBody MessageReq msg) {
        return iSendMessageService.sendEmail(msg);
    }

    @PostMapping("/model/msg")
    public ResultResponse modelMsg(@RequestBody MessageReq messageReq){
        return iSendMessageService.modelMsg(messageReq);
    }
}
