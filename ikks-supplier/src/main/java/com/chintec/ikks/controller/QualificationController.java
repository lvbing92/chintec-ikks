package com.chintec.ikks.controller;


import com.chintec.ikks.common.entity.vo.QualificationVo;
import com.chintec.ikks.common.util.ResultResponse;
import com.chintec.ikks.service.IQualificationService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

/**
 * <p>
 * 前端控制器
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

    @PostMapping("/qualification")
    @ResponseStatus(HttpStatus.CREATED)
    public ResultResponse saveQualification(@RequestBody @Valid QualificationVo qualificationVo, BindingResult result) {
        if (result.hasErrors()) {
            return ResultResponse.failResponse(Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
        }
        return iQualificationService.saveQualification(qualificationVo);
    }

    @PutMapping("/qualification")
    public ResultResponse updateQualification(@RequestBody @Valid QualificationVo qualificationVo, BindingResult result) {
        if (result.hasErrors()) {
            return ResultResponse.failResponse(Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
        }
        return iQualificationService.updateQualification(qualificationVo);
    }

    @GetMapping("/qualifications")
    public ResultResponse qualifications(@RequestParam Integer currentPage, @RequestParam(required = false, defaultValue = "10") Integer pageSize, @RequestParam(required = false, defaultValue = "0") Integer categoryId) {
        return iQualificationService.qualifications(currentPage, pageSize, categoryId);
    }

    @GetMapping("/qualification/{id}")
    public ResultResponse qualification(@PathVariable Integer id) {
        return iQualificationService.qualification(id);
    }

    @DeleteMapping("/qualification/{id}")
    public ResultResponse deleteQualification(@PathVariable Integer id) {
        return iQualificationService.deleteQualification(id);
    }
}
