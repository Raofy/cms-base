package com.rfy.util;

import com.rfy.annotation.Require;
import com.rfy.enumeration.UserLevel;

import java.lang.annotation.Annotation;

/**
 * Created by Rao on 2023/8/31 15:11
 */
public class AnnotationUtil {

    /**
     * 获取用户等级
     *
     * @param annotations
     * @return
     */
    public static UserLevel findRequire(Annotation[] annotations) {
        for (Annotation annotation : annotations) {
            Class<? extends Annotation> aClass = annotation.annotationType();
            Require require = aClass.getAnnotation(Require.class);
            if (require != null) {
                return  require.level();
            }
        }
        return UserLevel.TOURIST;
    }
}
