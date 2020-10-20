package com.chintec.ikks.common.entity.response;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 供应商类型
 * </p>
 *
 * @author jeff·Tang
 * @since 2020-10-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel
public class SupplierTypeResponse {

    private Integer id;

    /**
     * 供应商类型
     */
    private String name;

    /**
     * 描述
     */
    private String describe;

    /**
     * 类型状态码
     */
    private Integer typeCode;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 修改时间
     */
    private String updateTime;


    /**
     * 创建人姓名
     */
    private String updateName;


    /**
     * 节点id
     */
    private Integer flowId;

    /**
     * 节点名称
     */
    private String flowName;


}
