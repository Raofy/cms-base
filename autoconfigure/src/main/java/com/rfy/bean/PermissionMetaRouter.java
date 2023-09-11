package com.rfy.bean;

import com.rfy.annotation.PermissionMeta;
import com.rfy.annotation.PermissionModule;
import com.rfy.enumeration.UserLevel;
import com.rfy.util.AnnotationUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cglib.core.ReflectUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 权限信息路由
 *
 * Created by Rao on 2023/8/31 14:28
 */
public class PermissionMetaRouter implements BeanPostProcessor {
    private Map<String, MetaInfo> metaInfoMap = new ConcurrentHashMap<>();
    private Map<String, Map<String, Set<String>>> metaRouter = new ConcurrentHashMap<>();


    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    /**
     * 扫描注解信息，并提取加入路由容器
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Controller controllerAnnotation = AnnotationUtils.findAnnotation(bean.getClass(), Controller.class);
        if (controllerAnnotation == null) {
            return bean;
        }
        Method[] declaredMethods = ReflectionUtils.getDeclaredMethods(bean.getClass());
        for (Method method : declaredMethods) {
            System.out.println(controllerAnnotation.value());
            PermissionMeta permissionMetaAnnotation = AnnotationUtils.findAnnotation(method, PermissionMeta.class);
            if (permissionMetaAnnotation != null && permissionMetaAnnotation.mount() == true) {
                String permission = permissionMetaAnnotation.value();
                // 获取用户等级
                UserLevel level = AnnotationUtil.findRequire(method.getAnnotations());
                putOneIntoMetaInfoMap(method, permission, permissionMetaAnnotation.module(), level);
            }
        }
        return bean;
    }

    private void putOneIntoMetaInfoMap(Method method, String permission, String module, UserLevel level) {
        if (StringUtils.isEmpty(module)) {
            // 尝试获取类上的permissionModule信息
            PermissionModule annotation = AnnotationUtils.getAnnotation(method.getDeclaringClass(), PermissionModule.class);
            if (annotation != null) {
                module = StringUtils.isEmpty(annotation.value()) ? method.getDeclaringClass().getName() : annotation.value();
            }
        }
        String methodName = method.getName();
        String className = method.getDeclaringClass().getName();
        String identity = String.format("%s#%s", className, methodName);
        MetaInfo metaInfo = new MetaInfo(module, permission, identity, level);
        metaInfoMap.put(identity, metaInfo);
        putMetaIntoRouterMeta(metaInfo, identity);
    }

    private void putMetaIntoRouterMeta(MetaInfo metaInfo, String identity) {
        String module = metaInfo.getModule();
        String permission = metaInfo.getPermission();
        if (metaRouter.containsKey(module)) {
            Map<String, Set<String>> moduleMap = metaRouter.get(module);
            putPermissionToModule(moduleMap, identity, permission);
        } else {
            HashMap<String, Set<String>> moduleMap = new HashMap<>();
            putPermissionToModule(moduleMap, identity, permission);
            metaRouter.put(module, moduleMap);
        }
    }

    private void putPermissionToModule(Map<String, Set<String>> moduleMap, String identity, String auth) {
        if (moduleMap.containsKey(auth)) {
            moduleMap.get(auth).add(identity);
        } else {
            HashSet<String> identitySet = new HashSet<>();
            identitySet.add(identity);
            moduleMap.put(auth, identitySet);
        }
    }

    public PermissionMetaRouter() {
    }

    public MetaInfo findMeta(String identity) {
        return metaInfoMap.get(identity);
    }

    public Map<String, MetaInfo> getMetaInfoMap() {
        return metaInfoMap;
    }
}
