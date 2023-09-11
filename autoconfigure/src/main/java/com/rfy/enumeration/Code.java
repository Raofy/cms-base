package com.rfy.enumeration;

/**
 *
 * Created by Rao on 2023/8/28 14:31
 */
public enum Code {

    SUCCESS(200, "OK", "成功"),
    CREATED(201, "Created", "创建成功"),
    UPDATED(202, "Updated", "更新成功"),
    DELETE(203, "Deleted", "删除成功"),
    NOT_FOUND(404, "Not Found", "资源不存在"),
    INTERNAL_SERVER_ERROR(9999, "internal server error", "服务器异常"),
    UN_AUTHORIZATION(10000, "Authorization Failed", "认证失败"),
    PARAMETER_ERROR(10030, "Parameter Error", "参数错误"),
    TOKEN_INVALID(10040, "Token Invalid", "令牌不合法"),
    TOKEN_EXPIRED(10050, "Token Expired", "令牌过期"),
    METHOD_NOT_ALLOWED(10080, "Method not allowed", "请求方法不允许"),
    REQUEST_LIMIT(10140, "Too Many Requests", "请求过于频繁，请稍后再试"),
    FAILED(10200, "Failed", "失败");


    private int code;
    private String description;
    private String zhDescription;

    Code() {
    }

    Code(int code, String description, String zhDescription) {
        this.code = code;
        this.description = description;
        this.zhDescription = zhDescription;
    }


    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public String getZhDescription() {
        return zhDescription;
    }
}
