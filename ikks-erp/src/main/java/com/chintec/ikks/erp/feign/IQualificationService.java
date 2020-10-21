package com.chintec.ikks.erp.feign;

import com.chintec.ikks.common.entity.vo.QualificationVo;
import com.chintec.ikks.common.util.ResultResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author jeff·tang
 * @since 2020-10-21
 */
@FeignClient(value = "ikks-supplier", path = "/v1")
public interface IQualificationService {

    /**
     * 创建
     *
     * @param qualificationVo 实体类
     * @return ResultResponse
     */
    @PostMapping("/qualification")
    ResultResponse saveQualification(@RequestBody QualificationVo qualificationVo);

    /**
     * 修改
     *
     * @param qualificationVo 实体类
     * @return ResultResponse
     */
    @PutMapping("/qualification")
    ResultResponse updateQualification(@RequestBody QualificationVo qualificationVo);

    /**
     * 查询所有
     *
     * @param categoryId  类别id
     * @param currentPage 当前页
     * @param pageSize    条数
     * @return ResultResponse 分页结果
     */
    @GetMapping("/qualifications")
    ResultResponse qualifications(@RequestParam Integer currentPage, @RequestParam(required = false, defaultValue = "10") Integer pageSize, @RequestParam(required = false, defaultValue = "0") Integer categoryId);

    /**
     * 详情
     *
     * @param id 主键
     * @return ResultResponse
     */
    @GetMapping("/qualification/{id}")
    ResultResponse qualification(@PathVariable Integer id);

    /**
     * 详情
     *
     * @param id 删除
     * @return ResultResponse
     */
    @DeleteMapping("/qualification/{id}")
    ResultResponse deleteQualification(@PathVariable Integer id);
}
