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

    @PostMapping("process")
    public ResultResponse create(@RequestBody FlowInfoVo flowInfoVo) {
        return iFlowInfoService.createFlowNode(flowInfoVo);
    }

    @PutMapping("process")
    public ResultResponse update(@RequestBody FlowInfoVo flowInfoVo) {
        return iFlowInfoService.updateFlowNode(flowInfoVo);
    }

    @GetMapping("process")
    public ResultResponse list(@RequestParam Integer currentPage, @RequestParam(required = false,defaultValue = "0") Integer pageSize) {
        return iFlowInfoService.listFlow(currentPage,pageSize);
    }

    @GetMapping("process/{id}")
    public ResultResponse one(@PathVariable Integer id) {
        return iFlowInfoService.one(id);
    }

    @DeleteMapping("process/{id}")
    public ResultResponse delete(@PathVariable Integer id) {
        return iFlowInfoService.delete(id);
    }

}
