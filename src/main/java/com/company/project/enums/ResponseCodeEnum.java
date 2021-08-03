package com.company.project.enums;

import com.company.project.common.BaseCommonError;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 全局错误码
 * 占用 [0, 999]
 * 一般情况下，使用 HTTP 响应状态码 https://developer.mozilla.org/zh-CN/docs/Web/HTTP/Status
 * 虽然说，HTTP 响应状态码作为业务使用表达能力偏弱，但是使用在系统层面还是非常不错的
 * <p>
 * 业务异常错误码 参见 {@link ServiceErrorCodeEnum}
 *
 * @author DanielQSL
 */
@Getter
@AllArgsConstructor
public enum ResponseCodeEnum implements BaseCommonError {
    /**
     * 响应码
     */
    SUCCESS(0, "成功"),
    ERROR(999, "未知错误"),

    // ========== 服务端错误段 ==========

    INTERNAL_SERVER_ERROR(500, "系统异常"),

    // ========== 客户端错误段 ==========

    BAD_REQUEST(400, "请求参数不正确"),
    UNAUTHORIZED(401, "账号未登录"),
    FORBIDDEN(403, "没有该操作权限"),
    NOT_FOUND(404, "请求未找到"),
    METHOD_NOT_ALLOWED(405, "请求方法不正确"),

    ;

    private final Integer code;
    private final String msg;

    @Override
    public int getErrorCode() {
        return this.code;
    }

    @Override
    public String getErrorMsg() {
        return this.msg;
    }

}
