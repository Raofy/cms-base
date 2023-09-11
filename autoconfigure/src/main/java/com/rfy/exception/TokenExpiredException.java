package com.rfy.exception;

import com.rfy.enumeration.Code;
import jdk.nashorn.internal.parser.Token;
import org.springframework.http.HttpStatus;

/**
 * Created by Rao on 2023/9/7 10:38
 */
public class TokenExpiredException extends HttpException{

    private static final long serialVersionUID = -5869652913094627736L;
    private int status = Code.TOKEN_EXPIRED.getCode();
    private int httpCode = HttpStatus.UNAUTHORIZED.value();

    public TokenExpiredException() {
        super(Code.TOKEN_EXPIRED.getCode(), Code.TOKEN_EXPIRED.getDescription());
    }
    public TokenExpiredException(int status) {
        super(status, Code.TOKEN_EXPIRED.getDescription());
        this.status = status;
    }
    public TokenExpiredException(String message) {
        super(Code.TOKEN_EXPIRED.getCode(), message);
    }
    public TokenExpiredException(int status, String message) {
        super(status, message);
        this.status = status;
    }

    @Override
    public int getStatus() {
        return status;
    }

    @Override
    public int getHttpCode() {
        return httpCode;
    }
}
