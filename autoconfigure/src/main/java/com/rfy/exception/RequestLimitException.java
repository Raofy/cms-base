package com.rfy.exception;

import com.rfy.enumeration.Code;
import org.springframework.http.HttpStatus;

/**
 * Created by Rao on 2023/8/28 20:44
 */
public class RequestLimitException extends HttpException{
    private static final long serialVersionUID = -4108799365663036498L;
    protected int status = Code.REQUEST_LIMIT.getCode();
    protected int httpCode = HttpStatus.TOO_MANY_REQUESTS.value();

    public RequestLimitException() {
        super(Code.REQUEST_LIMIT.getCode(), Code.REQUEST_LIMIT.getDescription());
    }

    public RequestLimitException(String message) {
        super(message);
    }
    public RequestLimitException(int code) {
        super(code, Code.REQUEST_LIMIT.getDescription());
        this.status = code;
    }

    public RequestLimitException(int code, String message) {
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
