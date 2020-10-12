package com.chintec.ikks.process;

import com.chintec.ikks.common.enums.NodeStateEnum;
import com.chintec.ikks.common.util.ResultResponse;
import com.chintec.ikks.process.entity.FlowTaskStatus;
import com.chintec.ikks.process.entity.po.FlowTaskStatusPo;
import com.chintec.ikks.process.entity.po.MessageReq;
import com.chintec.ikks.process.entity.vo.FlowInfoVo;
import com.chintec.ikks.process.entity.vo.FlowNodeVo;
import com.chintec.ikks.process.entity.vo.FlowTaskVo;
import com.chintec.ikks.process.entity.vo.NodeFunctionVo;
import com.chintec.ikks.process.feign.IRabbitMqService;
import com.chintec.ikks.process.service.IFlowInfoService;
import com.chintec.ikks.process.service.IFlowTaskService;
import com.chintec.ikks.process.service.IFlowTaskStatusService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
    @Autowired
    private IFlowInfoService iFlowInfoService;
    @Autowired
    private IFlowTaskStatusService iFlowTaskStatusService;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    void creatProcessTest() {
        FlowInfoVo flowInfoVo = new FlowInfoVo();
        flowInfoVo.setFlowName("测试流程");
        flowInfoVo.setModuleId(2);
        List<FlowNodeVo> flowInfoVos = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            FlowNodeVo flowNodeVo = new FlowNodeVo();
            flowNodeVo.setDelayTime(0);
            flowNodeVo.setNodeId(i + 1);
            flowNodeVo.setNodeName("测试节点" + i);
            flowNodeVo.setProveNodes(i == 0 ? new ArrayList<>() : Collections.singletonList(i));
            flowNodeVo.setNextNodes(i == 4 ? new ArrayList<>() : Collections.singletonList(i + 2));
            flowNodeVo.setNodeType(i == 0 ? "1" : (i == 4 ? "3" : "2"));
            flowNodeVo.setIsReject(i % 2 == 1 ? "1" : "0");
            flowNodeVo.setRejectButtonName("驳回按钮" + i);
            flowNodeVo.setNodeExc("1");
            flowNodeVo.setNextNodeTrend("0");
            flowNodeVo.setNodeFunctionVos(Arrays.asList(new NodeFunctionVo("1", "1", i %2==0 &&i<4? i + 2 : null), new NodeFunctionVo("1", "2", i % 2 == 1 ? i+2 : null)));
            flowNodeVo.setRejectNode(i % 2 == 1 ? i - 1 : null);
            flowNodeVo.setNodeRunMode("1");
            flowInfoVos.add(flowNodeVo);
        }
        flowInfoVo.setFlowNodes(flowInfoVos);
        assert iFlowInfoService.createFlowNode(flowInfoVo).isSuccess();
    }

    @Test
    void startProcessTest() {
        FlowTaskVo flowTaskVo = new FlowTaskVo();
        flowTaskVo.setFollowInfoId(8);
        flowTaskVo.setModuleId(2);
        flowTaskVo.setName("测试流程任务");
        flowTaskVo.setStatus(NodeStateEnum.PENDING.getCode().toString());
        ResultResponse task = iFlowTaskService.createTask(flowTaskVo);
        assert task.isSuccess();
    }

    @Test
    void send() {
        FlowTaskStatus byId = iFlowTaskStatusService.getById(273);
        FlowTaskStatusPo flowTaskStatus = new FlowTaskStatusPo();
        flowTaskStatus.setId(byId.getStatusId());
        flowTaskStatus.setName("这是个测试");
        flowTaskStatus.setTime("30000");
        flowTaskStatus.setTaskStatus("2");
        flowTaskStatus.setData(byId);
        flowTaskStatus.setIsFinish(1);
        MessageReq messageReq = new MessageReq();
        messageReq.setSuccess(true);
        messageReq.setMessageMsg(flowTaskStatus);
        ResultResponse resultResponse = iRabbitMqService.modelMsg(messageReq);
    }

    @Test
    void redis() {
        System.out.println(redisTemplate.hasKey(iFlowTaskStatusService.getById(166).getStatusId()));
    }
}
