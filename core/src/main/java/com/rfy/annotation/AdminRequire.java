package com.rfy.annotation;

import com.rfy.enumeration.UserLevel;

import java.lang.annotation.*;

/**
 * Created by Rao on 2023/8/31 14:14
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Require(level = UserLevel.AMDIN)
public @interface AdminRequire {

}
