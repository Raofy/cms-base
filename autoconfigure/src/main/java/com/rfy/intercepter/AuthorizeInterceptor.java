package com.rfy.intercepter;

import com.rfy.bean.MetaInfo;
import com.rfy.bean.PermissionMetaRouter;
import com.rfy.enumeration.UserLevel;
import com.rfy.interfaces.AuthorizeVerifyHandler;
import com.rfy.util.AnnotationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * SpringMVC 请求拦截处理权限
 *
 * Created by Rao on 2023/8/31 16:50
 */
public class AuthorizeInterceptor extends HandlerInterceptorAdapter {

    String[] excludeMethods = new String[]{"OPTIONS"};
    @Autowired
    PermissionMetaRouter permissionMetaRouter;
    @Autowired
    AuthorizeVerifyHandler authorizeVerifyHandler;

    public AuthorizeInterceptor() {

    }
    public AuthorizeInterceptor(String[] excludeMethods) {
        this.excludeMethods = excludeMethods;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (checkInExclude(request.getMethod())) {
            return true;
        }
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            String methodName = method.getName();
            String className = method.getDeclaringClass().getName();
            String identity = String.format("%s#%s", className, methodName);
            MetaInfo meta = permissionMetaRouter.findMeta(identity);
            if (meta == null) {
                return this.handlerNotMeta(request, response, method);
            } else {
                return this.handlerMeta(request, response, meta);
            }
        } else {
            return authorizeVerifyHandler.handlerNotHandlerMethod(request, response, handler);
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        authorizeVerifyHandler.handlerPostHandler(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        authorizeVerifyHandler.handlerAfterCompletion(request, response, handler, ex);
    }

    private boolean handlerNotMeta(HttpServletRequest request, HttpServletResponse response, Method method) {
        Annotation[] annotations = method.getAnnotations();
        UserLevel level = AnnotationUtil.findRequire(annotations);
        switch (level) {
            case GROUP:
            case LOGIN:
                return authorizeVerifyHandler.handlerLogin(request, response, null);
            case AMDIN:
                return authorizeVerifyHandler.handlerAdmin(request, response, null);
            case REFRESH:
                return authorizeVerifyHandler.handlerRefresh(request, response, null);
            default:
                return true;
        }
    }
    private boolean handlerMeta(HttpServletRequest request, HttpServletResponse response, MetaInfo metaInfo) {
        UserLevel level = metaInfo.getUserLevel();
        switch (level) {
            case GROUP:
                return authorizeVerifyHandler.handlerGroup(request, response, metaInfo);
            case LOGIN:
                return authorizeVerifyHandler.handlerLogin(request, response, metaInfo);
            case AMDIN:
                return authorizeVerifyHandler.handlerAdmin(request, response, metaInfo);
            case REFRESH:
                return authorizeVerifyHandler.handlerRefresh(request, response, metaInfo);
            default:
                return true;
        }
    }
    private boolean checkInExclude(String method) {
        for (String excludeMethod : excludeMethods) {
            if (excludeMethod.equals(method)) {
                return true;
            }
        }
        return false;
    }
}
