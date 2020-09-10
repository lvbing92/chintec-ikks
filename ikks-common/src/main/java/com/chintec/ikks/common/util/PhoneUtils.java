package com.chintec.ikks.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author rubin
 */
public class PhoneUtils {
    /**
     * 手机号码校验
     *
     * @param phone 手机号
     * @return true 为有效手机号 false 无效
     */
    public static boolean phoneCheck(String phone) {
        String regex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$";
        boolean isMatch = false;
        if (phone.length() == 11) {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phone);
            isMatch = m.matches();
        }
        return isMatch;
    }
}
