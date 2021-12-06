package com.company.project.enums;

import com.company.project.model.BaseCommonError;

/**
 * 业务异常错误码
 * <p>
 * 一共 9 位，分成三段
 *
 * 第一段，3 位，系统类型（业务域）
 *      101 - 用户系统
 *      102 - 商品系统
 * 第二段，2 位，模块（业务划分）
 *      不限制规则。
 *      一般建议，每个系统里面，可能有多个模块，可以再去做分段。以用户系统为例子：
 *          01 - OAuth2 模块
 *          02 - User 模块
 * 第三段，4 位，错误码
 *       不限制规则。
 *       一般建议，每个模块从0001开始自增。
 *
 * @author DanielQSL
 */
public enum ServiceErrorCodeEnum implements BaseCommonError {

    // ========== user 错误码区间 [101_00_0000 ~ 102_00_0000) ==========
    USER_QUERY_EMPTY(101_02_0001, "未查询到用户"),

    ;

    private final Integer code;
    private final String msg;

    ServiceErrorCodeEnum(Integer code, String msg) {
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
