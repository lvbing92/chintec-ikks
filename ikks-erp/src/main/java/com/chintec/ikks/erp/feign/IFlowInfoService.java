package com.chintec.ikks.erp.feign;

import com.chintec.ikks.common.entity.vo.FlowInfoVo;
import com.chintec.ikks.common.util.ResultResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("process")
    ResultResponse update(@RequestBody FlowInfoVo flowInfoVo);

    @GetMapping("process")
    ResultResponse list(@RequestParam Integer currentPage, @RequestParam(required = false, defaultValue = "0") Integer pageSize);

    @GetMapping("process/{id}")
    ResultResponse one(@PathVariable Integer id);

    @DeleteMapping("process/{id}")
    ResultResponse delete(@PathVariable Integer id);
}
