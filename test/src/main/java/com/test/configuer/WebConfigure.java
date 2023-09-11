package com.test.configuer;

import com.rfy.intercepter.AuthorizeInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by Rao on 2023/9/1 10:30
 */
@Configuration(proxyBeanMethods = false)
public class WebConfigure implements WebMvcConfigurer {
    @Autowired
    AuthorizeInterceptor authorizeInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizeInterceptor);
    }
}
