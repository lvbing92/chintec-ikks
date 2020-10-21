package com.chintec.ikks.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chintec.ikks.common.entity.Qualification;
import com.chintec.ikks.common.entity.vo.QualificationVo;
import com.chintec.ikks.common.util.AssertsUtil;
import com.chintec.ikks.common.util.PageResultResponse;
import com.chintec.ikks.common.util.ResultResponse;
import com.chintec.ikks.mapper.QualificationMapper;
import com.chintec.ikks.service.IQualificationService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author jeff·tang
 * @since 2020-10-21
 */
@Service
public class QualificationServiceImpl extends ServiceImpl<QualificationMapper, Qualification> implements IQualificationService {

    @Override
    public ResultResponse saveQualification(QualificationVo qualificationVo) {
        Qualification qualification = new Qualification();
        BeanUtils.copyProperties(qualificationVo, qualification);
        qualification.setCreateTime(LocalDateTime.now(ZoneOffset.UTC));
        qualification.setIsDeleted(1);
        saveAndUpdate(qualification);
        return ResultResponse.successResponse("操作成功");
    }

    @Override
    public ResultResponse updateQualification(QualificationVo qualificationVo) {
        AssertsUtil.isTrue(StringUtils.isEmpty(qualificationVo.getId()), "请选择要修改的内容");
        Qualification byId = this.getById(qualificationVo.getId());
        AssertsUtil.isTrue(byId == null, "要修改的内容不存在");
        assert byId != null;
        BeanUtils.copyProperties(qualificationVo, byId);
        saveAndUpdate(byId);
        return ResultResponse.successResponse("操作成功");
    }

    @Override
    public ResultResponse qualifications(Integer currentPage, Integer pageSize, Integer categoryId) {
        IPage<Qualification> page = this.page(new Page<>(currentPage, pageSize), new QueryWrapper<Qualification>().lambda()
                .eq(categoryId != 0, Qualification::getCategoryId, categoryId)
                .eq(Qualification::getIsDeleted, 1)
                .orderByDesc(Qualification::getCreateTime));
        PageResultResponse<Qualification> pageResultResponse = new PageResultResponse<>(page.getTotal(), currentPage, pageSize);
        pageResultResponse.setTotalPages(page.getPages());
        pageResultResponse.setResults(page.getRecords());
        return ResultResponse.successResponse(pageResultResponse);
    }

    @Override
    public ResultResponse qualification(Integer id) {
        AssertsUtil.isTrue(StringUtils.isEmpty(id), "请选择要查询的内容");
        return ResultResponse.successResponse(this.getById(id));
    }

    @Override
    public ResultResponse deleteQualification(Integer id) {
        AssertsUtil.isTrue(StringUtils.isEmpty(id), "请选择要删除的内容");
        Qualification byId = this.getById(id);
        AssertsUtil.isTrue(byId == null, "要删除的内容不存在");
        assert byId != null;
        byId.setIsDeleted(0);
        saveAndUpdate(byId);
        return ResultResponse.successResponse("操作成功");
    }

    private void saveAndUpdate(Qualification qualification) {
        qualification.setUpdateTime(LocalDateTime.now(ZoneOffset.UTC));
        AssertsUtil.isTrue(!this.saveOrUpdate(qualification), "操作失败");
    }
}
