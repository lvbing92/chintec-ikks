package com.chintec.ikks.common.entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
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
@ApiModel
public class QualificationVo {
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 资质名称
     */
    @NotBlank(message = "资质名称不能为空")
    @ApiModelProperty("资质名称")
    private String qualificationName;

    /**
     * 资质文档类型:1文件2图片3视频
     */
    @ApiModelProperty("资质类型 1文件2图片3视频 ")
    private Integer qualificationType;

    /**
     * 描述
     */
    @NotBlank(message = "资质描述不能为空")
    @ApiModelProperty("资质描述")
    private String qualificationDescribe;

    /**
     * 供应商类型id
     */
    @ApiModelProperty(hidden = true)
    private Integer categoryId=0;


    /**
     * 操作人id
     */
    @ApiModelProperty(hidden = true)
    private Integer updateBy;

    /**
     * 操作人name
     */
    @ApiModelProperty(hidden = true)
    private String updateName;


}
