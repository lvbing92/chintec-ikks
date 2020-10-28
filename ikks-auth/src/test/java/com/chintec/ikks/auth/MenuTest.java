package com.chintec.ikks.auth;


import com.chintec.ikks.auth.service.IAuthorityService;
import com.chintec.ikks.auth.service.IMenuService;
import com.chintec.ikks.common.util.EncryptionUtil;
import com.chintec.ikks.common.util.ResultResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
    void queryMenuList() {
//        ResultResponse res = iAuthorityService.queryRole(4L);
//        System.out.println(res);
//        System.out.println(iMenuService.getMenuList("1"));
    }

    @Test
    void invokeTest() {
        System.out.println(EncryptionUtil.passWordEnCode("123456", BCryptPasswordEncoder.class));
    }

    @Test
    void invokeTest1() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class bCryptPasswordEncoderClass = BCryptPasswordEncoder.class;
        Constructor constructor = bCryptPasswordEncoderClass.getConstructor();
        Object o = constructor.newInstance();
        Method encode = bCryptPasswordEncoderClass.getMethod("encode", CharSequence.class);
        Object invoke = encode.invoke(o, "123456");
        System.out.println(invoke);
        HashMap<String,Object> map = new HashMap<>();
        Set<Map.Entry<String, Object>> entries = map.entrySet();
    }

}
