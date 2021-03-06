package com.chintec.ikks.controller;


import com.chintec.ikks.common.entity.vo.SupplierFieldVo;
import com.chintec.ikks.common.util.ResultResponse;
import com.chintec.ikks.service.ISupplierFieldService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

/**
 * <p>
 * 供应商属性字段表 前端控制器
 * </p>
 *
 * @author jeff·Tang
 * @since 2020-10-19
 */
@RestController
@RequestMapping("/v1")
@Slf4j
public class SupplierFieldController {
    @Autowired
    private ISupplierFieldService iSupplierFieldService;

    @PutMapping("/supplierField")
    public ResultResponse updateField(@RequestBody @Valid SupplierFieldVo supplierFieldVo, BindingResult results) {
        log.info("update supplierFieldPo :{}", supplierFieldVo);
        if (results.hasErrors()) {
            return ResultResponse.failResponse(Objects.requireNonNull(results.getFieldError()).getDefaultMessage());
        }
        return iSupplierFieldService.updateField(supplierFieldVo);
    }

    @PostMapping("/supplierField")
    @ResponseStatus(HttpStatus.CREATED)
    public ResultResponse saveField(@RequestBody @Valid SupplierFieldVo supplierFieldVo, BindingResult results) {
        log.info("save supplierFieldPo :{}", supplierFieldVo);
        if (results.hasErrors()) {
            return ResultResponse.failResponse(Objects.requireNonNull(results.getFieldError()).getDefaultMessage());
        }
        return iSupplierFieldService.saveField(supplierFieldVo);
    }

    @DeleteMapping("/supplierField/{id}")
    public ResultResponse deleteField(@PathVariable Integer id) {
        log.info("delete id:{}", id);
        return iSupplierFieldService.deleteField(id);
    }

    @GetMapping("/supplierFields")
    public ResultResponse fields(@RequestParam Integer currentPage, @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        log.info("list pageSize:{}", pageSize);
        return iSupplierFieldService.fields(currentPage, pageSize);
    }

    @GetMapping("/supplierField/{id}")
    public ResultResponse field(@PathVariable Integer id) {
        return iSupplierFieldService.field(id);
    }
}
