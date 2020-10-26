package com.chintec.ikks.common.entity.vo;

import lombok.Data;

/**
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/10/23 14:46
 */
@Data
public class FlowTaskStatusVo {

    private Integer id;

    /**
     * 是否被展示 0 是 1不是
     */
    private Integer isViewed;
}
