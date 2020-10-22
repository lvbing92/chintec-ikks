package com.chintec.ikks.erp.feign;

import com.chintec.ikks.common.entity.vo.FlowInfoVo;
import com.chintec.ikks.common.util.ResultResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author jeff·Tang
 * @since 2020-09-24
 */
@FeignClient(value = "ikks-process", path = "/v1")
public interface IFlowInfoService {
    /**
     * 创建一个流程
     *
     * @param flowInfoVo 流程信息
     * @return ResultResponse
     */
    @PostMapping("/process")
    ResultResponse createFlowNode(@RequestBody FlowInfoVo flowInfoVo);
}
