package com.rfy.token;

/**
 * Created by Rao on 2023/9/4 17:19
 */
public class Tokens {

    private String accessToken;
    private String refreshToken;

    public Tokens(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Override
    public String toString() {
        return "Tokens{" +
                "accessToken='" + accessToken + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                '}';
    }
}
