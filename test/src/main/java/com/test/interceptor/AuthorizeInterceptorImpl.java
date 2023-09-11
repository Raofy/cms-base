package com.test.interceptor;

import com.auth0.jwt.exceptions.*;
import com.auth0.jwt.interfaces.Claim;
import com.rfy.bean.MetaInfo;
import com.rfy.exception.AuthorizationException;
import com.rfy.exception.NotFoundException;
import com.rfy.exception.TokenInvalidException;
import com.rfy.interfaces.AuthorizeVerifyHandler;
import com.rfy.token.DoubleJWT;
import com.test.common.util.LocalUser;
import com.test.entity.LinPermission;
import com.test.entity.LinUser;
import com.test.service.ILinGroupService;
import com.test.service.ILinUserService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by Rao on 2023/8/31 18:49
 */
@Component
public class AuthorizeInterceptorImpl implements AuthorizeVerifyHandler {
    public final static String AUTHORIZATION_HEADER = "Authorization";
    public final static String BEARER_PATTERN = "^Bearer$";

    @Autowired
    ILinUserService iLinUserService;
    @Autowired
    DoubleJWT doubleJWT;
    @Autowired
    ILinGroupService iLinGroupService;
    @Override
    public boolean handlerLogin(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, MetaInfo metaInfo) {
        String authorization = verifyHeader(httpServletRequest, httpServletResponse);
        Map<String, Claim> claimMap;
        try {
            claimMap = doubleJWT.decodeAccessToken(authorization);
        } catch (TokenExpiredException e) {
            throw new com.rfy.exception.TokenExpiredException(10051, e.getMessage());
        } catch (AlgorithmMismatchException | SignatureVerificationException | JWTDecodeException | InvalidClaimException e) {
            throw new TokenInvalidException(10041, e.getMessage());
        }
        return getClaim(claimMap);
    }

    @Override
    public boolean handlerGroup(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, MetaInfo metaInfo) {
        this.handlerLogin(httpServletRequest, httpServletResponse, metaInfo);
        LinUser user = LocalUser.getLocalUser();
        boolean admin = verifyAdmin(user);
        if (admin) {
            return true;
        }
        String permission = metaInfo.getPermission();
        String module = metaInfo.getModule();
        List<LinPermission> userPermission = iLinUserService.getUserPermission(user);
        boolean matched = userPermission.stream().anyMatch(item -> item.getName().equals(permission) && item.getModule().equals(module));
        if (!matched) {
            throw new AuthorizationException("you don't have the permission to access", 10001);
        }
        return true;
    }

    @Override
    public boolean handlerAdmin(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, MetaInfo metaInfo) {
        handlerLogin(httpServletRequest, httpServletResponse, metaInfo);
        LinUser localUser = LocalUser.getLocalUser();
        if (!verifyAdmin(localUser)) {
            throw new AuthorizationException("you don't have the permission to access", 10001);
        }
        return true;
    }

    @Override
    public boolean handlerRefresh(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, MetaInfo metaInfo) {
        String authorization = verifyHeader(httpServletRequest, httpServletResponse);
        Map<String, Claim> claimMap;
        try {
            claimMap = doubleJWT.decodeRefreshToken(authorization);
        } catch (TokenExpiredException e) {
            throw new com.rfy.exception.TokenExpiredException(10051, e.getMessage());
        } catch (AlgorithmMismatchException | SignatureVerificationException | JWTDecodeException | InvalidClaimException e) {
            throw new TokenInvalidException(10041, e.getMessage());
        }
        return getClaim(claimMap);
    }

    @Override
    public boolean handlerNotHandlerMethod(HttpServletRequest request, HttpServletResponse response, Object handler) {
        return true;
    }


    @Override
    public void handlerAfterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // 请求处理完之后一定要移除当前用户信息
        LocalUser.cleanLocalUser();
    }

    public boolean getClaim(Map<String, Claim> claim) {
        if (claim == null) {
            throw new TokenInvalidException(10041, "Token is invalid, can't be decode");
        }
        Integer identity = claim.get("identity").asInt();
        LinUser user = iLinUserService.getById(identity);
        if (user == null) {
            throw new NotFoundException(10021, "user not found");
        }
        String avatatUrl = null;
        if (user.getAvatar() == null) {
            avatatUrl = null;
        } else if (user.getAvatar().startsWith("http")) {
            avatatUrl = user.getAvatar();
        } else {

        }
        user.setAvatar(avatatUrl);
        LocalUser.setLocalUser(user);
        return true;
    }

    public String verifyHeader(HttpServletRequest request, HttpServletResponse response) {
        String authorization = request.getHeader(AUTHORIZATION_HEADER);
        if (Strings.isBlank(authorization)) {
            throw new AuthorizationException("authorization field is required", 10012);
        }
        String[] s = authorization.split(" ");
        if (s.length != 2) {
            throw new AuthorizationException("authorization field is invaild", 1001);
        }
        String bearn = s[0];
        if (!Pattern.matches(BEARER_PATTERN, bearn)) {
            throw new AuthorizationException("authorization field is invaild", 10013);
        }
        String auth = s[1];
        return auth;
    }

    private boolean verifyAdmin(LinUser user) {
        return iLinGroupService.checkIsRootByUserId(user.getId());
    }
}
