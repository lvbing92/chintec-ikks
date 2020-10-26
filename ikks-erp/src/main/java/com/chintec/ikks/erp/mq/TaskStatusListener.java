package com.chintec.ikks.erp.mq;

import com.alibaba.fastjson.JSONObject;
import com.chintec.ikks.common.entity.FlowTaskStatus;
import com.chintec.ikks.common.entity.po.FlowTaskStatusPo;
import com.chintec.ikks.common.entity.po.MessageReq;
import com.chintec.ikks.common.entity.vo.FlowTaskStatusVo;
import com.chintec.ikks.common.util.AssertsUtil;
import com.chintec.ikks.common.util.ResultResponse;
import com.chintec.ikks.erp.feign.ICompanyUserService;
import com.chintec.ikks.erp.feign.IFlowTaskService;
import com.chintec.ikks.erp.feign.IRabbitMqService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/10/19 9:33
 */
@Slf4j
@Component
public class TaskStatusListener {
    @Autowired
    private IFlowTaskService iFlowTaskService;
    @Autowired
    private ICompanyUserService iCompanyUserService;
    @Autowired
    private IRabbitMqService iRabbitMqService;

    @RabbitListener(queues = "message.queue")
    @RabbitHandler
    public void taskStatusListener(Message message, @Headers Map<String, Object> headers, Channel channel) {
        log.info(" start taskStatusListener :{}", message);
        MessageReq messageReq = JSONObject.parseObject(new String(message.getBody()), MessageReq.class);
        FlowTaskStatusPo flowTaskStatusPo = JSONObject.parseObject(JSONObject.toJSONString(messageReq.getMessageMsg()), FlowTaskStatusPo.class);
        FlowTaskStatus data = flowTaskStatusPo.getData();
        FlowTaskStatusVo flowTaskStatusVo = new FlowTaskStatusVo();
        flowTaskStatusVo.setId(data.getId());
        flowTaskStatusVo.setIsViewed(1);
        ResultResponse resultResponse = iFlowTaskService.updateTask(flowTaskStatusVo);
        AssertsUtil.isTrue(!resultResponse.isSuccess(), resultResponse.getMessage());
        log.info(" end taskStatusListener :{}", resultResponse.getMessage());
        ResultResponse queryCompanyUser = iCompanyUserService.queryCompanyUser(data.getAssignee());
        AssertsUtil.isTrue(!queryCompanyUser.isSuccess(), queryCompanyUser.getMessage());
        //  CompanyUserRequest companyUserRequest = JSONObject.parseObject(JSONObject.toJSONString(queryCompanyUser.getData()), CompanyUserRequest.class);
        //TODO 发送邮件给用户
    }
}
