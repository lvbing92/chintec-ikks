package com.chintec.ikks.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chintec.ikks.common.entity.Qualification;
import com.chintec.ikks.common.entity.vo.QualificationVo;
import com.chintec.ikks.common.util.ResultResponse;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author jeff·tang
 * @since 2020-10-21
 */
public interface IQualificationService extends IService<Qualification> {

    /**
     * 创建
     *
     * @param qualificationVo 实体类
     * @return ResultResponse
     */
    ResultResponse saveQualification(QualificationVo qualificationVo);

    /**
     * 修改
     *
     * @param qualificationVo 实体类
     * @return ResultResponse
     */
    ResultResponse updateQualification(QualificationVo qualificationVo);

    /**
     * 查询所有
     *
     * @param currentPage 当前页
     * @param pageSize    条数
     * @return ResultResponse 分页结果
     */
    ResultResponse qualifications(Integer currentPage, Integer pageSize,Integer categoryId);

    /**
     * 详情
     *
     * @param id 主键
     * @return ResultResponse
     */
    ResultResponse qualification(Integer id);

    /**
     * 详情
     *
     * @param id 删除
     * @return ResultResponse
     */
    ResultResponse deleteQualification(Integer id);
}
