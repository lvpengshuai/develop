package com.trs.core.annotations;

import java.lang.annotation.*;

/**
 * Created by root on 17-3-19.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Documented
public @interface Permission {
    String url() default "";
    //TODO 如果type == "user"，进行前端页面的权限判断
    String type() default "";
}
