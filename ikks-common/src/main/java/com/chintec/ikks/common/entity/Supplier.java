package com.chintec.ikks.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

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
public class Supplier extends Model<Supplier> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 扩展字段
     */
    private String properties;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 公司简称
     */
    private String shortName;

    /**
     * 主营类型
     */
    private String companyType;

    /**
     * 联系人姓名
     */
    private String contactName;

    /**
     * 联系人电话
     */
    private String contactPhone;

    /**
     * 联系人邮箱
     */
    private String contactEmail;

    /**
     * 开户行
     */
    private String openingBank;

    /**
     * 银行账号
     */
    private String bankAccount;

    /**
     * 注册资金
     */
    private BigDecimal registeredCapital;

    /**
     * 企业logo
     */
    private String logo;

    /**
     * 电子签名
     */
    private String electronicSignature;

    /**
     * 所属地区
     */
    private String comAddress;

    /**
     * 成立日期
     */
    private LocalDateTime comCreateDate;

    /**
     * 创建日期
     */
    private LocalDateTime createTime;

    /**
     * 更新日期
     */
    private LocalDateTime updateTime;

    /**
     * 更新人id
     */
    private Integer updateById;

    /**
     * 更新人名称
     */
    private String updateByName;

    /**
     * 是否有效  0无效 1有效
     */
    private Integer isDeleted;

    /**
     * 资质是否审核通过, 1待审核 2审核中3通过 4未通过
     */
    private Integer isAuthenticated;
    /**
     * 类别id
     */
    private Integer categoryId;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
