package com.rfy.response;

import com.rfy.enumeration.Code;
import com.rfy.interfaces.BaseResponse;
import org.springframework.http.HttpStatus;

/**
 * Created by Rao on 2023/8/28 14:31
 */
public class Success implements BaseResponse {
    private int status = Code.SUCCESS.getCode();
    private String message = Code.SUCCESS.getZhDescription();
    private int httpCode = HttpStatus.OK.value();

    public Success() {
    }

    public Success(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
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
