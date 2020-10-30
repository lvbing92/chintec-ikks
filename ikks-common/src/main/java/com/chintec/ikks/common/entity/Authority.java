package com.chintec.ikks.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author ruBIn·lv
 * @since 2020-08-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "Authority", description = "角色信息")
public class Authority extends Model<Authority> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "角色名称")
    private String authority;

    @ApiModelProperty(value = "是否可用")
    private Boolean enabled;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "更新人Id")
    private String updateById;

    @ApiModelProperty(value = "更新人名称")
    private String updateByName;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
