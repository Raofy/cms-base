package com.rfy.token;

import ch.qos.logback.core.subst.Token;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.rfy.constant.TokenConstant;
import com.rfy.util.DateUtil;

import java.util.Date;
import java.util.Map;

/**
 * 双令牌
 *
 * Created by Rao on 2023/9/4 17:26
 */
public class DoubleJWT {
    private long accessExpireTime;
    private long refreshExpireTime;
    private JWTVerifier accessVerifier;
    private JWTVerifier refreshVerifier;
    private JWTCreator.Builder builder;
    private Algorithm algorithm;

    public DoubleJWT(Algorithm algorithm, long accessExpireTime, long refreshExpireTime) {
        this.algorithm = algorithm;
        this.accessExpireTime = accessExpireTime;
        this.refreshExpireTime = refreshExpireTime;
        this.initVerifierAndBuilder();
    }

    public DoubleJWT(String secret, long accessExpireTime, long refreshExpireTime) {
        this.algorithm = Algorithm.HMAC256(secret);
        this.accessExpireTime = accessExpireTime;
        this.refreshExpireTime = refreshExpireTime;
        this.initVerifierAndBuilder();
    }

    public Tokens generateToken(long identity) {
        String access = this.generateToken("access", identity, "scope", this.accessExpireTime);
        String refresh = this.generateToken("refresh", identity, "scope", this.refreshExpireTime);
        return new Tokens(access, refresh);
    }
    public String generateToken(String type, long identity, String scope, long accessExpireTime) {
        Date durationDate = DateUtil.getDurationDate(accessExpireTime);
        return this.builder
                .withClaim("type", type)
                .withClaim("identity", identity)
                .withClaim("scope", scope)
                .withExpiresAt(durationDate)
                .sign(algorithm);
    }

    public String generateToken(String type, String identity, String scope, long accessExpireTime) {
        Date durationDate = DateUtil.getDurationDate(accessExpireTime);
        return this.builder
                .withClaim("type", type)
                .withClaim("identity", identity)
                .withClaim("scope", scope)
                .withExpiresAt(durationDate)
                .sign(algorithm);
    }

    public Map<String, Claim> decodeAccessToken(String token) {
        DecodedJWT verify = accessVerifier.verify(token);
        Date expiresAt = verify.getExpiresAt();
        checkTokenExpired(expiresAt);
        checkTokenIsAccess(verify.getClaim("type").asString(), TokenConstant.ACCESS_TYPE);
        checkTokenIsScope(verify.getClaim("scope").asString());
        return verify.getClaims();
    }

    public Map<String, Claim> decodeRefreshToken(String token) {
        DecodedJWT verify = accessVerifier.verify(token);
        Date expiresAt = verify.getExpiresAt();
        checkTokenExpired(expiresAt);
        checkTokenIsAccess(verify.getClaim("type").asString(), TokenConstant.REFRESH_TYPE);
        checkTokenIsScope(verify.getClaim("scope").asString());
        return verify.getClaims();
    }

    public void initVerifierAndBuilder() {
        accessVerifier = JWT.require(algorithm).acceptExpiresAt(this.accessExpireTime).build();
        refreshVerifier = JWT.require(algorithm).acceptExpiresAt(this.refreshExpireTime).build();
        builder = JWT.create();
    }

    public void checkTokenIsAccess(String type, String accessType) {
        if (type == null && !type.equals(accessType)) {
            throw new InvalidClaimException("token type is invalid");
        }
    }

    public void checkTokenIsScope(String scope) {
        if (scope == null && !scope.equals(TokenConstant.SCOPE)) {
            throw new InvalidClaimException("token scope is invalid");
        }
    }
    public void checkTokenExpired(Date expire) {
        long currentTimeMillis = System.currentTimeMillis();
        if (expire.getTime() < currentTimeMillis) {
            throw new TokenExpiredException("token is expired", expire.toInstant());
        }
    }

}
