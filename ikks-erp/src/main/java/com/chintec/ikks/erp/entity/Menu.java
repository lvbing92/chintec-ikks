package com.chintec.ikks.erp.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author rubIn·lv
 * @since 2020-09-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "TMenu", description = "菜单信息")
public class Menu {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 名称
     */
    private String menuName;

    /**
     * 父Id
     */
    private String parentId;

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


}
