package com.chintec.ikks.process.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chintec.ikks.common.entity.FlowNode;
import com.chintec.ikks.common.entity.FlowTask;
import com.chintec.ikks.common.entity.FlowTaskStatus;
import com.chintec.ikks.common.entity.po.FlowTaskStatusPo;
import com.chintec.ikks.common.entity.po.MessageReq;
import com.chintec.ikks.common.entity.response.FlowTaskStatusResponse;
import com.chintec.ikks.common.util.PageResultResponse;
import com.chintec.ikks.common.util.ResultResponse;
import com.chintec.ikks.process.feign.IRabbitMqService;
import com.chintec.ikks.process.mapper.FlowTaskStatusMapper;
import com.chintec.ikks.process.service.IFlowNodeService;
import com.chintec.ikks.process.service.IFlowTaskService;
import com.chintec.ikks.process.service.IFlowTaskStatusService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @since 2020-09-24
 */
@Service
public class FlowTaskStatusServiceImpl extends ServiceImpl<FlowTaskStatusMapper, FlowTaskStatus> implements IFlowTaskStatusService {
    @Autowired
    private IFlowNodeService iFlowNodeService;
    @Autowired
    private IFlowTaskService iFlowTaskService;
    @Autowired
    private IRabbitMqService iRabbitMqService;

    @Override
    public ResultResponse taskStatus(Integer userId, Integer currentPage, Integer pageSize, Integer statusId, String params) {
        IPage<FlowTaskStatus> page = this.page(new Page<>(currentPage, pageSize), new QueryWrapper<FlowTaskStatus>().lambda()
                .eq(FlowTaskStatus::getAssignee, userId)
                .eq(FlowTaskStatus::getIsViewed, 1)
                .eq(statusId != 0, FlowTaskStatus::getStatus, statusId + "")
                .apply(!StringUtils.isEmpty(params), "concat(IFNULL(id,''),IFNULL(name,'')) like '%" + params + "%'")
                .orderByDesc(FlowTaskStatus::getCreateTime));
        List<FlowTaskStatusResponse> collect = page.getRecords().stream().map(s -> {
            FlowTask byId = iFlowTaskService.getById(s.getTaskId());
            FlowNode one = iFlowNodeService.getOne(new QueryWrapper<FlowNode>().lambda()
                    .eq(FlowNode::getFlowInformationId, byId.getFollowInfoId())
                    .eq(FlowNode::getNodeId, s.getNodeId()));
            FlowTaskStatusResponse flowTaskStatusResponse = new FlowTaskStatusResponse();
            BeanUtils.copyProperties(s, flowTaskStatusResponse);
            flowTaskStatusResponse.setQualificationId(one.getQualificationId());
            flowTaskStatusResponse.setTaskId(byId.getTaskId());
            return flowTaskStatusResponse;
        }).collect(Collectors.toList());
        PageResultResponse<FlowTaskStatusResponse> pageResultResponse = new PageResultResponse<>(page.getTotal(), currentPage, pageSize);
        pageResultResponse.setResults(collect);
        pageResultResponse.setTotalPages(page.getPages());
        return ResultResponse.successResponse(pageResultResponse);
    }

    @Override
    public ResultResponse taskStatus(Integer id) {
        return ResultResponse.successResponse(this.getById(id));
    }

    @Override
    public ResultResponse passFlowNode( Integer flowTaskStatusId, Integer statusCode) {
        passAndRefuse(flowTaskStatusId, statusCode, 1);
        return ResultResponse.successResponse("操作成功");
    }

    @Override
    public ResultResponse refuseFlowNode(Integer flowTaskStatusId) {
        passAndRefuse(flowTaskStatusId, 0, 0);
        return ResultResponse.successResponse("操作成功");
    }

    private void passAndRefuse(Integer flowTaskStatusId, Integer statusCode, Integer handleStatus) {
        FlowTaskStatus byId = this.getById(flowTaskStatusId);
        FlowTask task = iFlowTaskService.getById(byId.getTaskId());
        FlowNode one = iFlowNodeService.getOne(new QueryWrapper<FlowNode>()
                .lambda()
                .eq(FlowNode::getNodeId, byId.getNodeId())
                .eq(FlowNode::getFlowInformationId, task.getFollowInfoId()));
        FlowTaskStatusPo flowTaskStatusPo = new FlowTaskStatusPo();
        flowTaskStatusPo.setId(byId.getStatusId());
        flowTaskStatusPo.setName(byId.getName());
        flowTaskStatusPo.setTime(one.getDelayTime() == null ? "0" : String.valueOf(one.getDelayTime()));
        flowTaskStatusPo.setTaskStatus(statusCode + "");
        flowTaskStatusPo.setData(byId);
        flowTaskStatusPo.setIsFinish(Integer.valueOf(one.getNodeType()));
        byId.setHandleStatus(handleStatus + "");
        MessageReq messageReq = new MessageReq();
        messageReq.setSuccess(true);
        messageReq.setMessageMsg(flowTaskStatusPo);
        iRabbitMqService.modelMsg(messageReq);
    }
}
