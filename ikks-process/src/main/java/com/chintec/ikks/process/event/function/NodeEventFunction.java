package com.chintec.ikks.process.event.function;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chintec.ikks.common.entity.FlowNode;
import com.chintec.ikks.common.entity.FlowTask;
import com.chintec.ikks.common.entity.FlowTaskStatus;
import com.chintec.ikks.common.entity.po.FlowTaskStatusPo;
import com.chintec.ikks.common.entity.po.MessageReq;
import com.chintec.ikks.common.entity.vo.NodeFunctionVo;
import com.chintec.ikks.common.enums.AutoEnum;
import com.chintec.ikks.common.enums.NodeStateChangeEnum;
import com.chintec.ikks.common.enums.NodeStateEnum;
import com.chintec.ikks.common.enums.NodeTypeEnum;
import com.chintec.ikks.common.util.AssertsUtil;
import com.chintec.ikks.common.util.ResultResponse;
import com.chintec.ikks.process.event.SendEvent;
import com.chintec.ikks.process.feign.IRabbitMqService;
import com.chintec.ikks.process.service.IFlowNodeService;
import com.chintec.ikks.process.service.IFlowTaskService;
import com.chintec.ikks.process.service.IFlowTaskStatusService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/10/13 15:30
 * 这个就是公共方法类,没啥很重要的作用
 * 只是提出来,进行解耦的
 */
public class NodeEventFunction {

    /**
     * 下个节点的公共方法
     *
     * @param nodeId 节点id
     */
    public static void nextTask(Integer nodeId, Integer flowInfoId, Integer taskId, IFlowTaskStatusService iFlowTaskStatusService, IFlowNodeService iFlowNodeService, SendEvent sendEvent) {
        FlowTaskStatusPo flowTaskStatusPo = new FlowTaskStatusPo();
        //根据节点信息查询节点id
        FlowNode node = iFlowNodeService.getOne(new QueryWrapper<FlowNode>()
                .lambda()
                .eq(FlowNode::getNodeId, nodeId)
                .eq(FlowNode::getFlowInformationId, flowInfoId));
        List<Integer> integers = JSONObject.parseArray(node.getProveNodes(), Integer.class);
        List<FlowTaskStatus> collect = null;
        //查看该节点的前置节点完成情况,过滤出进行中的
        if ((NodeTypeEnum.NODE_TYPE_ENUM_EXC_MORE.getCode() + "").equals(node.getNodeExc())) {
            collect = iFlowTaskStatusService.list(new QueryWrapper<FlowTaskStatus>()
                    .lambda()
                    .in(FlowTaskStatus::getNodeId, integers)
                    .eq(FlowTaskStatus::getTaskId, taskId))
                    .stream()
                    .filter(flowTaskStatus -> flowTaskStatus.getStatus().equals(NodeStateEnum.GOING.getCode().toString()))
                    .collect(Collectors.toList());
        }
        //判断前置节点是否有进行中的 如果没有,开启该节点任务
        if (CollectionUtils.isEmpty(collect)) {
            flowTaskStatusPo.setTime(StringUtils.isEmpty(node.getDelayTime()) ? "" : node.getDelayTime() * 3600 * 1000 + "");
            flowTaskStatusPo.setName(node.getNodeName());
            flowTaskStatusPo.setIsFinish(StringUtils.isEmpty(node.getNodeType()) ? NodeTypeEnum.NODE_TYPE_ENUM_NORMAL.getCode() : Integer.parseInt(node.getNodeType()));
            flowTaskStatusPo.setStatus(NodeStateEnum.PENDING);
            FlowTaskStatus one = iFlowTaskStatusService.getOne(new QueryWrapper<FlowTaskStatus>()
                    .lambda()
                    .eq(FlowTaskStatus::getNodeId, nodeId)
                    .eq(FlowTaskStatus::getTaskId, taskId));
            flowTaskStatusPo.setId(one.getStatusId());
            flowTaskStatusPo.setData(one);
            Message<NodeStateChangeEnum> message1 = MessageBuilder.withPayload(NodeStateChangeEnum.GOING).setHeader("flowTaskStatusPo", flowTaskStatusPo).build();
            AssertsUtil.isTrue(!sendEvent.sendEvents(message1, flowTaskStatusPo), "节点任务开启失败:" + flowTaskStatusPo.getName());
        }
    }

