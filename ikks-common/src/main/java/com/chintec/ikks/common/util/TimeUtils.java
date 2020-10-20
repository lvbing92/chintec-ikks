package com.chintec.ikks.common.util;

import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/10/20 9:45
 */
public class TimeUtils {
    public static String toTimeStamp(LocalDateTime localDateTime) {
        if (StringUtils.isEmpty(localDateTime)) {
            return "";
        }
        return String.valueOf(localDateTime.toEpochSecond(ZoneOffset.UTC));
    }
}
