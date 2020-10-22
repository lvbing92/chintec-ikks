package com.chintec.ikks.erp.controller;

import com.chintec.ikks.common.entity.vo.FlowInfoVo;
import com.chintec.ikks.common.util.ResultResponse;
import com.chintec.ikks.erp.service.IProcessAndControllerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
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
@Api(value = "flowInfo", tags = {"流程控制以及管理"})
public class FlowInfoController {
    @Autowired
    private IProcessAndControllerService iProcessAndControllerService;

    @PostMapping("/process")
    @ApiOperation("流程控制管理---创建流程")
    public ResultResponse createFlowInfo(@Valid FlowInfoVo flowInfoVo, BindingResult result) {
        if (result.hasErrors()) {
            return ResultResponse.failResponse(Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
        }
        return iProcessAndControllerService.createProcess(flowInfoVo);
    }
}
