package com.chintec.ikks.process.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chintec.ikks.common.util.ResultResponse;
import com.chintec.ikks.process.entity.FlowInfo;
import com.chintec.ikks.process.entity.vo.FlowInfoVo;

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
}
