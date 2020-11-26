package com.chintec.ikks.process.controller;


import com.chintec.ikks.common.entity.vo.FlowTaskStatusVo;
import com.chintec.ikks.common.entity.vo.FlowTaskVo;
import com.chintec.ikks.common.util.ResultResponse;
import com.chintec.ikks.process.service.IFlowTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

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
public class FlowTaskController {
    @Autowired
    private IFlowTaskService iFlowTaskService;

    /**
     * 流程任务查询
     *
     * @param userId 用户Id
     * @return ResultResponse
     */
    @GetMapping("/tasks")
    public ResultResponse tasks(@RequestParam Integer userId) {
        return iFlowTaskService.tasks(userId);
    }

    /**
     * 更新任务
     *
     * @param flowTaskStatusVo 任务信息
     * @param result           参数校验结果
     * @return ResultResponse
     */
    @PutMapping("/task")
    public ResultResponse updateTask(@RequestBody FlowTaskStatusVo flowTaskStatusVo, BindingResult result) {
        if (result.hasErrors()) {
            return ResultResponse.failResponse(Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
        }
        return iFlowTaskService.updateTask(flowTaskStatusVo);
    }

    /**
     * 创建任务
     *
     * @param flowTaskVo 任务信息
     * @param result     参数检验结果
     * @return ResultResponse
     */
    @PostMapping("/task")
    public ResultResponse creatTask(@RequestBody FlowTaskVo flowTaskVo, BindingResult result) {
        if (result.hasErrors()) {
            return ResultResponse.failResponse(Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
        }
        return iFlowTaskService.createTask(flowTaskVo);
    }
}
