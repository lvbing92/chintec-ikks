package com.chintec.ikks.auth.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chintec.ikks.auth.entity.Menu;
import com.chintec.ikks.auth.mapper.MenuMapper;
import com.chintec.ikks.auth.service.IMenuService;
import com.chintec.ikks.common.util.ResultResponse;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author rubIn·lv
 * @since 2020-09-01
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Override
    public ResultResponse getMenuList(Integer pageSize, Integer currentPage, String status, String searchValue, String sorted) {
        return null;
    }

    @Override
    public ResultResponse addMenu(Menu menu) {
        return null;
    }

    @Override
    public ResultResponse queryMenu(String id) {
        return null;
    }

    @Override
    public ResultResponse deleteMenu(String id) {
        return null;
    }
}
