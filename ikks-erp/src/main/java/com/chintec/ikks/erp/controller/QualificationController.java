package com.chintec.ikks.erp.controller;


import com.chintec.ikks.common.entity.vo.QualificationVo;
import com.chintec.ikks.common.util.ResultResponse;
import com.chintec.ikks.erp.service.IQualificationAndProcessService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
 * @author jeff·tang
 * @since 2020-10-21
 */
@RestController
@RequestMapping("/v1")
@Api(value = "Qualification", tags = {"资质文档管理"})
public class QualificationController {
    @Autowired
    private IQualificationAndProcessService iQualificationAndProcessService;

    @PostMapping("/qualification")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "资质文档-保存")
    public ResultResponse saveQualification(@Valid QualificationVo qualificationVo, BindingResult result, HttpServletRequest request) {
        if (result.hasErrors()) {
            return ResultResponse.failResponse(Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
        }
        return iQualificationAndProcessService.saveQualification(qualificationVo, request.getHeader("access_token"));
    }

    @PutMapping("/qualification")
    @ApiOperation(value = "资质文档-修改")
    public ResultResponse updateQualification(@Valid QualificationVo qualificationVo, BindingResult result, HttpServletRequest request) {
        if (result.hasErrors()) {
            return ResultResponse.failResponse(Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
        }
        return iQualificationAndProcessService.updateQualification(qualificationVo, request.getHeader("access_token"));
    }

    @GetMapping("/qualifications")
    @ApiOperation(value = "资质文档-查询")
    public ResultResponse qualifications(@RequestParam Integer currentPage, @RequestParam(required = false, defaultValue = "10") Integer pageSize, @RequestParam(required = false, defaultValue = "0") Integer categoryId) {
        return iQualificationAndProcessService.qualifications(currentPage, pageSize, categoryId);
    }

    @GetMapping("/qualification/{id}")
    @ApiOperation(value = "资质文档-详情")
    public ResultResponse qualification(@PathVariable Integer id) {
        return iQualificationAndProcessService.qualification(id);
    }

    @DeleteMapping("/qualification/{id}")
    @ApiOperation(value = "资质文档-删除")
    public ResultResponse deleteQualification(@PathVariable Integer id) {
        return iQualificationAndProcessService.deleteQualification(id);
    }
}
