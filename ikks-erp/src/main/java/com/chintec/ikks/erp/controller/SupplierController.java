package com.chintec.ikks.erp.controller;

import com.chintec.ikks.common.entity.vo.SupplierFieldVo;
import com.chintec.ikks.common.entity.vo.SupplierTypeVo;
import com.chintec.ikks.common.entity.vo.SupplierVo;
import com.chintec.ikks.common.util.ResultResponse;
import com.chintec.ikks.erp.service.ISupplierErpService;
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
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/10/19 11:37
 */
@RestController
@RequestMapping("/v1")
@Api(value = "Supplier", tags = {"供应商管理"})
public class SupplierController {
    @Autowired
    private ISupplierErpService iSupplierErpService;

    @PostMapping("/supplier/supplierField")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "自定义字段-保存")
    public ResultResponse saveField(@Valid SupplierFieldVo supplierFieldVo, BindingResult result) {
        if (result.hasErrors()) {
            return ResultResponse.failResponse(Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
        }
        return iSupplierErpService.saveField(supplierFieldVo);
    }

    @PutMapping("/supplier/supplierField")
    @ApiOperation(value = "自定义字段-修改")
    public ResultResponse updateField(@Valid SupplierFieldVo supplierFieldVo, BindingResult result) {
        if (result.hasErrors()) {
            return ResultResponse.failResponse(Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
        }
        return iSupplierErpService.updateField(supplierFieldVo);
    }

    @DeleteMapping("//supplier/supplierField/{id}")
    @ApiOperation(value = "自定义字段-删除")
    public ResultResponse deleteField(@PathVariable Integer id) {
        return iSupplierErpService.deleteField(id);
    }

    @GetMapping("/supplier/supplierFields")
    @ApiOperation(value = "自定义字段-列表")
    public ResultResponse fields(@RequestParam Integer currentPage, @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        return iSupplierErpService.fields(currentPage, pageSize);
    }

    @GetMapping("/supplier/supplierField/{id}")
    @ApiOperation(value = "自定义字段-详情")
    public ResultResponse field(@PathVariable Integer id) {
        return iSupplierErpService.field(id);
    }

    @GetMapping("/suppliers")
    @ApiOperation(value = "供应商-列表")
    public ResultResponse suppliers(@RequestParam Integer currentPage,
                                    @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                    @RequestParam(required = false, defaultValue = "0") Integer categoryId,
                                    @RequestParam(required = false, defaultValue = "0") Integer statusId,
                                    @RequestParam(required = false) String params, HttpServletRequest request) {
        return iSupplierErpService.suppliers(currentPage, pageSize, categoryId, statusId, params, request.getHeader("access_token"));
    }

    @GetMapping("/supplier")
    @ApiOperation(value = "供应商-列表类别和个数统计")
    public ResultResponse supplierCount() {
        return iSupplierErpService.supplierCount();
    }

    @PostMapping("/supplier")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "供应商-保存")
    public ResultResponse saveSupplier(@Valid SupplierVo supplierVo, BindingResult result) {
        if (result.hasErrors()) {
            return ResultResponse.failResponse(Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
        }
        return iSupplierErpService.saveSupplier(supplierVo);
    }

    @PutMapping("/supplier")
    @ApiOperation(value = "供应商-修改")
    public ResultResponse updateSupplier(@Valid SupplierVo supplierVo, BindingResult result) {
        if (result.hasErrors()) {
            return ResultResponse.failResponse(Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
        }
        return iSupplierErpService.updateSupplier(supplierVo);
    }

    @DeleteMapping("/supplier/{id}")
    @ApiOperation(value = "供应商-删除")
    public ResultResponse deleteSupplier(@PathVariable Integer id) {
        return iSupplierErpService.deleteSupplier(id);
    }

    @GetMapping("/supplier/{id}")
    @ApiOperation(value = "供应商-详情")
    public ResultResponse supplier(@PathVariable Integer id) {
        return iSupplierErpService.supplier(id);
    }

    @GetMapping("/supplier/supplierTypes")
    @ApiOperation(value = "供应商类别-列表")
    public ResultResponse types(@RequestParam Integer currentPage, @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        return iSupplierErpService.types(currentPage, pageSize);
    }

    @PostMapping("/supplier/supplierType")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "供应商类别-保存")
    public ResultResponse saveType(@Valid SupplierTypeVo supplierTypeVo, BindingResult result) {
        if (result.hasErrors()) {
            return ResultResponse.failResponse(Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
        }
        return iSupplierErpService.saveType(supplierTypeVo);
    }

    @PutMapping("/supplier/supplierType")
    @ApiOperation(value = "供应商类别-修改")
    public ResultResponse updateType(@Valid SupplierTypeVo supplierTypeVo, BindingResult result) {
        if (result.hasErrors()) {
            return ResultResponse.failResponse(Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
        }
        return iSupplierErpService.updateType(supplierTypeVo);
    }

    @DeleteMapping("/supplier/supplierType/{id}")
    @ApiOperation(value = "供应商类别-删除")
    public ResultResponse deleteType(@PathVariable Integer id) {
        return iSupplierErpService.deleteType(id);
    }

    @GetMapping("/supplier/supplierType/{id}")
    @ApiOperation(value = "供应商类别-详情")
    public ResultResponse type(@PathVariable Integer id) {
        return iSupplierErpService.type(id);
    }
}
