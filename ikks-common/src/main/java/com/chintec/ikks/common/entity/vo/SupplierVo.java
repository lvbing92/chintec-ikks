package com.chintec.ikks.common.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;


/**
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/10/20 9:18
 */
@Data
@ApiModel
public class SupplierVo {
    /**
     * id
     */
    private Integer id;

    /**
     * 扩展字段
     */
    @ApiModelProperty(value = "扩展字段",hidden = true)
    private String properties;

    /**
     * 公司名称
     */
    @NotBlank(message = "公司名称不能为空")
    @ApiModelProperty("公司名称")
    private String companyName;

    /**
     * 公司简称
     */
    @NotBlank(message = "公司简称不能为空")
    @ApiModelProperty("简称")
    private String shortName;

    /**
     * 主营类型
     */
    @NotBlank(message = "主营类型不能为空")
    @ApiModelProperty("类型")
    private String companyType;

    /**
     * 联系人姓名
     */
    @NotBlank(message = "联系人姓名不能为空")
    @ApiModelProperty("联系人")
    private String contactName;

    /**
     * 联系人电话
     */
    @NotBlank(message = "联系人电话不能为空")
    @ApiModelProperty("联系电话")
    private String contactPhone;

    /**
     * 联系人邮箱
     */
    @ApiModelProperty("联系人邮箱")
    private String contactEmail;

    /**
     * 法人
     */
    @NotBlank(message = "法人不能为空")
    @ApiModelProperty("法人")
    private String legalPerson;

    /**
     * 开户行
     */
    @ApiModelProperty("开户行")
    private String openingBank;

    /**
     * 银行账号
     */
    @ApiModelProperty("银行账号")
    private String bankAccount;

    /**
     * 企业logo
     */
    @NotBlank(message = "请上传企业log")
    @ApiModelProperty("logo")
    private String logo;


    /**
     * 所属地区
     */
    @NotBlank(message = "请输入正确地址")
    @ApiModelProperty("地址")
    private String affiliatingArea;

    /**
     * 成立日期
     */
    @NotBlank(message = "请选择成立日期")
    @ApiModelProperty("成立日期,时间戳 精确到s不要到毫秒")
    private String comCreateDate;

    /**
     * 类别id
     */
    @NotBlank(message = "请选择企业类别")
    @ApiModelProperty("企业类别")
    private Integer categoryId;

    /**
     * 更新人id
     */
    @ApiModelProperty(hidden = true)
    private Integer updateById;

    /**
     * 更新人名称
     */
    @ApiModelProperty(hidden = true)
    private String updateByName;
}
