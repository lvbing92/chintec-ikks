package com.chintec.ikks.process.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chintec.ikks.common.util.ResultResponse;
import com.chintec.ikks.process.entity.FlowTask;
import com.chintec.ikks.process.entity.vo.FlowTaskVo;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author jeff·Tang
 * @since 2020-09-24
 */
public interface IFlowTaskService extends IService<FlowTask> {
    /**
     * 创建一个流程任务
     *
     * @param flowTaskVo 任务信息类
     * @return ResultResponse
     */
    ResultResponse createTask(FlowTaskVo flowTaskVo);
}
