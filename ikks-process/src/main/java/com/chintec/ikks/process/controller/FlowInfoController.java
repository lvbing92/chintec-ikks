package com.chintec.ikks.process.controller;


import com.chintec.ikks.common.util.ResultResponse;
import com.chintec.ikks.process.entity.vo.FlowInfoVo;
import com.chintec.ikks.process.service.IFlowInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
public class FlowInfoController {
    @Autowired
    private IFlowInfoService iFlowInfoService;

    @PostMapping("process")
    public ResultResponse create(@RequestBody FlowInfoVo flowInfoVo) {
        return iFlowInfoService.createFlowNode(flowInfoVo);
    }
}
