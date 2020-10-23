package com.chintec.ikks.process.controller;


import com.chintec.ikks.common.util.ResultResponse;
import com.chintec.ikks.process.service.IFlowTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
