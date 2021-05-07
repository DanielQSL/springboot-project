package com.company.project.exception;

import com.company.project.common.BaseCommonError;

/**
 * 自定义业务异常
 *
 * @author DanielQSL
 */
public class BusinessException extends RuntimeException implements BaseCommonError {

    private static final long serialVersionUID = 4564124491192825748L;

    private BaseCommonError commonError;

    public BusinessException(BaseCommonError commonError) {
        super();
        this.commonError = commonError;
    }

    public BusinessException(BaseCommonError commonError, String errorMsg) {
        super();
        this.commonError = commonError;
        this.commonError.setErrorMsg(errorMsg);
    }

    @Override
    public int getErrorCode() {
        return this.commonError.getErrorCode();
    }

    @Override
    public String getErrorMsg() {
        return this.commonError.getErrorMsg();
    }

    @Override
    public BaseCommonError setErrorMsg(String errorMsg) {
        this.commonError.setErrorMsg(errorMsg);
        return this;
    }
}