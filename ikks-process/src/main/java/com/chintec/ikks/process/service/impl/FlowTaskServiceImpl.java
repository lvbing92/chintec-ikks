package com.chintec.ikks.process.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chintec.ikks.common.util.AssertsUtil;
import com.chintec.ikks.common.util.ResultResponse;
import com.chintec.ikks.process.entity.*;
import com.chintec.ikks.process.entity.po.FlowNodeFunctionPo;
import com.chintec.ikks.process.entity.vo.NodeFunction;
import com.chintec.ikks.process.entity.vo.ProcessFlow;
import com.chintec.ikks.process.mapper.FlowTaskMapper;
import com.chintec.ikks.process.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
public class FlowTaskServiceImpl extends ServiceImpl<FlowTaskMapper, FlowTask> implements IFlowTaskService {
    @Autowired
    private IFlowInfoService iFlowInfoService;
    @Autowired
    private IFlowNodeService iFlowNodeService;
    @Autowired
    private IFlowNodeFunctionService iFlowNodeFunctionService;
    @Autowired
    private IFlowTaskStatusService iFlowTaskStatusService;

    @Override
    public ResultResponse createFlowTask(FlowTask flowTask) {
        AssertsUtil.isTrue(!this.save(flowTask), "创建任务失败");
        FlowInfo byId = iFlowInfoService.getById(flowTask.getFollowId());
        List<FlowTaskStatus> collect = iFlowNodeService.list(new QueryWrapper<FlowNode>()
                .lambda()
                .eq(FlowNode::getFlowInformationId, byId.getId()))
                .stream()
                .map(flowNode -> {
                    ProcessFlow processFlow = new ProcessFlow();
                    BeanUtils.copyProperties(flowNode, processFlow);
                    FlowNodeFunction one = iFlowNodeFunctionService.getOne(new QueryWrapper<FlowNodeFunction>()
                            .lambda()
                            .eq(FlowNodeFunction::getFlowId, flowNode.getId()));
                    FlowNodeFunctionPo flowNodeFunctionPo = new FlowNodeFunctionPo();
                    BeanUtils.copyProperties(one, flowNodeFunctionPo);
                    flowNodeFunctionPo.setNodeFunctions(JSONObject.parseArray(one.getFunctionContent(), NodeFunction.class));
                    processFlow.setFlowNodeFunctionPo(flowNodeFunctionPo);
                    FlowTaskStatus flowTaskStatus = new FlowTaskStatus();
                    flowTaskStatus.setName(flowNode.getNodeName());
                    flowTaskStatus.setTaskId(flowTask.getId());
                    flowTaskStatus.setTaskFunction(JSONObject.toJSONString(processFlow));
                    return flowTaskStatus;
                }).collect(Collectors.toList());
        AssertsUtil.isTrue(!iFlowTaskStatusService.saveBatch(collect), "创建任务失败");
        FlowTaskStatus flowTaskStatus1 = collect.stream().filter(flowTaskStatus -> StringUtils.isEmpty(JSONObject.parseObject(flowTaskStatus.getTaskFunction(), ProcessFlow.class).getProveNodes())).collect(Collectors.toList()).get(0);
        //TODO mq发送消息开启业务流程驱动

        return ResultResponse.successResponse("创建任务成功");
    }
}
