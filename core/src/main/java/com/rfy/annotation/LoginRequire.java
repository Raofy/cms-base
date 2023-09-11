package com.rfy.annotation;

import com.rfy.enumeration.UserLevel;

import java.lang.annotation.*;

/**
 * Created by Rao on 2023/8/31 14:18
 */
@Documented
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Require(level = UserLevel.LOGIN)
public @interface LoginRequire {
}
