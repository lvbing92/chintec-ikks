package com.chintec.ikks.auth;


import com.chintec.ikks.auth.service.IAuthorityService;
import com.chintec.ikks.auth.service.IMenuService;
import com.chintec.ikks.common.util.ResultResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author rubin·lv
 * @version 1.0
 * @date 2020/10/13 17:31
 */
@SpringBootTest
public class MenuTest {
    @Autowired
    private IMenuService iMenuService;
    @Autowired
    private IAuthorityService iAuthorityService;

    @Test
    void queryMenuList(){
        ResultResponse res = iAuthorityService.queryRole(4L);
        System.out.println(res);
//        System.out.println(iMenuService.getMenuList("1"));
    }
}
