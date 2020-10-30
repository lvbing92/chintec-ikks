package com.chintec.ikks.erp.feign;

import com.chintec.ikks.common.entity.vo.QualificationSupplierVo;
import com.chintec.ikks.common.util.ResultResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author jeff·tang
 * @since 2020-10-21
 */
@FeignClient(value = "ikks-supplier", path = "/v1")
public interface IQualificationSupplierService {
    /**
     * 保存用户资质文档
     *
     * @param qualificationSupplierVos 资质文档实体类
     * @return resultResponse
     */
    @PostMapping("qualificationSupplier")
    ResultResponse saveSupplierQualification(@RequestBody List<QualificationSupplierVo> qualificationSupplierVos);

    /**
     * 更新户资质文档
     *
     * @param qualificationSupplierVos 资质文档实体类
     * @return resultResponse
     */
    @PutMapping("qualificationSupplier")
    ResultResponse updateSupplierQualification(@RequestBody List<QualificationSupplierVo> qualificationSupplierVos);

    /**
     * 删除用户资质文档
     *
     * @param id 资质文档id
     * @return resultResponse
     */
    @DeleteMapping("qualificationSupplier/{id}")
    ResultResponse deleteSupplierQualification(@PathVariable Integer id);

    /**
     * 查询用户资质文档列表
     *
     * @param supplierId      商户id
     * @param qualificationId 资质id
     * @return resultResponse
     */
    @GetMapping("qualificationSuppliers")
    ResultResponse supplierQualifications(@RequestParam(required = false) Integer qualificationId, @RequestParam Integer supplierId);

    /**
     * 查询一个资质文档详情
     *
     * @param id 资质文档id
     * @return resultResponse
     */
    @GetMapping("qualificationSupplier/{id}")
    ResultResponse supplierQualification(@PathVariable Integer id);

    /**
     * 查询每个资质文档的数量
     *
     * @param qualificationId 文档id
     * @param supplierId      供应商
     * @return resultResponse 结果类
     */
    @GetMapping("qualificationSupplier/count")
    ResultResponse totalSupplierQualification(@RequestParam(required = false) Integer qualificationId, @RequestParam Integer supplierId);
}
