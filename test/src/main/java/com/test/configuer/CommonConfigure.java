package com.test.configuer;

import com.rfy.bean.PermissionMetaRouter;
import com.rfy.interfaces.AuthorizeVerifyHandler;
import com.test.interceptor.AuthorizeInterceptorImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Rao on 2023/8/31 17:07
 */
@Configuration
public class CommonConfigure {
    @Bean
    public PermissionMetaRouter getRouter() {
        return new PermissionMetaRouter();
    }

}
