package com.chintec.ikks.process.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chintec.ikks.common.util.AssertsUtil;
import com.chintec.ikks.common.util.ResultResponse;
import com.chintec.ikks.process.entity.FlowInfo;
import com.chintec.ikks.process.entity.FlowNode;
import com.chintec.ikks.process.entity.vo.FlowInfoVo;
import com.chintec.ikks.process.entity.vo.FlowNodeVo;
import com.chintec.ikks.process.mapper.FlowInfoMapper;
import com.chintec.ikks.process.service.IFlowInfoService;
import com.chintec.ikks.process.service.IFlowNodeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author jeff·Tang
 * @since 2020-09-24
 */
@Service
public class FlowInfoServiceImpl extends ServiceImpl<FlowInfoMapper, FlowInfo> implements IFlowInfoService {
    @Autowired
    private IFlowNodeService iFlowNodeService;

    @Override
    public ResultResponse createFlowNode(FlowInfoVo flowInfoVo)  {
        FlowInfo flowInfo = new FlowInfo();
        BeanUtils.copyProperties(flowInfoVo, flowInfo);
        flowInfo.setUpdateTime(LocalDateTime.now());
        flowInfo.setCreateTime(LocalDateTime.now());
        flowInfo.setUpdataBy("");
        AssertsUtil.isTrue(!this.saveOrUpdate(flowInfo), "创建流程信息失败");
        return saveFlowNodes(flowInfoVo.getFlowNodes());
    }

    private ResultResponse saveFlowNodes(List<FlowNodeVo> flowNodeVos) {
        AssertsUtil.isTrue(!iFlowNodeService.saveBatch(flowNodeVos.stream().map(flowNodeVo -> {
            FlowNode flowNode = new FlowNode();
            BeanUtils.copyProperties(flowNodeVo, flowNode);
            flowNode.setCreateTime(LocalDateTime.now());
            flowNode.setUpdataBy("");
            flowNode.setUpdateTime(LocalDateTime.now());
            flowNode.setNextNodeCondition(JSONObject.toJSONString(flowNodeVo.getNodeFunctionVos()));
            return flowNode;
        }).collect(Collectors.toList())), "创建流程信息失败");
        return ResultResponse.successResponse("创建流程信息成功");
    }
}
