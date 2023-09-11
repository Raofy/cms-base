package com.rfy.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.rfy.util.DateUtil;

import java.time.Instant;
import java.util.Date;
import java.util.Map;

/**
 * 单令牌
 * <p>
 * Created by Rao on 2023/9/4 17:26
 */
public class SingleJWT {
    private long expireTime;
    private JWTVerifier verifier;
    private JWTCreator.Builder builder;
    private Algorithm algorithm;

    public void initBuilderAndVerifier() {
        this.verifier = JWT.require(algorithm).acceptExpiresAt(expireTime).build();
        this.builder = JWT.create();

    }

    public SingleJWT(Algorithm algorithm, long expireTime) {
        this.algorithm = algorithm;
        this.expireTime = expireTime;
        this.initBuilderAndVerifier();
    }

    public SingleJWT(String secret, long expireTime) {
        this.algorithm = Algorithm.HMAC256(secret);
        this.expireTime = expireTime;
        this.initBuilderAndVerifier();
    }

    public String generateToken(String tokenType, long identity, String scope, long expireTime) {
        Date durationDate = DateUtil.getDurationDate(expireTime);
        return builder
                .withClaim("type", tokenType)
                .withClaim("identity", identity)
                .withClaim("scope", scope)
                .withExpiresAt(durationDate)
                .sign(algorithm);
    }

    public Map<String, Claim> decodeToken(String token) {
        DecodedJWT verify = verifier.verify(token);
        checkTokenExpired(verify.getExpiresAt());
        return verify.getClaims();
    }

    public void checkTokenExpired(Date expire) {
        long currentTimeMillis = System.currentTimeMillis();
        if (expire.getTime() < currentTimeMillis) {
            throw new TokenExpiredException("token is expired", expire.toInstant());
        }
    }

    public long getExpireTime() {
        return expireTime;
    }

    public JWTVerifier getVerifier() {
        return verifier;
    }

    public JWTCreator.Builder getBuilder() {
        return builder;
    }

    public Algorithm getAlgorithm() {
        return algorithm;
    }
}
