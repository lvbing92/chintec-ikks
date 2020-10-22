package com.chintec.ikks.common.entity.response;

import com.chintec.ikks.common.entity.Menu;
import lombok.Data;

import java.util.List;


/**
 * @author rubin·lv
 * @version 1.0
 * @date 2020/10/13 17:11
 */
@Data
public class MenuResponse {
    /**
     * id
     */
    private Integer id;

    /**
     * 名称
     */
    private String menuName;

    /**
     * 客户命名菜单名称
     */
    private String userMenuName;

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

    /**
     * 子菜单
     */
    private List<Menu> childList;
}
