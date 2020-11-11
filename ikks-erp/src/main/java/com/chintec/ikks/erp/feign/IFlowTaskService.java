package com.chintec.ikks.erp.feign;

import com.chintec.ikks.common.entity.vo.FlowTaskStatusVo;
import com.chintec.ikks.common.entity.vo.FlowTaskVo;
import com.chintec.ikks.common.util.ResultResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author jeff·Tang
 * @since 2020-09-24
 */
@FeignClient(value = "ikks-process", path = "/v1")
public interface IFlowTaskService {
    /**
     * 创建一个流程任务
     *
     * @param flowTaskVo 任务信息类
     * @return ResultResponse
     */
    @PostMapping("task")
    ResultResponse createTask(FlowTaskVo flowTaskVo);

    @GetMapping("/tasks")
    ResultResponse tasks(@RequestParam Integer userId);

    @PutMapping("/task")
    ResultResponse updateTask(@RequestBody FlowTaskStatusVo flowTaskStatusVo);

    @GetMapping("flowTaskStatus")
    ResultResponse flowTaskStatus(@RequestParam Integer userId,
                                  @RequestParam Integer currentPage,
                                  @RequestParam(required = false, defaultValue = "0") Integer pageSize,
                                  @RequestParam(required = false, defaultValue = "0") Integer statusId,
                                  @RequestParam(required = false) String params);

    @GetMapping("flowTaskStatus/{id}")
    ResultResponse flowTaskStatus(@PathVariable Integer id);

    /**
     * 通过节点审核
     *
     * @param flowTaskStatusId
     * @return
     */
    @GetMapping("flowTaskStatus/pass/{flowTaskStatusId}/{statusCode}")
    ResultResponse passFlowNode(@PathVariable("flowTaskStatusId") Integer flowTaskStatusId, @PathVariable("statusCode") Integer statusCode);

    /**
     * 拒绝节点审核
     *
     * @param flowTaskStatusId
     * @return ResultResponse
     */
    @GetMapping("flowTaskStatus/refuse/{flowTaskStatusId}")
    ResultResponse refuseFlowNode(@PathVariable Integer flowTaskStatusId);
}
