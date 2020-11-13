package com.chintec.ikks.process.controller;


import com.chintec.ikks.common.entity.vo.FlowTaskVo;
import com.chintec.ikks.common.enums.NodeStateEnum;
import com.chintec.ikks.common.util.ResultResponse;
import com.chintec.ikks.process.service.IFlowTaskService;
import com.chintec.ikks.process.service.IFlowTaskStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author jeff·Tang
 * @since 2020-09-24
 */
@RestController
@RequestMapping("/v1")
public class FlowTaskStatusController {
    @Autowired
    private IFlowTaskStatusService iFlowTaskStatusService;
    @Autowired
    private IFlowTaskService iFlowTaskService;

    @GetMapping("flowTaskStatus")
    public ResultResponse flowTaskStatus(@RequestParam Integer userId,
                                         @RequestParam Integer currentPage,
                                         @RequestParam(required = false, defaultValue = "0") Integer pageSize,
                                         @RequestParam(required = false, defaultValue = "0") Integer statusId,
                                         @RequestParam(required = false) String params) {
        return iFlowTaskStatusService.taskStatus(userId, currentPage, pageSize, statusId, params);
    }

    @GetMapping("flowTaskStatus/{id}")
    public ResultResponse flowTaskStatus(@PathVariable Integer id) {
        return iFlowTaskStatusService.taskStatus(id);
    }

    /**
     * 通过节点
     *
     * @param flowTaskStatusId id
     * @return re
     */
    @GetMapping("flowTaskStatus/pass/{flowTaskStatusId}/{statusCode}")
    public ResultResponse passFlowNode(@PathVariable("flowTaskStatusId") Integer flowTaskStatusId, @PathVariable("statusCode") Integer statusCode) {
        return iFlowTaskStatusService.passFlowNode(flowTaskStatusId, statusCode);
    }

    /**
     * 拒绝节点审核
     *
     * @param flowTaskStatusId id
     * @return ResultResponse re
     */
    @GetMapping("flowTaskStatus/refuse/{flowTaskStatusId}")
    public ResultResponse refuseFlowNode(@PathVariable Integer flowTaskStatusId) {
        return iFlowTaskStatusService.refuseFlowNode(flowTaskStatusId);
    }

    @GetMapping("test")
    public ResultResponse testProcess() {

        FlowTaskVo flowTaskVo = new FlowTaskVo();
        flowTaskVo.setFollowInfoId(6);
        flowTaskVo.setTaskId(7);
        flowTaskVo.setName("测试流程任务07");
        flowTaskVo.setStatus(NodeStateEnum.PENDING.getCode().toString());

        return iFlowTaskService.createTask(flowTaskVo);
    }
}