    /**
     * 完成任务的公共方法
     *
     * @param byId   任务状态类
     * @param status 状态
     */
    public static void finishTask(FlowTask byId, String status, String name, IFlowTaskService iFlowTaskService) {
        byId.setStatus(status);
        byId.setAuditTime(LocalDateTime.now());
        byId.setUpdateTime(LocalDateTime.now());
        byId.setUpdataBy(name);
        AssertsUtil.isTrue(!iFlowTaskService.saveOrUpdate(byId), "完成任务失败");
    }

    /**
     * 更新节点任务状态
     *
     * @param flowTaskStatus f
     * @param status         s
     */
    public static void updateTaskStatus(FlowTaskStatus flowTaskStatus, String status, IFlowTaskStatusService iFlowTaskStatusService) {
        flowTaskStatus.setStatus(status);
        flowTaskStatus.setUpdateTime(LocalDateTime.now());
        AssertsUtil.isTrue(!iFlowTaskStatusService.saveOrUpdate(flowTaskStatus), "初始化失败");
    }

    /**
     * 发送消息给对应的人员
     * 以及修改 task属性为可见
     *
     * @param flowTaskStatus 任务状态
     */
    public static void sendMessage(FlowTaskStatusPo flowTaskStatus, IRabbitMqService iRabbitMqService) {
        MessageReq messageReq = new MessageReq();
        messageReq.setUuid(UUID.randomUUID().toString());
        messageReq.setMessageMsg(flowTaskStatus);
        messageReq.setSuccess(true);
        iRabbitMqService.sendMsg(messageReq, StringUtils.isEmpty(flowTaskStatus.getTime()) ? "" : flowTaskStatus.getTime());
    }

    /**
     * 当任务完成或是返回上一个节点的时候,删除该条记录缓存
     * 并将此次执行结果记录到数据库中去
     *
     * @param id 任务缓存id
     */
    public static void removeTaskMachine(String id, RedisTemplate<String, Object> redisTemplate) {
        //TODO 执行结果记录到 数据库中 然后删除缓存

        Boolean delete = redisTemplate.delete(id);
        AssertsUtil.isTrue(!(delete == null ? false : delete), "删除失败任务缓存失败");
    }

    /**
     * 自动完成任务和手动完成任务
     * 自动完成任务的时候 要求每一个节点的下一个节点是唯一的
     * 并且上一个节点也是惟一的 否则会报 参数错误
     *
     * @param flowTaskStatusPo       流程信息类
     * @param iFlowNodeService       流程节点 service
     * @param iFlowTaskService       流程任务 service
     * @param iRabbitMqService       mqservice
     * @param iFlowTaskStatusService 流程任务状态 service
     */
    public static void autoFinishTask(FlowTaskStatusPo flowTaskStatusPo, IFlowNodeService iFlowNodeService, IFlowTaskService iFlowTaskService, IRabbitMqService iRabbitMqService, IFlowTaskStatusService iFlowTaskStatusService) {
        FlowTaskStatus data = flowTaskStatusPo.getData();
        FlowTask byId = iFlowTaskService.getById(data.getTaskId());
        FlowNode one = iFlowNodeService.getOne(new QueryWrapper<FlowNode>()
                .lambda()
                .eq(FlowNode::getFlowInformationId, byId.getFollowInfoId())
                .eq(FlowNode::getNodeId, data.getNodeId()));
        if ((AutoEnum.AUTO_ENUM_YES.getCode() + "").equals(one.getNodeRunMode())) {
            List<NodeFunctionVo> nodeIds = flowTaskStatusPo.getNodeIds();
            AssertsUtil.isTrue(nodeIds.size() > 0, "自动完成任务流程失败,请确认下一个流程节点的唯一性");
            ResultResponse resultResponse = iFlowTaskStatusService
                    .passFlowNode(data.getTaskId(),
                            Integer.parseInt(nodeIds
                                    .get(0)
                                    .getStatus()));
            AssertsUtil.isTrue(!resultResponse.isSuccess(), resultResponse.getMessage());
        } else if ((AutoEnum.AUTO_ENUM_NO.getCode() + "").equals(one.getNodeRunMode())) {
            sendMessage(flowTaskStatusPo, iRabbitMqService);
        } else {
            AssertsUtil.isTrue(true, "不存在的操作类型,请确定是自动集成还是人工");
        }
    }
}
