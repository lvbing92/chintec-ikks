package com.chintec.ikks.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chintec.ikks.common.entity.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author rubIn·lv
 * @since 2020-09-01
 */
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 查询菜单 列表
     *
     * @return List<Menu>
     */
//    List<Menu> getMenuList(MenuRequest menuRequest);
    List<Menu> getMenuList(@Param("id") long id);
}
