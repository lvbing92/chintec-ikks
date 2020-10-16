package com.chintec.ikks.process;

import com.baomidou.mybatisplus.core.conditions.interfaces.Func;
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
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

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
            flowNodeVo.setNodeFunctionVos(Arrays.asList(new NodeFunctionVo("1", "1", i % 2 == 0 && i < 4 ? i + 2 : null), new NodeFunctionVo("1", "2", i % 2 == 1 ? i + 2 : null)));
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
        flowTaskVo.setFollowInfoId(6);
        flowTaskVo.setModuleId(4);
        flowTaskVo.setName("测试流程任务");
        flowTaskVo.setStatus(NodeStateEnum.PENDING.getCode().toString());
        ResultResponse task = iFlowTaskService.createTask(flowTaskVo);
        assert task.isSuccess();
    }

    @Test
    void send() {
        FlowTaskStatus byId = iFlowTaskStatusService.getById(515);
        FlowTaskStatusPo flowTaskStatus = new FlowTaskStatusPo();
        flowTaskStatus.setId(byId.getStatusId());
        flowTaskStatus.setName("这是个测试");
        flowTaskStatus.setTime("30000");
        flowTaskStatus.setTaskStatus("1");
        flowTaskStatus.setData(byId);
        flowTaskStatus.setIsFinish(3);
        byId.setHandleStatus("1");
        MessageReq messageReq = new MessageReq();
        messageReq.setSuccess(true);
        messageReq.setMessageMsg(flowTaskStatus);
        ResultResponse resultResponse = iRabbitMqService.modelMsg(messageReq);
        log.info("结果:{}", resultResponse);
    }

    @Test
    void redis() {
        System.out.println(redisTemplate.opsForValue().get("589a690a-23f5-41df-ba74-6602d63b4e61"));
    }

    @Test
    void concurrentHashMap() {
        ConcurrentHashMap<String, Object> stringObjectConcurrentHashMap = new ConcurrentHashMap<>(10);
        Object o = stringObjectConcurrentHashMap.putIfAbsent("1", "如果我说如果");
        log.info("concurrentHashMap first {}", o);
        Object put = stringObjectConcurrentHashMap.putIfAbsent("1", "哈哈");
        log.info("concurrentHashMap second {}", put);
    }

    @Test
    void stream() {
        ConcurrentHashMap<String, Long> stringObjectConcurrentHashMap = new ConcurrentHashMap<>(getData(900));
        log.info("init size:{}", stringObjectConcurrentHashMap.size());
//        OptionalLong reduce = LongStream.rangeClosed(1, 10).reduce(Long::sum);
//        assert reduce.isPresent();
//        long asLong = reduce.getAsLong();
//        System.out.println(asLong);
        ForkJoinPool forkJoinPool = new ForkJoinPool(10);
        forkJoinPool.execute(() -> LongStream.rangeClosed(1, 10).parallel().forEach(__ -> {
            synchronized (stringObjectConcurrentHashMap) {
                int size = 1000 - stringObjectConcurrentHashMap.size();
                log.info("difference size:{}", size);
                stringObjectConcurrentHashMap.putAll(getData(size));
            }
//            int size = 1000 - stringObjectConcurrentHashMap.size();
//            log.info("difference size:{}", size);
//            stringObjectConcurrentHashMap.putAll(getData(size));
        }));
        forkJoinPool.shutdown();
        forkJoinPool.awaitQuiescence(1, TimeUnit.HOURS);
        log.info("finish size:{}", stringObjectConcurrentHashMap.size());
    }

    private ConcurrentHashMap<String, Long> getData(int count) {
        return LongStream.rangeClosed(1, count)
                .boxed()
                .collect(Collectors.toConcurrentMap(i -> UUID.randomUUID().toString(), Function.identity(), (o1, o2) -> o1, ConcurrentHashMap::new));
    }
}
