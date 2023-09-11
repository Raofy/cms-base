package com.rfy.vo;

import com.rfy.enumeration.Code;
import com.rfy.util.RequestUtil;
import com.rfy.util.ResponseUtil;
import org.springframework.http.HttpStatus;

/**
 * http返回结果
 *
 * Created by Rao on 2023/8/28 14:31
 */
public class UnifyResponseVO<T> {

    private Integer status;

    private T message;

    private String request;

    public UnifyResponseVO() {
        this.status = Code.SUCCESS.getCode();
        this.request = RequestUtil.getSimpleRequest();
    }

    public UnifyResponseVO(Integer code) {
        this.status = code;
        this.request = RequestUtil.getSimpleRequest();
    }

    public UnifyResponseVO(T message) {
        this.status = Code.SUCCESS.getCode();
        this.message = message;
        this.request = RequestUtil.getSimpleRequest();
    }

    public UnifyResponseVO(HttpStatus httpStatus) {
        this.status = Code.SUCCESS.getCode();
        this.message = (T) Code.SUCCESS.getDescription();
        this.request = RequestUtil.getSimpleRequest();
        ResponseUtil.setCurrentResponseHttpStatus(httpStatus.value());
    }

    public UnifyResponseVO(Integer code, T message) {
        this.status = code;
        this.message = message;
        this.request = RequestUtil.getSimpleRequest();
    }

    public UnifyResponseVO(Integer code, HttpStatus httpStatus) {
        this.status = code;
        this.request = RequestUtil.getSimpleRequest();
        ResponseUtil.setCurrentResponseHttpStatus(httpStatus.value());
    }

    public UnifyResponseVO(T message, HttpStatus httpStatus) {
        this.status = Code.SUCCESS.getCode();
        this.message = message;
        this.request = RequestUtil.getSimpleRequest();
        ResponseUtil.setCurrentResponseHttpStatus(httpStatus.value());
    }

    public UnifyResponseVO(Integer status, T message, HttpStatus httpStatus) {
        this.status = status;
        this.message = message;
        this.request = RequestUtil.getSimpleRequest();
        ResponseUtil.setCurrentResponseHttpStatus(httpStatus.value());
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public T getMessage() {
        return message;
    }

    public void setMessage(T message) {
        this.message = message;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }
}
