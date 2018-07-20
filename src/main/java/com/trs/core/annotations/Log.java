package com.trs.core.annotations;

import java.lang.annotation.*;

/**
 * Created by root on 17-3-19.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Documented
public @interface Log {

    /*操作类型*/
    String operationType() default "";

    /*对象类型*/
    String targetType() default "";

    /*操作描述*/
    String description() default "";

    /*前台操作*/
    String isForeground() default "";
}
