package com.company.project.enums;

import com.company.project.common.BaseCommonError;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 业务异常错误码
 * <p>
 * 一共 9 位，分成三段
 *
 * 第一段，3 位，系统类型（业务域）
 *      101 - 用户系统
 *      102 - 商品系统
 * 第二段，3 位，模块（业务划分）
 *      不限制规则。
 *      一般建议，每个系统里面，可能有多个模块，可以再去做分段。以用户系统为例子：
 *          001 - OAuth2 模块
 *          002 - User 模块
 * 第三段，3 位，错误码
 *       不限制规则。
 *       一般建议，每个模块自增。
 *
 * @author DanielQSL
 */
@Getter
@AllArgsConstructor
public enum ServiceErrorCodeEnum implements BaseCommonError {

    // ========== user 错误码区间 [101_000_000 ~ 102_000_000) ==========
    USER_QUERY_EMPTY(101_002_001, "未查询到用户"),

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
