package com.rfy.exception;

import com.rfy.enumeration.Code;
import org.springframework.http.HttpStatus;

/**
 * Created by Rao on 2023/8/28 18:12
 */
public class NotFoundException extends HttpException{
    private static final long serialVersionUID = 9043158041170770452L;
    protected int status = Code.NOT_FOUND.getCode();
    protected int httpCode = HttpStatus.NOT_FOUND.value();

    public NotFoundException() {
        super(Code.NOT_FOUND.getCode(), Code.NOT_FOUND.getDescription());
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(int code) {
        super(code, Code.NOT_FOUND.getDescription());
        this.status = code;
    }

    public NotFoundException(int code, String message) {
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
