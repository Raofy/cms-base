package com.rfy.exception;

import com.rfy.enumeration.Code;
import org.springframework.http.HttpStatus;

/**
 * Created by Rao on 2023/8/28 17:42
 */
public class FailedException extends HttpException {
    private static final long serialVersionUID = 1260218211666642971L;

    protected int statue = Code.FAILED.getCode();
    protected int httpCode = HttpStatus.INTERNAL_SERVER_ERROR.value();


    public FailedException() {
        super(Code.FAILED.getCode(), Code.FAILED.getDescription());
    }

    public FailedException(String message) {
        super(message);
    }

    public FailedException(int code) {
        super(code, Code.FAILED.getDescription());
        this.statue = code;
    }

    public FailedException(int code, String message) {
        super(code, message);
        this.statue = status;
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
