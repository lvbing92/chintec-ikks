package com.chintec.ikks.process.service;

import com.chintec.ikks.common.util.ResultResponse;
import com.chintec.ikks.process.entity.po.FlowTaskStatus;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author jeff·Tang
 * @since 2020-09-17
 */
public interface IFlowTaskService {
    /**
     * 开始节点任务
     * @param flowTaskStatus
     * @return
     */
    ResultResponse startTask(FlowTaskStatus flowTaskStatus);


}
