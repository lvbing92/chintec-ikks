package com.chintec.ikks.auth;


import com.chintec.ikks.auth.service.IMenuService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author rubin·lv
 * @version 1.0
 * @date 2020/10/13 17:31
 */
@SpringBootTest
public class MenuTest {
    @Autowired
    private IMenuService iMenuService;

    @Test
    void queryMenuList(){
        System.out.println(iMenuService.getMenuList());
    }
}
