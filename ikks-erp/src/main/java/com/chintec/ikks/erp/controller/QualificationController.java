package com.chintec.ikks.erp.controller;


import com.chintec.ikks.common.entity.vo.QualificationVo;
import com.chintec.ikks.common.util.ResultResponse;
import com.chintec.ikks.erp.service.IQualificationAndProcessService;
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
 * @author jeff·tang
 * @since 2020-10-21
 */
@RestController
@RequestMapping("/v1")
@Api(value = "Qualification", tags = {"资质文档管理"})
@Slf4j
public class QualificationController {
    @Autowired
    private IQualificationAndProcessService iQualificationAndProcessService;

    @PostMapping("/qualification")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "资质文档-保存")
    public ResultResponse saveQualification(@Valid QualificationVo qualificationVo, BindingResult result, HttpServletRequest request) {
        log.info("资质文档-保存:{}", qualificationVo);
        if (result.hasErrors()) {
            return ResultResponse.failResponse(Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
        }
        return iQualificationAndProcessService.saveQualification(qualificationVo, request.getHeader("access_token"));
    }

    @PutMapping("/qualification")
    @ApiOperation(value = "资质文档-修改")
    public ResultResponse updateQualification(@Valid QualificationVo qualificationVo, BindingResult result, HttpServletRequest request) {
        log.info("资质文档-修改:{}", qualificationVo);
        if (result.hasErrors()) {
            return ResultResponse.failResponse(Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
        }
        return iQualificationAndProcessService.updateQualification(qualificationVo, request.getHeader("access_token"));
    }

    @GetMapping("/qualifications")
    @ApiOperation(value = "资质文档-查询(包含供应商端和平台端)")
    public ResultResponse qualifications(@RequestParam Integer currentPage,
                                         @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                         @RequestParam(required = false, defaultValue = "0") Integer categoryId,
                                         HttpServletRequest request) {
        log.info("资质文档-查询:{}", pageSize);
        return iQualificationAndProcessService.qualifications(currentPage, pageSize, categoryId, request.getHeader("access_token"));
    }

    @GetMapping("/qualification/{id}")
    @ApiOperation(value = "资质文档-详情")
    public ResultResponse qualification(@PathVariable Integer id, HttpServletRequest request) {
        log.info("资质文档-详情:{}", id);
        return iQualificationAndProcessService.qualification(id, request.getHeader("access_token"));
    }

    @DeleteMapping("/qualification/{id}")
    @ApiOperation(value = "资质文档-删除")
    public ResultResponse deleteQualification(@PathVariable Integer id) {
        log.info("资质文档-删除:{}", id);
        return iQualificationAndProcessService.deleteQualification(id);
    }

    @PostMapping("/qualification/supplier")
    @ApiOperation(value = "供应商资质文档-保存(供应商端)")
    public ResultResponse saveQualificationSupplier(String qualificationSupplierVo, HttpServletRequest request) {
        log.info("资质文档-保存客户资质文档属性:{}", qualificationSupplierVo);
        return iQualificationAndProcessService.saveQualificationSupplier(qualificationSupplierVo, request.getHeader("access_token"));
    }

    @PutMapping("/qualification/supplier")
    @ApiOperation(value = "供应商资质文档-修改(供应商端)")
    public ResultResponse updateQualificationSupplier(String qualificationSupplierVo, HttpServletRequest request) {
        log.info("资质文档-修改客户资质文档属性:{}", qualificationSupplierVo);
        return iQualificationAndProcessService.updateQualificationSupplier(qualificationSupplierVo, request.getHeader("access_token"));
    }

    @DeleteMapping("/qualification/supplier/{id}")
    @ApiOperation(value = "供应商资质文档-删除(供应商端)")
    public ResultResponse deleteQualificationSupplier(@PathVariable Integer id) {
        log.info("资质文档-删除客户资质文档属性:{}", id);
        return iQualificationAndProcessService.deleteQualificationSupplier(id);
    }

    @GetMapping("/qualification/supplier/{id}")
    @ApiOperation(value = "供应商资质文档-详情(供应商端)")
    public ResultResponse qualificationSupplier(@PathVariable Integer id) {
        log.info("资质文档-详情客户资质文档属性:{}", id);
        return iQualificationAndProcessService.qualificationSupplier(id);
    }

    @GetMapping("/qualification/suppliers")
    @ApiOperation(value = "供应商资质文档-列表(供应商端)")
    public ResultResponse qualificationSuppliers(Integer qualificationSupplierId, Integer supplierId) {
        return iQualificationAndProcessService.qualificationSuppliers(qualificationSupplierId, supplierId);
    }
}
