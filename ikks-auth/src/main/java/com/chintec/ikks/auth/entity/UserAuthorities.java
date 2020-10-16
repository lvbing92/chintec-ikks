package com.chintec.ikks.auth.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 公司人员角色关系表
 * </p>
 *
 * @author rubIn·lv
 * @since 2020-10-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserAuthorities extends Model<UserAuthorities> {

    private static final long serialVersionUID = 1L;

    /**
     * 公司人员Id
     */
    private Integer companyUserId;

    /**
     * 角色Id
     */
    private Integer authorityId;


    @Override
    protected Serializable pkVal() {
        return this.companyUserId;
    }

}
