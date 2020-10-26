package com.chintec.ikks.process.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chintec.ikks.common.entity.FlowTaskStatus;
import com.chintec.ikks.common.util.ResultResponse;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author jeff·Tang
 * @since 2020-09-24
 */
public interface IFlowTaskStatusService extends IService<FlowTaskStatus> {

    ResultResponse taskStatus(Integer userId, Integer currentPage, Integer pageSize, Integer statusId, String params);

    ResultResponse taskStatus(Integer id);

    /**
     * 开始节点审核
     *
     * @param flowTaskStatusId
     * @return
     */
    ResultResponse passFlowNode(Integer flowTaskStatusId, Integer statusCode);

    /**
     * 拒绝节点审核
     *
     * @param flowTaskStatusId
     * @return ResultResponse
     */
    ResultResponse refuseFlowNode(Integer flowTaskStatusId);
}
