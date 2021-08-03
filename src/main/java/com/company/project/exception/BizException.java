package com.company.project.exception;

import com.company.project.common.BaseCommonError;

/**
 * 自定义业务异常
 *
 * @author DanielQSL
 */
public class BizException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private Integer code;

    public BizException() {
        super();
    }

    public BizException(String message) {
        super(message);
    }

    public BizException(Integer code, String message) {
        super(message);
        this.setCode(code);
    }

    public BizException(BaseCommonError commonError) {
        this(commonError.getErrorCode(), commonError.getErrorMsg());
    }

    public BizException(BaseCommonError commonError, String errorMsg) {
        this(commonError.getErrorCode(), errorMsg);
    }

    public int getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

}