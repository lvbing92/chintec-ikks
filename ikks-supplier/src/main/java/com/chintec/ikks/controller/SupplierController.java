package com.chintec.ikks.controller;


import com.chintec.ikks.common.entity.vo.SupplierVo;
import com.chintec.ikks.common.util.ResultResponse;
import com.chintec.ikks.service.ISupplierService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

/**
 * <p>
 * 供应商表 前端控制器
 * </p>
 *
 * @author jeff·Tang
 * @since 2020-10-19
 */
@RestController
@RequestMapping("/v1")
@Slf4j
public class SupplierController {
    @Autowired
    private ISupplierService iSupplierService;

    @GetMapping("/suppliers")
    public ResultResponse suppliers(@RequestParam Integer currentPage,
                                    @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                    @RequestParam(required = false, defaultValue = "0") Integer categoryId,
                                    @RequestParam(required = false, defaultValue = "0") Integer statusId,
                                    @RequestParam(required = false) String params) {
        log.info("list currentPage:{},pageSize:{},categoryId:{},statusId:{}.params:{}", currentPage, pageSize, categoryId, statusId, params);
        return iSupplierService.suppliers(currentPage, pageSize, categoryId, params, statusId);
    }

    @GetMapping("/supplier")
    public ResultResponse supplierCount() {
        log.info("count");
        return iSupplierService.countTotal();
    }

    @PostMapping("/supplier")
    @ResponseStatus(HttpStatus.CREATED)
    public ResultResponse saveSupplier(@RequestBody @Valid SupplierVo supplierVo, BindingResult result) {
        log.info("save supplierVo:{}", supplierVo);
        if (result.hasErrors()) {
            return ResultResponse.failResponse(Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
        }
        return iSupplierService.saveSupplier(supplierVo);
    }

    @PutMapping("/supplier")
    public ResultResponse updateSupplier(@RequestBody @Valid SupplierVo supplierVo, BindingResult result) {
        log.info("update supplierVo:{}", supplierVo);
        if (result.hasErrors()) {
            return ResultResponse.failResponse(Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
        }
        return iSupplierService.updateSupplier(supplierVo);
    }

    @DeleteMapping("/supplier/{id}")
    public ResultResponse deleteSupplier(@PathVariable Integer id) {
        log.info("delete id:{}", id);
        return iSupplierService.deleteSupplier(id);
    }

    @GetMapping("/supplier/{id}")
    public ResultResponse supplier(@PathVariable Integer id) {
        return iSupplierService.supplier(id);
    }
}
