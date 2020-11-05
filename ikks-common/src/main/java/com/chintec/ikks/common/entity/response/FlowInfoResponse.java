package com.chintec.ikks.common.entity.response;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.chintec.ikks.common.entity.FlowNode;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/11/5 10:01
 */
@Data
public class FlowInfoResponse {

    private Integer id;

    /**
     * 流程名字
     */
    private String flowName;

    /**
     * 停用：0，启用：1
     */
    private String flowStatus;

    private String createTime;

    private String updateTime;

    private String updataBy;

    private List<FlowNode> flowNodes;

}
