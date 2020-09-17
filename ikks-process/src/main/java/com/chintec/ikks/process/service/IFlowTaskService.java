package com.chintec.ikks.process.service;

import com.chintec.ikks.common.util.ResultResponse;
import com.chintec.ikks.process.entity.FlowTask;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jeff·Tang
 * @since 2020-09-17
 */
public interface IFlowTaskService extends IService<FlowTask> {
    /**
     * 创建一个流程任务
     * @param flowTask
     * @return
     */
    ResultResponse createFlowTask(FlowTask flowTask);

}
