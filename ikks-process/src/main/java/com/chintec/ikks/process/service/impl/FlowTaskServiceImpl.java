package com.chintec.ikks.process.service.impl;

import com.chintec.ikks.common.enums.NodeStateChangeEnum;
import com.chintec.ikks.common.util.AssertsUtil;
import com.chintec.ikks.common.util.ResultResponse;
import com.chintec.ikks.process.entity.po.FlowTaskStatus;
import com.chintec.ikks.process.event.SendEvent;
import com.chintec.ikks.process.service.IFlowTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author jeff·Tang
 * @since 2020-09-17
 */
@Service
@Slf4j
public class FlowTaskServiceImpl implements IFlowTaskService {
    @Autowired
    private SendEvent sendEvent;

    @Override
    public ResultResponse startTask(FlowTaskStatus flowTaskStatus) {
        Message<NodeStateChangeEnum> message = MessageBuilder.withPayload(NodeStateChangeEnum.GOING).setHeader("flowTaskStatus", flowTaskStatus).build();
        AssertsUtil.isTrue(sendEvent.sendEvents(message, flowTaskStatus), "状态事件执行失败");
        return ResultResponse.successResponse();
    }
}
