package com.chintec.ikks.common.util;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.util.Base64;


/**
 * @author rubin
 */
@Slf4j
public class EncryptionUtil {

    /**
     * Md5加密
     *
     * @param msg 数据
     * @return String
     */
    public static String encode(String msg) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("md5");
            return Base64.getEncoder().encodeToString(messageDigest.digest(msg.getBytes()));
        } catch (Exception e) {
            log.info("encode e:{}", e.getMessage());
            return null;
        }
    }

    /**
     * BCryptPasswordEncoder加密
     *
     * @param passWord 密码
     * @return String
     */
    public static String passWordEnCode(String passWord, Class t) {
        Method encode;
        Object invoke = null;
        try {
            Constructor constructor = t.getConstructor();
            Object o = constructor.newInstance();
            encode = t.getMethod("encode", CharSequence.class);
            invoke = encode.invoke(o, passWord);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
            log.info("re  e:{}", e.getMessage());
            return null;
        }
        return invoke + "";
    }
}
