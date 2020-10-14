package com.chintec.ikks.auth.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chintec.ikks.auth.entity.Menu;
import com.chintec.ikks.auth.mapper.MenuMapper;
import com.chintec.ikks.auth.service.IMenuService;
import com.chintec.ikks.common.util.AssertsUtil;
import com.chintec.ikks.common.util.ResultResponse;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author rubIn·lv
 * @since 2020-09-01
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Override
    public ResultResponse getMenuList() {
        List<Menu> menuList = this.baseMapper.getMenuList();
        return ResultResponse.successResponse("查询菜单成功", menuList);
    }

    /**
     * 新增编辑菜单保存
     *
     * @param menu 菜单信息
     * @return ResultResponse
     */
    @Override
    public ResultResponse addOrUpdateMenu(Menu menu) {
        if(StringUtil.isNullOrEmpty(menu.getId().toString())){
            menu.setCreateTime(LocalDateTime.now());
            //添加用户
            boolean creFlag = this.save(menu);
            AssertsUtil.isTrue(creFlag, "保存失败！");
        }else{
            menu.setUpdateTime(LocalDateTime.now());
            this.updateById(menu);
        }
        return ResultResponse.successResponse("保存成功！");
    }

    /**
     * 通过Id查询菜单
     *
     * @param id 菜单Id
     * @return ResultResponse
     */
    @Override
    public ResultResponse queryMenu(String id) {
        //查询用户
        Menu menu = getById(new QueryWrapper<Menu>().lambda().eq(Menu::getId, id));
        //查询当前用户
        return ResultResponse.successResponse("查询用户详情成功", menu);
    }

    /**
     * 删除菜单
     *
     * @param id 菜单Id
     * @return ResultResponse
     */
    @Override
    public ResultResponse deleteMenu(String id) {
        this.baseMapper.deleteById(id);
        return ResultResponse.successResponse("删除成功");
    }
}
