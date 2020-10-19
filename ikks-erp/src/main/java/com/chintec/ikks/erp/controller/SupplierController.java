package com.chintec.ikks.erp.controller;

import com.chintec.ikks.common.entity.po.SupplierFieldPo;
import com.chintec.ikks.common.util.ResultResponse;
import com.chintec.ikks.erp.feign.ISupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

/**
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/10/19 11:37
 */
@RestController
@RequestMapping("/v1")
public class SupplierController {
    @Autowired
    private ISupplierService iSupplierService;

    @PostMapping("/supplier/supplierField")
    @ResponseStatus(HttpStatus.CREATED)
    public ResultResponse saveField(@Valid SupplierFieldPo supplierFieldPo, BindingResult result) {
        if (result.hasErrors()) {
            return ResultResponse.failResponse(Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
        }
        return iSupplierService.saveField(supplierFieldPo);
    }

    @PutMapping("/supplier/supplierField")
    public ResultResponse updateField(@Valid SupplierFieldPo supplierFieldPo, BindingResult result) {
        if (result.hasErrors()) {
            return ResultResponse.failResponse(Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
        }
        return iSupplierService.updateField(supplierFieldPo);
    }

    @DeleteMapping("//supplier/supplierField/{id}")
    public ResultResponse deleteField(@PathVariable Integer id) {
        return iSupplierService.deleteField(id);
    }

    @GetMapping("/supplier/supplierFields")
    public ResultResponse fields(@RequestParam Integer currentPage, @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        return iSupplierService.fields(currentPage, pageSize);
    }
}
