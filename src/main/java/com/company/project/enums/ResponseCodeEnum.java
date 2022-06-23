package com.company.project.enums;

import com.company.project.model.BaseCommonError;
import com.qsl.project.base.enums.ServiceErrorCodeEnum;

/**
 * 全局错误码
 * 占用 [0, 99999]
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

    // =========== 服务端异常 =========

    /**
     * 通用的业务方法入参检查错误
     * java.lang.IllegalArgumentException
     */
    SERVER_ILLEGAL_ARGUMENT_ERROR(2001, "业务方法参数检查不通过"),

    // ========== 系统错误段[10000~99999] ==========

    SYSTEM_TIMEOUT(10001, "系统执行超时"),
    SYSTEM_BIZ_TIMEOUT(10002, "系统业务处理超时"),
    SYSTEM_DISASTER_RECOVERY(10003, "系统容灾功能被触发"),
    SYSTEM_LIMIT(10004, "系统限流"),
    SYSTEM_DEGRADED(10005, "系统功能降级"),
    SYSTEM_RESOURCE_ERROR(10006, "系统资源异常"),
    SYSTEM_RESOURCE_EXHAUSTION(10007, "系统资源耗尽"),
    SYSTEM_DISK_EXHAUSTION(10008, "系统磁盘空间耗尽"),
    SYSTEM_MEM_EXHAUSTION(10009, "系统内存耗尽"),
    SYSTEM_HFILE_EXHAUSTION(10010, "文件句柄耗尽"),
    SYSTEM_CONNECTION_EXHAUSTION(10011, "系统连接池耗尽"),
    SYSTEM_THREAD_EXHAUSTION(10012, "系统线程池耗尽"),
    SYSTEM_RESOURCE_READ_ERROR(10013, "系统资源访问异常"),

    THIRD_PARTY_SERVICE_ERROR(20001, "调用第三方服务出错"),
    INTERFACE_NOT_FOUND(20002, "接口不存在"),
    MIDDLEWARE_ERROR(20003, "中间件服务出错"),
    NOX_SERVICE_ERROR(20004, "通知服务出错"),

    DATABASE_TIMEOUT(21001, "数据库服务超时"),
    DATABASE_ERROR(21002, "数据库服务出错"),
    TABLE_NOT_FOUND(21003, "表不存在"),
    COLUMN_NOT_FOUND(21004, "列不存在"),
    DATABASE_DEADLOCK(21005, "数据库死锁"),
    PRIMARY_KEY_CONFLICT(21006, "主键冲突"),

    MSG_SERVICE_ERROR(22001, "消息服务出错"),
    MSG_SEND_ERROR(22002, "消息投递出错"),
    MSG_CONSUME_ERROR(22003, "消息消费出错"),
    MSG_SUBSCRIBE_ERROR(22004, "消息订阅出错"),
    MSG_SEND_TIMEOUT(22005, "消息投递超时"),

    CACHE_SERVICE_ERROR(23001, "缓存服务出错"),
    CACHE_KEY_TOO_LONG(23002, "key长度超过限制"),
    CACHE_VALUE_TOO_LONG(23003, "value长度超过限制"),
    CACHE_CAPACITY_FULL(23004, "存储容量已满"),
    DAT_FORMAT_NOT_SUPPORT(23005, "不支持的数据格式"),
    CACHE_SERVICE_TIMEOUT(23006, "缓存服务超时"),

    APOLLO_ERROR(24001, "配置服务出错"),
    APOLLO_TIMEOUT(24002, "配置服务超时"),

    USER_SESSION_EXPIRED(25001, "用户登录已过期"),
    REQUEST_PARAMS_ERROR(25002, "用户请求参数错误"),

    RPC_SERVICE_ERROR(30001, "RPC服务出错"),
    RPC_SERVICE_NOT_FOUND(30002, "RPC服务未找到"),
    RPC_SERVICE_NOT_REGISTER(30003, "RPC服务未注册"),
    RPC_SERVICE_TIMEOUT(30004, "RPC执行超时"),
    RPC_RETURN_NULL(30005, "RPC返回响应为空"),

    ;

    private final Integer code;

    private final String msg;

    ResponseCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int getErrorCode() {
        return this.code;
    }

    @Override
    public String getErrorMsg() {
        return this.msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
