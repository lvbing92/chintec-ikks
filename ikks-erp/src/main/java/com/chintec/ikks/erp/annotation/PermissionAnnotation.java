package com.chintec.ikks.erp.annotation;

import java.lang.annotation.*;

/**
 * @author rubin·lv
 * @version 1.0
 * @date 2020/10/27 15:34
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PermissionAnnotation {
    String code() default "";
}
