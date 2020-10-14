package com.chintec.ikks.auth.request;

import com.chintec.ikks.auth.entity.Menu;
import lombok.Data;

import java.util.List;


/**
 * @author rubin·lv
 * @version 1.0
 * @date 2020/10/13 17:11
 */
@Data
public class MenuRequest {
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

    /**
     * 子菜单
     */
    private List<Menu> childList;
}
