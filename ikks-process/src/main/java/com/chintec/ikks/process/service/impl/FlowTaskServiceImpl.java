package com.chintec.ikks.process.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chintec.ikks.common.entity.FlowNode;
import com.chintec.ikks.common.entity.FlowTask;
import com.chintec.ikks.common.entity.FlowTaskStatus;
import com.chintec.ikks.common.entity.po.FlowTaskStatusPo;
import com.chintec.ikks.common.entity.vo.FlowTaskVo;
import com.chintec.ikks.common.enums.NodeStateChangeEnum;
import com.chintec.ikks.common.enums.NodeStateEnum;
import com.chintec.ikks.common.util.AssertsUtil;
import com.chintec.ikks.common.util.ResultResponse;
import com.chintec.ikks.process.event.SendEvent;
import com.chintec.ikks.process.mapper.FlowTaskMapper;
import com.chintec.ikks.process.service.IFlowNodeService;
import com.chintec.ikks.process.service.IFlowTaskService;
import com.chintec.ikks.process.service.IFlowTaskStatusService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author jeff·Tang
 * @since 2020-09-24
 */
@Service
public class FlowTaskServiceImpl extends ServiceImpl<FlowTaskMapper, FlowTask> implements IFlowTaskService {
    @Autowired
    private IFlowNodeService iFlowNodeService;

    @Autowired
    private IFlowTaskStatusService iFlowTaskStatusService;

    @Autowired
    private SendEvent sendEvent;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public ResultResponse createTask(FlowTaskVo flowTaskVo) {
        FlowTask flowTask = new FlowTask();
        BeanUtils.copyProperties(flowTaskVo, flowTask);
        flowTask.setCreateTime(LocalDateTime.now());
        flowTask.setStatus(NodeStateEnum.PENDING.getCode().toString());
        flowTask.setUpdataBy("");
        flowTask.setUpdateTime(LocalDateTime.now());
        AssertsUtil.isTrue(!this.saveOrUpdate(flowTask), "创建任务失败");
        return saveTaskNodeStatus(flowTask.getFollowInfoId(), flowTask.getId());
    }

    @Override
    public ResultResponse tasks(Integer userId) {
        List<FlowTaskStatus> flowTaskStatusList = iFlowTaskStatusService.list(new QueryWrapper<FlowTaskStatus>().lambda()
                .eq(FlowTaskStatus::getAssignee, userId));
        List<Integer> collect = flowTaskStatusList.stream().map(FlowTaskStatus::getTaskId).collect(Collectors.toList());
        List<FlowTask> flowTasks = this.list(new QueryWrapper<FlowTask>().lambda().in(!CollectionUtils.isEmpty(collect), FlowTask::getId, collect));
        return ResultResponse.successResponse(flowTasks);

    }

    private ResultResponse saveTaskNodeStatus(Integer flowId, Integer flowTaskId) {
        AssertsUtil.isTrue(!iFlowTaskStatusService.saveBatch(iFlowNodeService
                .list(new QueryWrapper<FlowNode>()
                        .lambda()
                        .eq(FlowNode::getFlowInformationId, flowId))
                .stream()
                .map(flowNode -> {
                    FlowTaskStatus flowTaskStatus = new FlowTaskStatus();
                    flowTaskStatus.setTaskId(flowTaskId);
                    flowTaskStatus.setTaskFunction(flowNode.getNextNodeCondition());
                    flowTaskStatus.setName(flowNode.getNodeName());
                    flowTaskStatus.setAssignee(flowNode.getOwnerId());
                    flowTaskStatus.setNodeId(flowNode.getNodeId());
                    flowTaskStatus.setUpdataBy("");
                    flowTaskStatus.setStatusId(UUID.randomUUID().toString());
                    flowTaskStatus.setHandleStatus(NodeStateEnum.GOING.getCode().toString());
                    flowTaskStatus.setStatus(NodeStateEnum.PENDING.getCode().toString());
                    flowTaskStatus.setUpdateTime(LocalDateTime.now());
                    flowTaskStatus.setCreateTime(LocalDateTime.now());
                    flowTaskStatus.setNodeExc(flowNode.getNodeExc());
                    flowTaskStatus.setRejectNode(flowNode.getRejectNode());
                    return flowTaskStatus;
                }).collect(Collectors.toList())), "创建任务失败");
        //初始化任务,开始第一个节点任务
        AssertsUtil.isTrue(!startEvent(flowTaskId), "任务初始化失败");
        return ResultResponse.successResponse("创建任务成功");
    }

    /**
     * 开启第一个节点初始化节点信息
     *
     * @param flowTaskId taskId
     * @return boolean
     */
    private boolean startEvent(Integer flowTaskId) {
        FlowTask byId = this.getById(flowTaskId);
        FlowNode one = iFlowNodeService.getOne(new QueryWrapper<FlowNode>()
                .lambda()
                .eq(FlowNode::getFlowInformationId, byId.getFollowInfoId())
                .eq(FlowNode::getNodeType, "1"));
        FlowTaskStatus flowTaskStatus = iFlowTaskStatusService.getOne(new QueryWrapper<FlowTaskStatus>()
                .lambda()
                .eq(FlowTaskStatus::getNodeId, one.getNodeId())
                .eq(FlowTaskStatus::getTaskId, flowTaskId));
        FlowTaskStatusPo flowTaskStatusPo = new FlowTaskStatusPo();
        flowTaskStatusPo.setData(flowTaskStatus);
        flowTaskStatusPo.setName(flowTaskStatus.getName());
        flowTaskStatusPo.setId(flowTaskStatus.getStatusId());
        flowTaskStatusPo.setStatus(NodeStateEnum.PENDING);
        flowTaskStatusPo.setIsFinish(StringUtils.isEmpty(one.getNodeType()) ? 2 : Integer.parseInt(one.getNodeType()));
        flowTaskStatusPo.setTime(one.getDelayTime() == null ? "" : one.getDelayTime() * 3600 * 1000 + "");
        Message<NodeStateChangeEnum> flowTaskStatus1 = MessageBuilder.withPayload(NodeStateChangeEnum.GOING).setHeader("flowTaskStatusPo", flowTaskStatusPo).build();
        return sendEvent.sendEvents(flowTaskStatus1, flowTaskStatusPo);
    }
}
