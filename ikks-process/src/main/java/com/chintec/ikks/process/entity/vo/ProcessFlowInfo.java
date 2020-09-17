package com.chintec.ikks.process.entity.vo;

import lombok.Data;

import java.util.List;

/**
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/9/17 11:34
 */
@Data
public class ProcessFlowInfo  {
    private Integer id;

    /**
     * 流程名字
     */
    private String name;

    /**
     * 停用：0，启用：1
     */
    private String status;

    /**
     * 模块ID
     */
    private Integer moduleId;

    private List<ProcessFlow> processFlows;
}
