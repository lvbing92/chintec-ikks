package com.chintec.ikks.process;

import com.chintec.ikks.common.util.ResultResponse;
import com.chintec.ikks.process.entity.FlowTask;
import com.chintec.ikks.process.service.IFlowTaskService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/9/17 15:44
 */
@SpringBootTest
@Slf4j
class processTest {
    @Autowired
    private IFlowTaskService iFlowTaskService;

    @Test
    void startProcessTest() {
        FlowTask flowTask = new FlowTask();
        flowTask.setName("流程开始测试");
        flowTask.setFollowId(1);
        flowTask.setStatus("0");
        ResultResponse flowTask1 = iFlowTaskService.createFlowTask(flowTask);
        log.info(flowTask1.getMessage());
    }
}
