package com.chintec.ikks.process.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chintec.ikks.common.entity.FlowTask;
import com.chintec.ikks.common.entity.vo.FlowTaskStatusVo;
import com.chintec.ikks.common.entity.vo.FlowTaskVo;
import com.chintec.ikks.common.util.ResultResponse;

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

    ResultResponse tasks(Integer userId);

    ResultResponse updateTask(FlowTaskStatusVo flowTaskStatusVo);

    ResultResponse getFlowStatus(Integer qualificationId, Integer supplierId,Integer userId);

}
