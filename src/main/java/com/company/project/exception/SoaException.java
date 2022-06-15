package com.company.project.exception;

/**
 * RPC异常
 *
 * @author DanielQSL
 */
public class SoaException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * 默认错误码
     */
    private static final int DEFAULT_ERROR_CODE = -1;

    private Integer errCode;

    private String errMsg;

    public SoaException(Integer code, String msg) {
        super(msg);
        this.errCode = code;
        this.errMsg = msg;
    }

    public SoaException(String errorMsg) {
        this(DEFAULT_ERROR_CODE, errorMsg);
    }

    public Integer getErrCode() {
        return errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrCode(Integer errCode) {
        this.errCode = errCode;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

}
