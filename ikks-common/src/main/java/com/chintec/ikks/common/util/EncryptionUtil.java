package com.chintec.ikks.common.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.MessageDigest;
import java.util.Base64;


/**
 * @author rubin
 */
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
            e.printStackTrace();
            return null;
        }
    }

    /**
     * BCryptPasswordEncoder加密
     *
     * @param passWord 密码
     * @return String
     */
    public static String passWordEnCode(String passWord) {
        BCryptPasswordEncoder b = new BCryptPasswordEncoder();
        return b.encode(passWord);
    }
}
