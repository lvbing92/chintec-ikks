package com.chintec.ikks.erp.feign;

import com.chintec.ikks.common.entity.vo.SupplierFieldVo;
import com.chintec.ikks.common.entity.vo.SupplierTypeVo;
import com.chintec.ikks.common.entity.vo.SupplierVo;
import com.chintec.ikks.common.util.ResultResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/10/19 11:34
 */
@FeignClient(value = "ikks-supplier", path = "/v1")
public interface ISupplierService {
    @PostMapping("/supplierField")
    ResultResponse saveField(@RequestBody SupplierFieldVo supplierFieldVo);

    @PutMapping("/supplierField")
    ResultResponse updateField(@RequestBody SupplierFieldVo supplierFieldVo);

    @DeleteMapping("/supplierField/{id}")
    ResultResponse deleteField(@PathVariable Integer id);

    @GetMapping("/supplierFields")
    ResultResponse fields(@RequestParam Integer currentPage, @RequestParam(required = false, defaultValue = "10") Integer pageSize);

    @GetMapping("/supplierField/{id}")
    ResultResponse field(@PathVariable Integer id);

    @GetMapping("/suppliers")
    ResultResponse suppliers(@RequestParam Integer currentPage,
                             @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                             @RequestParam(required = false, defaultValue = "0") Integer categoryId,
                             @RequestParam(required = false, defaultValue = "0") Integer statusId,
                             @RequestParam(required = false) String params,
                             @RequestParam(required = false) String ids);

    @GetMapping("/supplier")
    ResultResponse supplierCount();

    @PostMapping("/supplier")
    ResultResponse saveSupplier(@RequestBody @Valid SupplierVo supplierVo);

    @PutMapping("/supplier")
    ResultResponse updateSupplier(@RequestBody @Valid SupplierVo supplierVo);

    @DeleteMapping("/supplier/{id}")
    ResultResponse deleteSupplier(@PathVariable Integer id);

    @GetMapping("/supplier/{id}")
    ResultResponse supplier(@PathVariable Integer id);

    @GetMapping("/supplierTypes")
    ResultResponse types(@RequestParam Integer currentPage,
                         @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                         @RequestParam(required = false) String ids);

    @PostMapping("/supplierType")
    ResultResponse saveType(@Valid @RequestBody SupplierTypeVo supplierTypeVo);

    @PutMapping("/supplierType")
    ResultResponse updateType(@Valid @RequestBody SupplierTypeVo supplierTypeVo);

    @DeleteMapping("/supplierType/{id}")
    ResultResponse deleteType(@PathVariable Integer id);

    @GetMapping("/supplierType/{id}")
    ResultResponse type(@PathVariable Integer id);
}
