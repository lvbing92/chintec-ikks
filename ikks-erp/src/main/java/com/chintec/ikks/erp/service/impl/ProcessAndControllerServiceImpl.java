package com.chintec.ikks.erp.service.impl;

import com.chintec.ikks.common.entity.vo.FlowInfoVo;
import com.chintec.ikks.common.util.ResultResponse;
import com.chintec.ikks.erp.feign.IFlowInfoService;
import com.chintec.ikks.erp.service.IProcessAndControllerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/10/21 14:02
 */
@Service
public class ProcessAndControllerServiceImpl implements IProcessAndControllerService {
    @Autowired
    private IFlowInfoService iFlowInfoService;

    @Override
    public ResultResponse createProcess(FlowInfoVo flowInfoVo) {
        return iFlowInfoService.createFlowNode(flowInfoVo);
    }
}
