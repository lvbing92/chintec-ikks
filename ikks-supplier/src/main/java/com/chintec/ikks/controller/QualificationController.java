package com.chintec.ikks.controller;


import com.chintec.ikks.common.entity.vo.QualificationVo;
import com.chintec.ikks.common.util.ResultResponse;
import com.chintec.ikks.service.IQualificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

/**
 * <p>
 * 资质文档类型的 前端控制器
 * </p>
 *
 * @author jeff·tang
 * @since 2020-10-21
 */
@RestController
@RequestMapping("/v1")
public class QualificationController {
    @Autowired
    private IQualificationService iQualificationService;

    /**
     * 资质文档的创建
     *
     * @param qualificationVo
     * @param result
     * @return
     */
    @PostMapping("/qualification")
    @ResponseStatus(HttpStatus.CREATED)
    public ResultResponse saveQualification(@RequestBody @Valid QualificationVo qualificationVo, BindingResult result) {
        if (result.hasErrors()) {
            return ResultResponse.failResponse(Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
        }
        return iQualificationService.saveQualification(qualificationVo);
    }

    /**
     * 资质文档的修改
     *
     * @param qualificationVo
     * @param result
     * @return
     */
    @PutMapping("/qualification")
    public ResultResponse updateQualification(@RequestBody @Valid QualificationVo qualificationVo, BindingResult result) {
        if (result.hasErrors()) {
            return ResultResponse.failResponse(Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
        }
        return iQualificationService.updateQualification(qualificationVo);
    }

    /**
     * 列表
     *
     * @param currentPage
     * @param pageSize
     * @param categoryId
     * @return
     */
    @GetMapping("/qualifications")
    public ResultResponse qualifications(@RequestParam Integer currentPage, @RequestParam(required = false, defaultValue = "10") Integer pageSize, @RequestParam(required = false, defaultValue = "0") Integer categoryId) {
        return iQualificationService.qualifications(currentPage, pageSize, categoryId);
    }

    /**
     * 详情
     *
     * @param id
     * @return
     */
    @GetMapping("/qualification/{id}")
    public ResultResponse qualification(@PathVariable Integer id) {
        return iQualificationService.qualification(id);
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @DeleteMapping("/qualification/{id}")
    public ResultResponse deleteQualification(@PathVariable Integer id) {
        return iQualificationService.deleteQualification(id);
    }
}
