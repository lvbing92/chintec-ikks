package com.chintec.ikks.process.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chintec.ikks.common.entity.FlowInfo;
import com.chintec.ikks.common.entity.vo.FlowInfoVo;
import com.chintec.ikks.common.util.ResultResponse;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author jeff·Tang
 * @since 2020-09-24
 */
public interface IFlowInfoService extends IService<FlowInfo> {
    /**
     * 创建一个流程
     *
     * @param flowInfoVo 流程信息
     * @return ResultResponse
     */
    ResultResponse createFlowNode(FlowInfoVo flowInfoVo);

    /**
     * 更新
     *
     * @param flowInfoVo 流程信息类
     * @return re
     */
    ResultResponse updateFlowNode(FlowInfoVo flowInfoVo);

    /**
     * 列表
     *
     * @param currentPage 当前页
     * @param pageSize    数据个数
     * @return re
     */
    ResultResponse listFlow(Integer currentPage, Integer pageSize);

    /**
     * 详情
     *
     * @param id 流程id
     * @return re
     */
    ResultResponse one(Integer id);

    /**
     * 删除
     *
     * @param id 流程id
     * @return re
     */
    ResultResponse delete(Integer id);
}
