package com.trs.core.annotations;

import java.lang.annotation.*;

/**
 * Created by zly on 2017-6-12.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Documented
public @interface AccessLog {
    String url() default "";
}
