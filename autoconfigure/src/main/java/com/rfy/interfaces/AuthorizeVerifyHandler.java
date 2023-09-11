package com.rfy.interfaces;

import com.rfy.bean.MetaInfo;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 权限校验处理
 *
 * Created by Rao on 2023/8/31 17:57
 */
public interface AuthorizeVerifyHandler {
    /**
     * 处理 LoginRequire
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param metaInfo
     * @return
     */
    boolean handlerLogin(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, MetaInfo metaInfo);

    /**
     * 处理 GroupRequire
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param metaInfo
     * @return
     */
    boolean handlerGroup(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, MetaInfo metaInfo);

    /**
     * 处理 AdminRequire
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param metaInfo
     * @return
     */
    boolean handlerAdmin(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, MetaInfo metaInfo);

    /**
     * 处理 RefreshRequire
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param metaInfo
     * @return
     */
    boolean handlerRefresh(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, MetaInfo metaInfo);

    boolean handlerNotHandlerMethod(HttpServletRequest request, HttpServletResponse response, Object handler);

    default void handlerPostHandler(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView){}

    default void handlerAfterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex){}
}
