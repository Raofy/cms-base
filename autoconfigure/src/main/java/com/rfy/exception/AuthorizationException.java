package com.rfy.exception;

import com.rfy.enumeration.Code;
import org.springframework.http.HttpStatus;

/**
 * Created by Rao on 2023/9/5 15:53
 */
public class AuthorizationException extends HttpException{
    private static final long serialVersionUID = 75880285505498722L;
    protected int httpCode = HttpStatus.UNAUTHORIZED.value();
    protected int status = Code.UN_AUTHORIZATION.getCode();

    public AuthorizationException() {
        super(Code.UN_AUTHORIZATION.getCode(), Code.UN_AUTHORIZATION.getDescription());
    }
    public AuthorizationException(int code) {
        super(code, Code.UN_AUTHORIZATION.getDescription());
        this.status = code;
    }
    public AuthorizationException(String message) {
        super(message);
    }
    public AuthorizationException(String message, int code) {
        super(code, message);
        this.status = code;
    }
    @Override
    public int getHttpCode() {
        return httpCode;
    }

    @Override
    public int getStatus() {
        return status;
    }
}
