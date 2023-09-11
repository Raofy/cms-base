package com.rfy.response;

import com.rfy.enumeration.Code;
import com.rfy.interfaces.BaseResponse;
import org.springframework.http.HttpStatus;

/**
 * Created by Rao on 2023/8/28 14:45
 */
public class Deleted implements BaseResponse {
    private int status = Code.DELETE.getCode();
    private String message = Code.DELETE.getZhDescription();
    private int httpCode = HttpStatus.OK.value();

    public Deleted() {
    }

    public Deleted(String message) {
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
