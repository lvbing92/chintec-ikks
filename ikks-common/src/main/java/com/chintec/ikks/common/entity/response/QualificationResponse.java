package com.chintec.ikks.common.entity.response;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author jeff·tang
 * @since 2020-10-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class QualificationResponse  {


    /**
     * 主键id
     */
    private Integer id;

    /**
     * 资质名称
     */
    private String qualificationName;

    /**
     * 资质文档类型:1文件2图片3视频
     */
    private Integer qualificationType;

    /**
     * 描述
     */
    private String qualificationDescribe;

    /**
     * 供应商类型id
     */
    private Integer categoryId;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String updateTime;

    /**
     * 操作人id
     */
    private Integer updateBy;

    /**
     * 操作人name
     */
    private String updateName;
}
