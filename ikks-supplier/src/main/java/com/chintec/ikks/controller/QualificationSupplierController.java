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
 * 前端控制器
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

    @PutMapping("qualificationSupplier")
    public ResultResponse updateQualificationSupplier(@RequestBody @Valid List<QualificationSupplierVo> qualificationSupplierVos, BindingResult result) {
        if (result.hasErrors()) {
            return ResultResponse.failResponse(Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
        }
        return iQualificationSupplierService.updateSupplierQualification(qualificationSupplierVos);
    }

    @PostMapping("qualificationSupplier")
    public ResultResponse saveQualificationSupplier(@RequestBody @Valid List<QualificationSupplierVo> qualificationSupplierVos, BindingResult result) {
        if (result.hasErrors()) {
            return ResultResponse.failResponse(Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
        }
        return iQualificationSupplierService.saveSupplierQualification(qualificationSupplierVos);
    }

    @GetMapping("qualificationSuppliers")
    public ResultResponse qualificationSuppliers(@RequestParam Integer qualificationSupplierId, @RequestParam Integer supplierId) {
        return iQualificationSupplierService.supplierQualifications(qualificationSupplierId, supplierId);
    }

    @GetMapping("qualificationSupplier/{id}")
    public ResultResponse qualificationSupplier(@PathVariable Integer id) {
        return iQualificationSupplierService.supplierQualification(id);
    }

    @DeleteMapping("qualificationSupplier/{id}")
    public ResultResponse deleteQualificationSupplier(@PathVariable Integer id) {
        return iQualificationSupplierService.deleteSupplierQualification(id);
    }

    @GetMapping("qualificationSupplier/count")
    public ResultResponse count(@RequestParam Integer qualificationSupplierId, @RequestParam Integer supplierId) {
        return iQualificationSupplierService.totalSupplierQualification(qualificationSupplierId, supplierId);
    }

}
