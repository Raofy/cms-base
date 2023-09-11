package com.rfy.annotation;

import java.lang.annotation.*;

/**
 * Created by Rao on 2023/8/31 14:21
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PermissionModule {

    String value() default "";
}
