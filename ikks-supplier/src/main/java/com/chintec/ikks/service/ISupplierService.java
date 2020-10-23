package com.chintec.ikks.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chintec.ikks.common.entity.Supplier;
import com.chintec.ikks.common.entity.vo.SupplierVo;
import com.chintec.ikks.common.util.ResultResponse;

/**
 * <p>
 * 供应商表 服务类
 * </p>
 *
 * @author jeff·Tang
 * @since 2020-10-19
 */
public interface ISupplierService extends IService<Supplier> {
    /**
     * 分页查询
     *
     * @param currentPage 当前页
     * @param pageSize    每页数据条数
     * @param categoryId  类别id
     * @param params      搜索参数
     * @param statusId    状态id
     * @param ids         id集合
     * @return resultResponse
     */
    ResultResponse suppliers(Integer currentPage, Integer pageSize, Integer categoryId, String params, Integer statusId, String ids);

    /**
     * 数据统计
     *
     * @return resultResponse
     */
    ResultResponse countTotal();

    /**
     * 创建供应商
     *
     * @param supplierVo 供应商实体类
     * @return resultResponse
     */
    ResultResponse saveSupplier(SupplierVo supplierVo);

    /**
     * 修改供应商
     *
     * @param supplierVo 供应商属性
     * @return resultResponse
     */
    ResultResponse updateSupplier(SupplierVo supplierVo);

    /**
     * 删除供应商
     *
     * @param id 唯一id
     * @return resultResponse
     */
    ResultResponse deleteSupplier(Integer id);

    /**
     * 查询详情
     *
     * @param id 供应商id
     * @return ResultResponse
     */
    ResultResponse supplier(Integer id);
}
