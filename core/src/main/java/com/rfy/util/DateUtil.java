package com.rfy.util;

import java.util.Date;

/**
 * Created by Rao on 2023/9/4 17:50
 */
public class DateUtil {

    public static Date getDurationDate(long duration) {
        long currentTimeMillis = System.currentTimeMillis();
        long l = currentTimeMillis + duration * 1000;
        return new Date(l);
    }
}
