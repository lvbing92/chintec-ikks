package com.chintec.ikks.process.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author jeff·Tang
 * @since 2020-09-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class FlowInfo extends Model<FlowInfo> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 流程名字
     */
    private String name;

    /**
     * 停用：0，启用：1
     */
    private String status;

    /**
     * 模块ID
     */
    private Integer moduleId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String updataBy;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
