package com.rfy.exception;

import com.rfy.enumeration.Code;
import org.springframework.http.HttpStatus;

/**
 * Created by Rao on 2023/8/28 20:33
 */
public class ParameterException extends HttpException {
    private static final long serialVersionUID = 2311808330596251228L;
    protected int status = Code.PARAMETER_ERROR.getCode();
    protected int httpCode = HttpStatus.BAD_REQUEST.value();

    public ParameterException() {
        super(Code.PARAMETER_ERROR.getCode(), Code.PARAMETER_ERROR.getDescription());
    }

    public ParameterException(String message) {
        super(message);
    }

    public ParameterException(int code) {
        super(code, Code.PARAMETER_ERROR.getDescription());
        this.status = code;
    }

    public ParameterException(int code, String message) {
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
