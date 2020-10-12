package com.chintec.ikks.process.entity.po;

import com.alibaba.fastjson.JSONObject;
import com.chintec.ikks.common.enums.NodeStateEnum;
import com.chintec.ikks.process.entity.FlowTaskStatus;
import com.chintec.ikks.process.entity.vo.NodeFunctionVo;
import lombok.Data;

import java.util.List;

/**
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/9/24 9:50
 */
@Data
public class FlowTaskStatusPo {
    /**
     * 状态任务id
     */
    private String id;
    /**
     * 任务名字
     */
    private String name;
    /**
     * 任务所处的状态
     */
    private NodeStateEnum status;
    /**
     * 延时时间
     */
    private String time;
    /**
     * 任务内容
     */
    private FlowTaskStatus data;
    /**
     * 每个任务返回的状态码
     */
    private String taskStatus;

    private Integer isFinish;
    /**
     * 驳回节点
     */
    private Integer rejectNode;

    private List<NodeFunctionVo> nodeIds;

    public List<NodeFunctionVo> getNodeIds() {
        if (isFinish != null && isFinish == 3) {
            return null;
        } else if (data == null) {
            return this.nodeIds;
        }
        return JSONObject.parseArray(data.getTaskFunction(), NodeFunctionVo.class);
    }
}
