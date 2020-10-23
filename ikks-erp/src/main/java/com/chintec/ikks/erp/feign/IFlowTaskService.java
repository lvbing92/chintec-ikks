package com.chintec.ikks.erp.feign;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chintec.ikks.common.entity.FlowTask;
import com.chintec.ikks.common.entity.vo.FlowTaskVo;
import com.chintec.ikks.common.util.ResultResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author jeff·Tang
 * @since 2020-09-24
 */
@FeignClient(value = "ikks-process", path = "/v1")
public interface IFlowTaskService  {
    /**
     * 创建一个流程任务
     *
     * @param flowTaskVo 任务信息类
     * @return ResultResponse
     */
//    ResultResponse createTask(FlowTaskVo flowTaskVo);

    @GetMapping("/tasks")
    ResultResponse tasks(@RequestParam Integer userId);
}
