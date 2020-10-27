package com.chintec.ikks.common.util;

import com.chintec.ikks.common.exception.NoLoginException;
import com.chintec.ikks.common.exception.ParamsException;

/**
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/6/19 12:42
 */
public class AssertsUtil {
    public static void isTrue(Boolean t, String message, Integer code) {
        if (t) {
            throw new ParamsException(code, message);
        }
    }

    public static void isTrue(Boolean t, String message) {
        if (t) {
            throw new ParamsException(message);
        }
    }

    public static void noLogin(Boolean t, String message) {
        if (t) {
            throw new NoLoginException(message);
        }
    }

    public static void noLogin(Boolean t) {
        if (t) {
            throw new NoLoginException();
        }
    }
}
