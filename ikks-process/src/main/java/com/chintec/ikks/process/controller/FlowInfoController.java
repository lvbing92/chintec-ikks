package com.chintec.ikks.process.controller;


import com.chintec.ikks.common.entity.vo.FlowInfoVo;
import com.chintec.ikks.common.util.ResultResponse;
import com.chintec.ikks.process.service.IFlowInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author jeff·Tang
 * @since 2020-09-24
 */
@RestController
@RequestMapping("/v1")
public class FlowInfoController {
    @Autowired
    private IFlowInfoService iFlowInfoService;

    /**
     * 创建流程
     *
     * @param flowInfoVo 流程信息
     * @return ResultResponse
     */
    @PostMapping("process")
    public ResultResponse create(@RequestBody FlowInfoVo flowInfoVo) {
        return iFlowInfoService.createFlowNode(flowInfoVo);
    }

    /**
     * 更新流程
     *
     * @param flowInfoVo 流程信息
     * @return ResultResponse
     */
    @PutMapping("process")
    public ResultResponse update(@RequestBody FlowInfoVo flowInfoVo) {
        return iFlowInfoService.updateFlowNode(flowInfoVo);
    }

    /**
     * 查询流程列表
     *
     * @param currentPage 当前页
     * @param pageSize    页条数
     * @return ResultResponse
     */
    @GetMapping("process")
    public ResultResponse list(@RequestParam Integer currentPage, @RequestParam(required = false, defaultValue = "0") Integer pageSize) {
        return iFlowInfoService.listFlow(currentPage, pageSize);
    }

    /**
     * 流程详情
     *
     * @param id 流程Id
     * @return ResultResponse
     */
    @GetMapping("process/{id}")
    public ResultResponse one(@PathVariable Integer id) {
        return iFlowInfoService.one(id);
    }

    /**
     * 删除流程
     *
     * @param id 流程Id
     * @return ResultResponse
     */
    @DeleteMapping("process/{id}")
    public ResultResponse delete(@PathVariable Integer id) {
        return iFlowInfoService.delete(id);
    }

}
