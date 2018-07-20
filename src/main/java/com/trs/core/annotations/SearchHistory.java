package com.trs.core.annotations;

import java.lang.annotation.*;

/**
 * Created by root on 17-3-19.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Documented
public @interface SearchHistory {
    String url() default "";
}
