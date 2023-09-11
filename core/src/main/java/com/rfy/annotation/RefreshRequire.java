package com.rfy.annotation;

import com.rfy.enumeration.UserLevel;

import java.lang.annotation.*;

/**
 * Created by Rao on 2023/8/31 14:19
 */
@Documented
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Require(level = UserLevel.REFRESH)
public @interface RefreshRequire {
}
