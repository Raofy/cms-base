package com.rfy.configure;

import com.rfy.intercepter.AuthorizeInterceptor;
import com.rfy.token.DoubleJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * Created by Rao on 2023/8/28 14:59
 */
@Configuration(proxyBeanMethods = false)
@Order(Ordered.HIGHEST_PRECEDENCE)
@EnableConfigurationProperties(CmsProperties.class)
public class CmsConfigure {

    @Autowired
    CmsProperties cmsProperties;

    /**
     * 注入权限拦截器
     *
     * @return
     */
    @Bean
    public AuthorizeInterceptor authorizeIntercepter() {
        String[] excludeMethods = cmsProperties.getExcludeMethods();
        return new AuthorizeInterceptor(excludeMethods);
    }

    @Bean
    public DoubleJWT getDoubleJWT() {
        String tokenSecret = cmsProperties.getTokenSecret();
        Long tokenAccessExpire = cmsProperties.getTokenAccessExpire();
        Long tokenRefreshExpire = cmsProperties.getTokenRefreshExpire();
        if (tokenRefreshExpire == null) {
            tokenRefreshExpire = 30 * 24 * 60 * 60L;
        }
        if (tokenAccessExpire == null) {
            tokenAccessExpire = 60 * 60L;
        }
        return new DoubleJWT(tokenSecret, tokenAccessExpire, tokenRefreshExpire);
    }
}
