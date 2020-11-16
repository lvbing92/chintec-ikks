package com.chintec.ikks.erp.controller;

import com.chintec.ikks.common.entity.vo.FlowInfoVo;
import com.chintec.ikks.common.util.ResultResponse;
import com.chintec.ikks.erp.annotation.PermissionAnnotation;
import com.chintec.ikks.erp.service.IProcessAndControllerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
@Slf4j
public class FlowInfoController {
    @Autowired
    private IProcessAndControllerService iProcessAndControllerService;

    @PostMapping("/process")
    @ApiOperation("流程控制管理---创建流程")
    @ResponseStatus(HttpStatus.CREATED)
    @PermissionAnnotation(code = "200301")
    public ResultResponse createFlowInfo(@Valid FlowInfoVo flowInfoVo, BindingResult result) {
        log.info("创建流程:{}", flowInfoVo);
        if (result.hasErrors()) {
            return ResultResponse.failResponse(Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
        }

        return iProcessAndControllerService.createProcess(flowInfoVo);
    }

    @GetMapping("/process/start")
    @ApiOperation("流程控制管理---开启任务流程")
    @PermissionAnnotation(code = "2003")
    public ResultResponse startFlow(Integer supplierId, HttpServletRequest request) {
        log.info("开启流程:{}", supplierId);
        return iProcessAndControllerService.startProcess(request.getHeader("access_token"), supplierId);
    }

    @PutMapping("/process/pass/{flowTaskStatusId}/{code}")
    @ApiOperation("流程控制管理---审核通过")
    @PermissionAnnotation(code = "2003")
    public ResultResponse passFlowNode(@PathVariable("flowTaskStatusId") Integer flowTaskStatusId, @PathVariable("code") Integer code, HttpServletRequest request) {
        return iProcessAndControllerService.passFlowNode(request.getHeader("access_token"), flowTaskStatusId, code);
    }

    @DeleteMapping("/process/refuse/{flowTaskStatusId}")
    @ApiOperation("流程控制管理---审核拒绝")
    @PermissionAnnotation(code = "2003")
    public ResultResponse refuseFlowNode(@PathVariable Integer flowTaskStatusId, HttpServletRequest request) {
        return iProcessAndControllerService.refuseFlowNode(request.getHeader("access_token"), flowTaskStatusId);
    }

    @PutMapping("process")
    @ApiOperation("流程---更新")
    @PermissionAnnotation(code = "200303")
    public ResultResponse update(@Valid FlowInfoVo flowInfoVo, BindingResult result) {
        if (result.hasErrors()) {
            return ResultResponse.failResponse(Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
        }
        return iProcessAndControllerService.update(flowInfoVo);
    }

    @GetMapping("process")
    @ApiOperation("流程---列表")
    @PermissionAnnotation(code = "200302")
    public ResultResponse list(@RequestParam Integer currentPage, @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        return iProcessAndControllerService.list(currentPage, pageSize);
    }

    @GetMapping("process/{id}")
    @ApiOperation("流程---详情")
    @PermissionAnnotation(code = "200304")
    public ResultResponse one(@PathVariable Integer id) {
        return iProcessAndControllerService.one(id);
    }

    @DeleteMapping("process/{id}")
    @ApiOperation("流程---删除")
    @PermissionAnnotation(code = "200305")
    public ResultResponse delete(@PathVariable Integer id) {
        return iProcessAndControllerService.delete(id);
    }
}
