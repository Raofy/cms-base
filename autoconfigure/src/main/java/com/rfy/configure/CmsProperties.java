package com.rfy.configure;

import com.rfy.token.DoubleJWT;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by Rao on 2023/8/28 14:55
 */
@ConfigurationProperties("jin10.cms")
public class CmsProperties {


    private static final String[] DEFAULT_EXCLUDE_METHODS = new String[] {"OPTIONS"};

    private String[] excludeMethods = DEFAULT_EXCLUDE_METHODS;

    private Long tokenAccessExpire = 1 * 60 * 60L;
    private Long tokenRefreshExpire = 30 * 24 * 60 * 60L;
    private String tokenSecret = "";

    public String[] getExcludeMethods() {
        return excludeMethods;
    }

    public void setExcludeMethods(String[] excludeMethods) {
        this.excludeMethods = excludeMethods;
    }

    public Long getTokenAccessExpire() {
        return tokenAccessExpire;
    }

    public void setTokenAccessExpire(Long tokenAccessExpire) {
        this.tokenAccessExpire = tokenAccessExpire;
    }

    public Long getTokenRefreshExpire() {
        return tokenRefreshExpire;
    }

    public void setTokenRefreshExpire(Long tokenRefreshExpire) {
        this.tokenRefreshExpire = tokenRefreshExpire;
    }

    public String getTokenSecret() {
        return tokenSecret;
    }

    public void setTokenSecret(String tokenSecret) {
        this.tokenSecret = tokenSecret;
    }
}
