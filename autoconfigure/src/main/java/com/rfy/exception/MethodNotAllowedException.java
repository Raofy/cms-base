package com.rfy.exception;

import com.rfy.enumeration.Code;
import org.springframework.http.HttpStatus;

/**
 * Created by Rao on 2023/8/28 20:24
 */
public class MethodNotAllowedException extends HttpException {
    private static final long serialVersionUID = -2808213482322602097L;
    protected int status = Code.METHOD_NOT_ALLOWED.getCode();
    protected int httpCode = HttpStatus.METHOD_NOT_ALLOWED.value();

    public MethodNotAllowedException() {
        super(Code.METHOD_NOT_ALLOWED.getCode(), Code.METHOD_NOT_ALLOWED.getDescription());
    }

    public MethodNotAllowedException(String message) {
        super(message);
    }

    public MethodNotAllowedException(int code) {
        super(code, Code.METHOD_NOT_ALLOWED.getDescription());
        this.status = code;
    }

    public MethodNotAllowedException(int code, String message) {
        super(code, message);
        this.status = code;
    }

    @Override
    public int getHttpCode() {
        return this.httpCode;
    }

    @Override
    public int getStatus() {
        return this.status;
    }
}
