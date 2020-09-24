package com.chintec.ikks.process;

import com.chintec.ikks.common.enums.NodeStateEnum;
import com.chintec.ikks.common.util.ResultResponse;
import com.chintec.ikks.process.entity.po.FlowTaskStatusPo;
import com.chintec.ikks.process.entity.po.MessageReq;
import com.chintec.ikks.process.feign.IRabbitMqService;
import com.chintec.ikks.process.service.IFlowTaskService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/9/17 15:44
 */
@SpringBootTest
@Slf4j
class processTest {
    @Autowired
    private IRabbitMqService iRabbitMqService;
    @Autowired
    private IFlowTaskService iFlowTaskService;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    void startProcessTest() throws Exception {
//        FlowTaskStatus flowTask = new FlowTaskStatus();
//        flowTask.setId(4);
//        flowTask.setName("流程开始测试");
//        flowTask.setStatus("0");
//        flowTask.setTaskStatus(NodeStateEnum.PENDING);
        FlowTaskStatusPo flowTask1 = new FlowTaskStatusPo();
        flowTask1.setId(UUID.randomUUID().toString());
        flowTask1.setName("流程开始测试");
        flowTask1.setStatus(NodeStateEnum.PENDING);
//        iFlowTaskService.init(flowTask);
        iFlowTaskService.startTask(flowTask1);
    }

    @Test
    void redis() {
        FlowTaskStatusPo flowTaskStatus = new FlowTaskStatusPo();
        flowTaskStatus.setId("1795fec1-b343-42a1-95ea-7d2400b6ad4e");
        flowTaskStatus.setName("这是个测试");
        flowTaskStatus.setTime("6000");
        MessageReq messageReq = new MessageReq();
        messageReq.setSuccess(true);
        messageReq.setMessageMsg(flowTaskStatus);
        ResultResponse resultResponse = iRabbitMqService.modelMsg(messageReq);
        System.out.println(resultResponse);
    }

}
