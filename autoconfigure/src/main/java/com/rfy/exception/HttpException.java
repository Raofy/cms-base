package com.rfy.exception;

import com.rfy.enumeration.Code;
import com.rfy.interfaces.BaseResponse;
import org.springframework.http.HttpStatus;

/**
 * Created by Rao on 2023/8/28 16:42
 */
public class HttpException extends RuntimeException implements BaseResponse {

    private static final long serialVersionUID = 1058682412697427275L;
    protected int httpCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
    protected int status = Code.INTERNAL_SERVER_ERROR.getCode();

    public HttpException() {
        super(Code.INTERNAL_SERVER_ERROR.getDescription());
    }

    public HttpException(String message) {
        super(message);
    }

    public HttpException(int code) {
        super(Code.INTERNAL_SERVER_ERROR.getDescription());
        this.status = code;
    }

    public HttpException(int code, int httpCode) {
        this.status = code;
        this.httpCode = httpCode;
    }

    public HttpException(int code, String message) {
        super(message);
        this.status = code;
    }

    public HttpException(int code, int httpCode , String message) {
        super(message);
        this.status = code;
        this.httpCode = httpCode;
    }

    public HttpException(Throwable throwable, int code) {
        super(throwable);
        this.status = code;
    }

    public HttpException(Throwable throwable, int code, int httpCode) {
        super(throwable);
        this.status = code;
        this.httpCode = httpCode;
    }

    public HttpException(Throwable throwable, String message) {
        super(message, throwable);
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
