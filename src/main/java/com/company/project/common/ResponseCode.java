package com.company.project.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 相应业务码
 * 业务码设计：前三位代表项目，中间两位代表开发小组，后三位错误码
 *
 * @author DanielQSL
 */
@Getter
@AllArgsConstructor
public enum ResponseCode {
    /**
     * 响应码
     */
    SUCCESS(0, "SUCCESS"),
    ERROR(-1, "ERROR"),
    PARAM_ILLEGAL(10011001, "参数不合法"),
    ;

    private final Integer code;
    private final String desc;

}
