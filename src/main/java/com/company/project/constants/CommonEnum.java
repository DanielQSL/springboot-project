package com.company.project.constants;

import com.company.project.common.BaseCommonError;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 通用码枚举类
 *
 * @author qianshuailong
 */
@Getter
@AllArgsConstructor
public enum CommonEnum implements BaseCommonError {
    /**
     * 通用码
     */
    PARAM_ILLEGAL(10011001, "参数不合法"),
    ;

    private final Integer code;
    private String desc;

    @Override
    public int getErrorCode() {
        return this.code;
    }

    @Override
    public String getErrorMsg() {
        return this.desc;
    }

    @Override
    public BaseCommonError setErrorMsg(String errorMsg) {
        this.desc = errorMsg;
        return this;
    }
}
