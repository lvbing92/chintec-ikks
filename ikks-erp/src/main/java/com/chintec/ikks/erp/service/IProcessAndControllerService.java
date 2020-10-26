package com.chintec.ikks.erp.service;

import com.chintec.ikks.common.entity.vo.FlowInfoVo;
import com.chintec.ikks.common.util.ResultResponse;

/**
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/10/21 14:00
 */
public interface IProcessAndControllerService {
    /**
     * 创建一个流程
     *
     * @param flowInfoVo
     * @return
     */
    ResultResponse createProcess(FlowInfoVo flowInfoVo);

    /**
     * 开始一个流程
     *
     * @param supplierId
     * @param token
     * @return
     */
    ResultResponse startProcess(String token, Integer supplierId);

    /**
     * 开始节点审核
     *
     * @param token
     * @param flowTaskStatusId
     * @return
     */
    ResultResponse passFlowNode(String token, Integer flowTaskStatusId, Integer code);

    /**
     * 拒绝节点审核
     *
     * @param token
     * @param flowTaskStatusId
     * @return ResultResponse
     */
    ResultResponse refuseFlowNode(String token, Integer flowTaskStatusId);

    ResultResponse taskStatus(String token, Integer currentPage, Integer pageSize, Integer statusId, String params);
}
