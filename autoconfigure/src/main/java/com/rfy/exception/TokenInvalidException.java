package com.rfy.exception;

import com.rfy.enumeration.Code;
import org.springframework.http.HttpStatus;

/**
 * Created by Rao on 2023/9/7 11:19
 */
public class TokenInvalidException extends HttpException{

    private static final long serialVersionUID = -2770633655465306834L;
    private int httpCode = HttpStatus.UNAUTHORIZED.value();
    private int status = Code.TOKEN_INVALID.getCode();

    public TokenInvalidException() {
        super(Code.TOKEN_INVALID.getCode(), Code.TOKEN_INVALID.getDescription());
    }
    public TokenInvalidException(int status) {
        super(status, Code.TOKEN_INVALID.getDescription());
        this.status = status;
    }
    public TokenInvalidException(String message) {
        super(Code.TOKEN_INVALID.getCode(), message);
    }
    public TokenInvalidException(int status, String message) {
        super(status, message);
        this.status = status;
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
