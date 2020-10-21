package com.chintec.ikks.erp.service;

import com.chintec.ikks.common.entity.vo.FlowInfoVo;
import com.chintec.ikks.common.util.ResultResponse;

/**
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/10/21 14:00
 */
public interface IProcessAndControllerService {
    /**
     * 创建一个流程
     *
     * @param flowInfoVo
     * @return
     */
    ResultResponse createProcess(FlowInfoVo flowInfoVo);
}
