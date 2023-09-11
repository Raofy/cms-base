package com.rfy.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;

/**
 * 请求工具类相关方法
 *
 * Created by Rao on 2023/8/28 14:31
 */
public class ResponseUtil {


    public static HttpServletResponse getResponse() {
        return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();
    }

    public static void setCurrentResponseHttpStatus(int value) {
        HttpServletResponse response = getResponse();
        response.setStatus(value);
    }
}
