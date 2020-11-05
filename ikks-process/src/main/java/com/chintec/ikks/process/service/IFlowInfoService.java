package com.chintec.ikks.process.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chintec.ikks.common.entity.FlowInfo;
import com.chintec.ikks.common.entity.vo.FlowInfoVo;
import com.chintec.ikks.common.util.ResultResponse;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author jeff·Tang
 * @since 2020-09-24
 */
public interface IFlowInfoService extends IService<FlowInfo> {
    /**
     * 创建一个流程
     * @param flowInfoVo 流程信息
     * @return ResultResponse
     */
    ResultResponse createFlowNode(FlowInfoVo flowInfoVo);

    ResultResponse updateFlowNode(FlowInfoVo flowInfoVo);

    ResultResponse listFlow(Integer currentPage, Integer pageSize);

    ResultResponse one(Integer id);

    ResultResponse delete(Integer id);
}
