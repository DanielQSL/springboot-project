package com.company.project.enums;

import com.company.project.model.BaseCommonError;

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
public enum ResponseCodeEnum implements BaseCommonError {

    /**
     * 成功响应码
     */
    SUCCESS(0, "成功"),

    // =========== 系统级别未知异常 =========

    /**
     * 系统未知错误
     */
    ERROR(-1, "系统未知错误"),

    // ========== 服务端错误段 ==========

    INTERNAL_SERVER_ERROR(500, "系统异常"),

    // ========== 客户端错误段 ==========

    BAD_REQUEST(400, "请求参数不正确"),
    UNAUTHORIZED(401, "账号未登录"),
    FORBIDDEN(403, "没有该操作权限"),
    NOT_FOUND(404, "请求未找到"),

    /**
     * 客户端HTTP请求方法错误
     * org.springframework.web.HttpRequestMethodNotSupportedException
     */
    CLIENT_HTTP_METHOD_ERROR(1001, "客户端HTTP请求方法错误"),

    /**
     * 客户端request body参数错误
     * 主要是未能通过Hibernate Validator校验的异常处理
     * <p>
     * org.springframework.web.bind.MethodArgumentNotValidException
     */
    CLIENT_REQUEST_BODY_CHECK_ERROR(1002, "客户端请求体参数校验不通过"),

    /**
     * 客户端@RequestBody请求体JSON格式错误或字段类型错误
     * org.springframework.http.converter.HttpMessageNotReadableException
     * <p>
     * eg:
     * 1、参数类型不对:{"test":"abc"}，本身类型是Long
     * 2、{"test":}  test属性没有给值
     */
    CLIENT_REQUEST_BODY_FORMAT_ERROR(1003, "客户端请求体JSON格式错误或字段类型不匹配"),

    /**
     * 客户端@PathVariable参数错误
     * 一般是类型不匹配，比如本来是Long类型，客户端却给了一个无法转换成Long字符串
     * org.springframework.validation.BindException
     */
    CLIENT_PATH_VARIABLE_ERROR(1004, "客户端URL中的参数类型错误"),

    /**
     * 客户端@RequestParam参数校验不通过
     * 主要是未能通过Hibernate Validator校验的异常处理
     * javax.validation.ConstraintViolationException
     */
    CLIENT_REQUEST_PARAM_CHECK_ERROR(1005, "客户端请求参数校验不通过"),

    /**
     * 客户端@RequestParam参数必填
     * 入参中的@RequestParam注解设置了必填，但是客户端没有给值
     * javax.validation.ConstraintViolationException
     */
    CLIENT_REQUEST_PARAM_REQUIRED_ERROR(1006, "客户端请求缺少必填的参数"),

    ;

    private final Integer code;
    private final String msg;

    ResponseCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public int getErrorCode() {
        return this.code;
    }

    @Override
    public String getErrorMsg() {
        return this.msg;
    }

}
