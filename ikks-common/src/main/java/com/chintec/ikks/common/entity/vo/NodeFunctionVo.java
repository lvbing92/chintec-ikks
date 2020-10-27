package com.chintec.ikks.common.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/9/24 15:15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel
public class NodeFunctionVo {
    /**
     * 方法类型
     */
    @ApiModelProperty("方法类型")
    private String type;
    /**
     * 需要的状态
     */
    @NotBlank(message = "需要的状态不能为空")
   @ApiModelProperty("需要的状态")
    private String status;
    /**
     * 对应的节点id
     */
    @ApiModelProperty("对应的节点信息")
    private Integer nextNode;
}
