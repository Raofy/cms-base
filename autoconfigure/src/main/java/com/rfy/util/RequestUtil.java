package com.rfy.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 请求工具类相关方法
 *
 * Created by Rao on 2023/8/28 14:31
 */
public class RequestUtil {

    /**
     * 获取当前请求
     *
     * @return 请求
     */
    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * 获取请求 url
     *
     * @return url
     */
    public static String getRequestUrl() {
        return getRequest().getServletPath();
    }

    /**
     * 获取简略信息
     *
     * @param request
     * @return 简略信息
     */
    public static String getSimpleRequest(HttpServletRequest request) {
        return String.format("%s %s", request.getMethod(), request.getServletPath());
    }

    /**
     * 获取简略信息
     *
     * @return 简略信息
     */
    public static String getSimpleRequest() {
        HttpServletRequest request = getRequest();
        return getSimpleRequest(request);
    }




}
