package com.rfy.interfaces;

/**
 * Created by Rao on 2023/8/28 14:31
 */
public interface BaseResponse {

    /**
     * 返回的信息
     *
     * @return
     */
    String getMessage();

    /**
     * http 状态码
     *
     * @return
     */
    int getHttpCode();

    /**
     * 错误码
     *
     * @return
     */
    int getStatus();

}
