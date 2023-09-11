package com.rfy.response;

import com.rfy.enumeration.Code;
import com.rfy.interfaces.BaseResponse;
import org.springframework.http.HttpStatus;

/**
 * Created by Rao on 2023/8/28 14:47
 */
public class Updated implements BaseResponse {
    private int status = Code.UPDATED.getCode();
    private String message = Code.UPDATED.getZhDescription();
    private int httpCode = HttpStatus.OK.value();

    public Updated() {
    }

    public Updated(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return null;
    }

    @Override
    public int getHttpCode() {
        return 0;
    }

    @Override
    public int getStatus() {
        return 0;
    }
}
