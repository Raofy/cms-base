package com.rfy.annotation;

import com.rfy.enumeration.UserLevel;

import java.lang.annotation.*;

/**
 * Created by Rao on 2023/8/31 13:49
 */
@Documented
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Require {
    UserLevel level() default UserLevel.TOURIST;
}
