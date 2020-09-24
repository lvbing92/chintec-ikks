package com.chintec.ikks.process.event;

import com.alibaba.fastjson.JSONObject;
import com.chintec.ikks.common.enums.NodeStateChangeEnum;
import com.chintec.ikks.common.util.AssertsUtil;
import com.chintec.ikks.common.util.ResultResponse;
import com.chintec.ikks.process.entity.po.FlowTaskStatusPo;
import com.chintec.ikks.process.entity.po.MessageReq;
import com.chintec.ikks.process.feign.IRabbitMqService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.Message;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/9/22 14:23
 */
@WithStateMachine(id = "nodeStateMachine")
public class NodeEvent {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private IRabbitMqService iRabbitMqService;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @OnTransition(source = "PENDING", target = "GOING")
    public void going(Message<NodeStateChangeEnum> message) {
        logger.info("---节点审核中---");
        FlowTaskStatusPo flowTaskStatus = JSONObject.parseObject(JSONObject.toJSONString(message.getHeaders().get("flowTaskStatus")), FlowTaskStatusPo.class);
        AssertsUtil.isTrue(!sendMessage(flowTaskStatus).isSuccess(), "节点任务启动失败");
    }

    @OnTransition(source = "GOING", target = "PASS")
    public void pass(Message<NodeStateChangeEnum> message) {
        logger.info("---节点审核通过---");
        FlowTaskStatusPo flowTaskStatus = JSONObject.parseObject(JSONObject.toJSONString(message.getHeaders().get("flowTaskStatusPo")), FlowTaskStatusPo.class);
        Object data = flowTaskStatus.getData();

        //TODO 该节点审核任务完成 删除该节点的缓存数据
        removeTaskMachine(flowTaskStatus.getId());
    }

    @OnTransition(source = "GOING", target = "REFUSE")
    public void refuse(Message<NodeStateChangeEnum> message) {
        logger.info("---节点审核不过---");
        FlowTaskStatusPo flowTaskStatus = JSONObject.parseObject(JSONObject.toJSONString(message.getHeaders().get("flowTaskStatusPo")), FlowTaskStatusPo.class);
        Object data = flowTaskStatus.getData();
        // TODO 判断是否为完成 或直接去另外一个节点

        removeTaskMachine(flowTaskStatus.getId());
    }

    private ResultResponse sendMessage(FlowTaskStatusPo flowTaskStatus) {
        MessageReq messageReq = new MessageReq();
        messageReq.setUuid(UUID.randomUUID().toString());
        messageReq.setMessageMsg(flowTaskStatus);
        messageReq.setSuccess(true);
        return iRabbitMqService.sendMsg(messageReq, StringUtils.isEmpty(flowTaskStatus.getTime()) ? "" : flowTaskStatus.getTime());
    }

    /**
     * 当任务完成或是返回上一个节点的时候,删除该条记录缓存
     * 并将此次执行结果记录到数据库中去
     *
     * @param id 任务缓存id
     */
    private void removeTaskMachine(String id) {
        //TODO 执行结果记录到 数据库中 然后删除缓存

        Boolean delete = redisTemplate.delete(id);
        AssertsUtil.isTrue(!(delete == null ? false : delete), "删除失败任务缓存失败");
    }
}

