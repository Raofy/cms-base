package com.rfy.response;

import com.rfy.enumeration.Code;
import com.rfy.interfaces.BaseResponse;
import org.springframework.http.HttpStatus;

/**
 * 新建回调
 *
 * Created by Rao on 2023/8/28 14:31
 */
public class Created implements BaseResponse {

    private int status = Code.CREATED.getCode();

    private String message = Code.CREATED.getZhDescription();

    private int httpCode = HttpStatus.CREATED.value();

    public Created() {
    }

    public Created(String message) {
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
