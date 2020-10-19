package com.chintec.ikks.erp.feign;

import com.chintec.ikks.common.entity.po.SupplierFieldPo;
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
    ResultResponse saveField(@RequestBody SupplierFieldPo supplierFieldPo);

    @PutMapping("/supplierField")
    ResultResponse updateField(@RequestBody SupplierFieldPo supplierFieldPo);

    @DeleteMapping("/supplierField/{id}")
    ResultResponse deleteField(@PathVariable Integer id);

    @GetMapping("/supplierFields")
    ResultResponse fields(@RequestParam Integer currentPage, @RequestParam(required = false, defaultValue ="10") Integer pageSize);
}
