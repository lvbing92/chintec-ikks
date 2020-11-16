package com.chintec.ikks.controller;


import com.chintec.ikks.common.entity.vo.QualificationSupplierVo;
import com.chintec.ikks.common.util.ResultResponse;
import com.chintec.ikks.service.IQualificationSupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 供应商资质文档 前端控制器
 * </p>
 *
 * @author jeff·tang
 * @since 2020-10-21
 */
@RestController
@RequestMapping("/v1")
public class QualificationSupplierController {
    @Autowired
    private IQualificationSupplierService iQualificationSupplierService;

    /**
     * 供应商资质文档的修改
     *
     * @param qualificationSupplierVos
     * @param result
     * @return
     */
    @PutMapping("qualificationSupplier")
    public ResultResponse updateQualificationSupplier(@RequestBody @Valid List<QualificationSupplierVo> qualificationSupplierVos, BindingResult result) {
        if (result.hasErrors()) {
            return ResultResponse.failResponse(Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
        }
        return iQualificationSupplierService.updateSupplierQualification(qualificationSupplierVos);
    }

    /**
     * 创建
     *
     * @param qualificationSupplierVos
     * @param result
     * @return
     */
    @PostMapping("qualificationSupplier")
    public ResultResponse saveQualificationSupplier(@RequestBody @Valid List<QualificationSupplierVo> qualificationSupplierVos, BindingResult result) {
        if (result.hasErrors()) {
            return ResultResponse.failResponse(Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
        }
        return iQualificationSupplierService.saveSupplierQualification(qualificationSupplierVos);
    }

    /**
     * 列表
     *
     * @param qualificationSupplierId
     * @param supplierId
     * @return
     */
    @GetMapping("qualificationSuppliers")
    public ResultResponse qualificationSuppliers(@RequestParam(required = false) Integer qualificationSupplierId, @RequestParam Integer supplierId) {
        return iQualificationSupplierService.supplierQualifications(qualificationSupplierId, supplierId);
    }

    /**
     * 详情
     *
     * @param id
     * @return
     */
    @GetMapping("qualificationSupplier/{id}")
    public ResultResponse qualificationSupplier(@PathVariable Integer id) {
        return iQualificationSupplierService.supplierQualification(id);
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @DeleteMapping("qualificationSupplier/{id}")
    public ResultResponse deleteQualificationSupplier(@PathVariable Integer id) {
        return iQualificationSupplierService.deleteSupplierQualification(id);
    }

    /**
     * 总数
     *
     * @param qualificationSupplierId
     * @param supplierId
     * @return
     */
    @GetMapping("qualificationSupplier/count")
    public ResultResponse count(@RequestParam Integer qualificationSupplierId, @RequestParam Integer supplierId) {
        return iQualificationSupplierService.totalSupplierQualification(qualificationSupplierId, supplierId);
    }

}
