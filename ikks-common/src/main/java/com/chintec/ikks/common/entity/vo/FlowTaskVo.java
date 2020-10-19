package com.chintec.ikks.common.entity.vo;

import lombok.Data;

/**
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/9/24 15:55
 */
@Data
public class FlowTaskVo {
    /**
     * 模块Id
     */
    private Integer moduleId;

    /**
     * 实例id
     */
    private Integer followInfoId;

    /**
     * 任务名称
     */
    private String name;

    /**
     * 状态
     */
    private String status;

}
