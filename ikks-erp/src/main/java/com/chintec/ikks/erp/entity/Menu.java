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
     * url
     */
    private String url;
    /**
     * 图片
     */
    private String icon;

    /**
     * 模块类型
     */
    private String modelType;

    /**
     * 父Id
     */
    private String parentId;

}
