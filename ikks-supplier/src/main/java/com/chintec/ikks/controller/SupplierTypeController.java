package com.chintec.ikks.controller;


import com.chintec.ikks.common.entity.vo.SupplierTypeVo;
import com.chintec.ikks.common.util.ResultResponse;
import com.chintec.ikks.service.ISupplierTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

/**
 * <p>
 * 供应商类型 前端控制器
 * </p>
 *
 * @author jeff·Tang
 * @since 2020-10-19
 */
@RestController
@RequestMapping("/v1")
@Slf4j
public class SupplierTypeController {
    @Autowired
    private ISupplierTypeService iSupplierTypeService;

    @GetMapping("/supplierTypes")
    public ResultResponse types(@RequestParam Integer currentPage,
                                @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                @RequestParam(required = false) String ids) {
        log.info("list currentPage:{} ,pageSize:{}", currentPage, pageSize);
        return iSupplierTypeService.types(currentPage, pageSize,ids);
    }

    @PostMapping("/supplierType")
    @ResponseStatus(HttpStatus.CREATED)
    public ResultResponse saveType(@Valid @RequestBody SupplierTypeVo supplierTypeVo, BindingResult result) {
        log.info("save supplierTypeVo:{}", supplierTypeVo);
        if (result.hasErrors()) {
            return ResultResponse.failResponse(Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
        }
        return iSupplierTypeService.saveType(supplierTypeVo);
    }

    @PutMapping("/supplierType")
    public ResultResponse updateType(@Valid @RequestBody SupplierTypeVo supplierTypeVo, BindingResult result) {
        log.info("update supplierTypeVo:{}", supplierTypeVo);
        if (result.hasErrors()) {
            return ResultResponse.failResponse(Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
        }
        return iSupplierTypeService.updateType(supplierTypeVo);
    }

    @DeleteMapping("/supplierType/{id}")
    public ResultResponse deleteType(@PathVariable Integer id) {
        log.info("delete id:{}", id);
        return iSupplierTypeService.deleteType(id);
    }

    @GetMapping("/supplierType/{id}")
    public ResultResponse type(@PathVariable Integer id) {
        return iSupplierTypeService.type(id);
    }
}
