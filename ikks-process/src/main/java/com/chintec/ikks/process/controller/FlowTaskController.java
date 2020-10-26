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

    @GetMapping("/tasks")
    public ResultResponse tasks(@RequestParam Integer userId) {
        return iFlowTaskService.tasks(userId);
    }

    @PutMapping("/task")
    public ResultResponse updateTask(@RequestBody FlowTaskStatusVo flowTaskStatusVo,BindingResult result) {
        if (result.hasErrors()) {
            return ResultResponse.failResponse(Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
        }
        return iFlowTaskService.updateTask(flowTaskStatusVo);
    }

    @PostMapping("/task")
    public ResultResponse creatTask(@RequestBody FlowTaskVo flowTaskVo, BindingResult result) {
        if (result.hasErrors()) {
            return ResultResponse.failResponse(Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
        }
        return iFlowTaskService.createTask(flowTaskVo);
    }
}
