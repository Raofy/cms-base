package com.rfy.annotation;

import java.lang.annotation.*;

/**
 * Created by Rao on 2023/8/31 14:23
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PermissionMeta {

    String value();

    String module() default "";

    boolean mount() default true;
}
