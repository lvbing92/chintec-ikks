package com.chintec.ikks.process.service.impl;

import com.alibaba.fastjson.JSON;
import com.chintec.ikks.common.util.AssertsUtil;
import com.chintec.ikks.common.util.ResultResponse;
import com.chintec.ikks.process.entity.FlowInfo;
import com.chintec.ikks.process.entity.FlowNode;
import com.chintec.ikks.process.entity.FlowNodeFunction;
import com.chintec.ikks.process.entity.po.FlowNodeFunctionPo;
import com.chintec.ikks.process.entity.vo.ProcessFlowInfo;
import com.chintec.ikks.process.mapper.FlowNodeMapper;
import com.chintec.ikks.process.service.IFlowInfoService;
import com.chintec.ikks.process.service.IFlowNodeFunctionService;
import com.chintec.ikks.process.service.IFlowNodeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author jeff·Tang
 * @since 2020-09-17
 */
@Service
public class FlowNodeServiceImpl extends ServiceImpl<FlowNodeMapper, FlowNode> implements IFlowNodeService {
    @Autowired
    private IFlowNodeFunctionService iFlowNodeFunctionService;
    @Autowired
    private IFlowInfoService iFlowInfoService;

    @Override
    public ResultResponse saveFlowNodeService(ProcessFlowInfo processFlowInfo) {
        FlowInfo flowInfo = new FlowInfo();
        BeanUtils.copyProperties(processFlowInfo, flowInfo);
        flowInfo.setCreateTime(LocalDateTime.now());
        flowInfo.setUpdateTime(LocalDateTime.now());
        AssertsUtil.isTrue(!iFlowInfoService.saveOrUpdate(flowInfo), "创建流程信息失败");
        List<FlowNodeFunctionPo> flowNodeFunctionPos = new ArrayList<>();
        processFlowInfo.getProcessFlows().forEach(processFlow -> {
            FlowNodeFunctionPo flowNodeFunctionPo = processFlow.getFlowNodeFunctionPo();
            flowNodeFunctionPo.setFlowId(processFlow.getId());
            flowNodeFunctionPos.add(flowNodeFunctionPo);
            FlowNode flowNode = new FlowNode();
            flowNode.setFlowInformationId(flowInfo.getId());
            BeanUtils.copyProperties(processFlow, flowNode);
            AssertsUtil.isTrue(!this.save(flowNode), "创建流程信息失败");
        });
        AssertsUtil.isTrue(!iFlowNodeFunctionService
                .saveBatch(flowNodeFunctionPos
                        .stream()
                        .map(flowNodeFunctionPo -> {
                            FlowNodeFunction flowNodeFunction = new FlowNodeFunction();
                            BeanUtils.copyProperties(flowNodeFunctionPo, flowNodeFunction);
                            flowNodeFunction.setFunctionContent(JSON.toJSONString(flowNodeFunctionPo.getNodeFunctions()));
                            return flowNodeFunction;
                        }).collect(Collectors.toList())), "创建流程信息失败");
        return ResultResponse.successResponse("创建流程信息成功");
    }

    @Override
    public ResultResponse updateFlowNodeService(ProcessFlowInfo processFlowInfo) {
        AssertsUtil.isTrue(!this.updateBatchById(processFlowInfo.getProcessFlows().stream().map(processFlow -> {
            FlowNode flowNode = new FlowNode();
            BeanUtils.copyProperties(processFlow, flowNode);
            return flowNode;
        }).collect(Collectors.toList())), "修改流程信息失败");
        return ResultResponse.successResponse("修改流程信息成功");
    }

    @Override
    public ResultResponse deleteUpdateFlowNodeService(Long id) {
        return null;
    }
}
