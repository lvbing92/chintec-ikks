package com.chintec.ikks.process.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chintec.ikks.common.entity.FlowInfo;
import com.chintec.ikks.common.entity.FlowNode;
import com.chintec.ikks.common.entity.response.FlowInfoResponse;
import com.chintec.ikks.common.entity.vo.FlowInfoVo;
import com.chintec.ikks.common.entity.vo.FlowNodeVo;
import com.chintec.ikks.common.util.AssertsUtil;
import com.chintec.ikks.common.util.PageResultResponse;
import com.chintec.ikks.common.util.ResultResponse;
import com.chintec.ikks.common.util.TimeUtils;
import com.chintec.ikks.process.mapper.FlowInfoMapper;
import com.chintec.ikks.process.service.IFlowInfoService;
import com.chintec.ikks.process.service.IFlowNodeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
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
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public ResultResponse createFlowNode(FlowInfoVo flowInfoVo) {
        FlowInfo flowInfo = new FlowInfo();
        BeanUtils.copyProperties(flowInfoVo, flowInfo);
        flowInfo.setFlowStatus("1");
        flowInfo.setUpdateTime(LocalDateTime.now());
        flowInfo.setCreateTime(LocalDateTime.now());
        flowInfo.setUpdataBy("");
        AssertsUtil.isTrue(!this.saveOrUpdate(flowInfo), "创建流程信息失败");
        return saveFlowNodes(flowInfoVo.getFlowNodes(), flowInfo.getId());
    }

    /**
     * 更新流程信息
     *
     * @param flowInfoVo 流程信息类
     * @return ResultResponse
     */
    @Override
    public ResultResponse updateFlowNode(FlowInfoVo flowInfoVo) {
        AssertsUtil.isTrue(StringUtils.isEmpty(flowInfoVo.getId()), "请选择要修改的内容");
        FlowInfo byId = this.getById(flowInfoVo.getId());
        AssertsUtil.isTrue(byId == null, "要修改的内容不存在");
        assert byId != null;
        BeanUtils.copyProperties(flowInfoVo, byId);
        byId.setUpdateTime(LocalDateTime.now());
        byId.setUpdataBy("");
        AssertsUtil.isTrue(!this.saveOrUpdate(byId), "创建流程信息失败");
        return updateFlowNodes(flowInfoVo.getFlowNodes());
    }

    /**
     *
     * @param currentPage 当前页
     * @param pageSize    数据个数
     * @return
     */
    @Override
    public ResultResponse listFlow(Integer currentPage, Integer pageSize) {
        IPage<FlowInfo> page = this.page(new Page<>(currentPage, pageSize));
        PageResultResponse<FlowInfoResponse> pageResultResponse = new PageResultResponse<>(page.getTotal(), currentPage, pageSize);
        pageResultResponse.setResults(page.getRecords().stream().map(s -> {
            FlowInfoResponse flowInfoResponse = new FlowInfoResponse();
            BeanUtils.copyProperties(s, flowInfoResponse);
            flowInfoResponse.setCreateTime(TimeUtils.toTimeStamp(s.getCreateTime()));
            flowInfoResponse.setUpdateTime(TimeUtils.toTimeStamp(s.getUpdateTime()));
            return flowInfoResponse;
        }).collect(Collectors.toList()));
        pageResultResponse.setTotalPages(page.getPages());
        return ResultResponse.successResponse(pageResultResponse);
    }

    @Override
    public ResultResponse one(Integer id) {
        FlowInfo byId = this.getById(id);
        FlowInfoResponse flowInfoResponse = new FlowInfoResponse();
        BeanUtils.copyProperties(byId, flowInfoResponse);
        flowInfoResponse.setCreateTime(TimeUtils.toTimeStamp(byId.getCreateTime()));
        flowInfoResponse.setUpdateTime(TimeUtils.toTimeStamp(byId.getUpdateTime()));
        flowInfoResponse.setFlowNodes(iFlowNodeService.list(new QueryWrapper<FlowNode>().lambda()
                .eq(FlowNode::getFlowInformationId, id)));
        return ResultResponse.successResponse(flowInfoResponse);
    }

    @Override
    public ResultResponse delete(Integer id) {
        AssertsUtil.isTrue(!this.removeById(id), "删除失败");
        AssertsUtil.isTrue(!iFlowNodeService.remove(new QueryWrapper<FlowNode>().lambda().eq(FlowNode::getFlowInformationId, id)), "删除失败");
        return ResultResponse.successResponse("删除成功");
    }

    /**
     * 保存流程节点信息
     *
     * @param flowNodeVos 流程节点信息
     * @param flowId      流程Id
     * @return ResultResponse
     */
    private ResultResponse saveFlowNodes(List<FlowNodeVo> flowNodeVos, Integer flowId) {
        AssertsUtil.isTrue(!iFlowNodeService.saveBatch(flowNodeVos.stream().map(flowNodeVo -> {
            FlowNode flowNode = new FlowNode();
            BeanUtils.copyProperties(flowNodeVo, flowNode);
            flowNode.setCreateTime(LocalDateTime.now());
            flowNode.setUpdataBy("");
            flowNode.setFlowInformationId(flowId);
            flowNode.setNextNodes(JSONObject.toJSONString(flowNodeVo.getNextNodes()));
            flowNode.setProveNodes(JSONObject.toJSONString(flowNodeVo.getProveNodes()));
            flowNode.setUpdateTime(LocalDateTime.now());
            flowNode.setNextNodeCondition(JSONObject.toJSONString(flowNodeVo.getNodeFunctionVos()));
            return flowNode;
        }).collect(Collectors.toList())), "创建流程信息失败");
        return ResultResponse.successResponse("创建流程信息成功");
    }

    /**
     * 更新流程节点信息
     *
     * @param flowNodeVos 流程节点信息
     * @return ResultResponse
     */
    private ResultResponse updateFlowNodes(List<FlowNodeVo> flowNodeVos) {
        AssertsUtil.isTrue(!iFlowNodeService.saveOrUpdateBatch(flowNodeVos.stream().map(s -> {
            AssertsUtil.isTrue(StringUtils.isEmpty(s.getId()), "节点未选择");
            FlowNode byId = iFlowNodeService.getById(s.getId());
            BeanUtils.copyProperties(s, byId);
            byId.setUpdateTime(LocalDateTime.now(ZoneOffset.UTC));
            return byId;
        }).collect(Collectors.toList())), "修改流程信息失败");
        return ResultResponse.successResponse("修改流程信息成功");
    }
}
