package com.chintec.ikks.process.feign;

import com.chintec.ikks.common.util.ResultResponse;
import com.chintec.ikks.process.entity.po.MessageReq;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/9/17 15:34
 */
@FeignClient(value = "ikks-rabbitmq", path = "/v1")
public interface IRabbitMqService {
    @PostMapping("/send/msg")
    ResultResponse sendMsg(@RequestBody MessageReq msg, @RequestParam String timeMills);

    @PostMapping("/send/email")
    ResultResponse sendMsg(@RequestBody MessageReq msg);
}
