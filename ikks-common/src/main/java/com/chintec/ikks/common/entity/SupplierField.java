package com.chintec.ikks.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 供应商属性字段表
 * </p>
 *
 * @author jeff·Tang
 * @since 2020-10-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SupplierField extends Model<SupplierField> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 英文字段
     */
    private String field;

    /**
     * 中文字段
     */
    private String fieldName;

    /**
     * 字段属性，如：文本,图片,视频
     */
    private String fieldType;

    /**
     * 字段说明
     */
    private String fieldExplain;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 更新人Id
     */
    private Integer updateById;

    /**
     * 更新人名称
     */
    private String updateByName;

    /**
     * 是否有效
     */
    private Integer isDeleted;

    /**
     * 所属节点
     */
    private Integer nodeId;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
