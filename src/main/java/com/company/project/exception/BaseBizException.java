package com.company.project.exception;

import cn.hutool.core.util.StrUtil;
import com.company.project.model.BaseCommonError;

/**
 * 基础自定义业务异常
 *
 * @author DanielQSL
 */
public class BaseBizException extends RuntimeException {

    /**
     * 默认错误码
     */
    private static final int DEFAULT_ERROR_CODE = -1;

    private Integer errorCode;

    private String errorMsg;

    public BaseBizException(String errorMsg) {
        super(errorMsg);
        this.errorCode = DEFAULT_ERROR_CODE;
        this.errorMsg = errorMsg;
    }

    public BaseBizException(Integer errorCode, String errorMsg) {
        super(errorMsg);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public BaseBizException(BaseCommonError baseCommonError) {
        super(baseCommonError.getErrorMsg());
        this.errorCode = baseCommonError.getErrorCode();
        this.errorMsg = baseCommonError.getErrorMsg();
    }

    public BaseBizException(Integer errorCode, String errorMsg, Object... arguments) {
        super(StrUtil.format(errorMsg, arguments));
        this.errorCode = errorCode;
        this.errorMsg = StrUtil.format(errorMsg, arguments);
    }

    public BaseBizException(BaseCommonError baseCommonError, Object... arguments) {
        super(StrUtil.format(baseCommonError.getErrorMsg(), arguments));
        this.errorCode = baseCommonError.getErrorCode();
        this.errorMsg = StrUtil.format(baseCommonError.getErrorMsg(), arguments);
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

}