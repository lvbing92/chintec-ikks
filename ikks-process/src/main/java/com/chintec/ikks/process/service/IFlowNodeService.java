package com.chintec.ikks.process.service;

import com.chintec.ikks.common.util.ResultResponse;
import com.chintec.ikks.process.entity.FlowNode;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chintec.ikks.process.entity.vo.NodeFunction;
import com.chintec.ikks.process.entity.vo.ProcessFlow;
import com.chintec.ikks.process.entity.vo.ProcessFlowInfo;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author jeff·Tang
 * @since 2020-09-17
 */
public interface IFlowNodeService extends IService<FlowNode> {

    /**
     * 创建模板流程
     *
     * @param processFlowInfo  流程信息
     * @return resultResponse
     */
    ResultResponse saveFlowNodeService(ProcessFlowInfo processFlowInfo);

    /**
     * 更新模板流程
     *
     * @param processFlowInfo  流程信息
     * @return resultResponse
     */
    ResultResponse updateFlowNodeService(ProcessFlowInfo processFlowInfo);

    /**
     * 启动禁用模板id
     *
     * @param id 模板信息id
     * @return resultResponse
     */
    ResultResponse deleteUpdateFlowNodeService(Long id);
}
