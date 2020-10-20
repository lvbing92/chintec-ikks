package com.chintec.ikks.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chintec.ikks.common.entity.SupplierType;
import com.chintec.ikks.common.entity.vo.SupplierTypeVo;
import com.chintec.ikks.common.util.ResultResponse;

/**
 * <p>
 * 供应商类型 服务类
 * </p>
 *
 * @author jeff·Tang
 * @since 2020-10-19
 */
public interface ISupplierTypeService extends IService<SupplierType> {
    /**
     * list
     * @param current 当前页
     * @param pageSiz 页码
     * @return ResultResponse
     */
    ResultResponse types(Integer current, Integer pageSiz);

    /**
     * 创建
     * @param supplierTypeVo 实体类
     * @return ResultResponse
     */
    ResultResponse saveType(SupplierTypeVo supplierTypeVo);

    /**
     * 更新
     * @param supplierTypeVo 实体类
     * @return ResultResponse
     */
    ResultResponse updateType(SupplierTypeVo supplierTypeVo);

    /**
     * 删除
     * @param id 主键
     * @return  ResultResponse
     */
    ResultResponse deleteType(Integer id);

    /**
     * 详情
     * @param id 主键
     * @return  ResultResponse
     */
    ResultResponse type(Integer id);
}
