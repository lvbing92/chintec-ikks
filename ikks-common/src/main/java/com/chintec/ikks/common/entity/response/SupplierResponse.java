package com.chintec.ikks.common.entity.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * <p>
 * 供应商表
 * </p>
 *
 * @author jeff·Tang
 * @since 2020-10-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel
public class SupplierResponse {


    /**
     * id
     */
    private Integer id;

    /**
     * 扩展字段
     */
    @ApiModelProperty("供应商-扩展字段")
    private String properties;

    /**
     * 公司名称
     */
    @ApiModelProperty("供应商-公司名字")
    private String companyName;

    /**
     * 公司简称
     */
    @ApiModelProperty("供应商-简称")
    private String shortName;

    /**
     * 主营类型
     */
    @ApiModelProperty("供应商-主营类型")
    private String companyType;

    /**
     * 联系人姓名
     */
    @ApiModelProperty("供应商-联系人姓名")
    private String contactName;

    /**
     * 联系人电话
     */
    @ApiModelProperty("供应商-联系人电话")
    private String contactPhone;

    /**
     * 联系人邮箱
     */
    @ApiModelProperty("供应商-邮箱")
    private String contactEmail;


    /**
     * 开户行
     */
    @ApiModelProperty("供应商-开户行")
    private String openingBank;

    /**
     * 银行账号
     */
    @ApiModelProperty("供应商-账户")
    private String bankAccount;

    /**
     * 注册资金
     */
    @ApiModelProperty("供应商-注册资金")
    private BigDecimal registeredCapital;

    /**
     * 企业logo
     */
    @ApiModelProperty("供应商-logo")
    private String logo;

    /**
     * 电子签名
     */
    @ApiModelProperty("供应商-电子签名")
    private String electronicSignature;

    /**
     * 所属地区
     */
    @ApiModelProperty("供应商-所属地区")
    private String comAddress;

    /**
     * 成立日期
     */
    @ApiModelProperty("供应商-成立日期 时间戳 精确到s需要乘以1000")
    private String comCreateDate;

    /**
     * 创建日期
     */
    @ApiModelProperty("供应商-创建日期 时间戳 精确到s需要乘以1000")
    private String createTime;

    /**
     * 更新日期
     */
    @ApiModelProperty("供应商-修改日期 时间戳 精确到s需要乘以1000")
    private String updateTime;


    /**
     * 更新人名称
     */
    private String updateByName;


    /**
     * 资质是否审核通过, 1待审核 2审核中3通过 4未通过
     */
    private Integer isAuthenticated;
    /**
     * 类别id
     */
    @ApiModelProperty("供应商-类别")
    private Integer categoryId;

}
