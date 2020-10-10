package com.chintec.ikks.process.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/9/24 15:15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NodeFunctionVo {
    /**
     * 方法类型
     */
    private String type;
    /**
     * 需要的状态
     */
    private String status;
    /**
     * 对应的节点id
     */
    private Integer nextNode;
}
