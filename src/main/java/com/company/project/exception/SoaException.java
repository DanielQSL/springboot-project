package com.company.project.exception;

/**
 * RPC异常
 *
 * @author DanielQSL
 */
public class SoaException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final Integer errCode;

    private final String errMsg;

    public SoaException(Integer code, String msg) {
        super(msg);
        this.errCode = code;
        this.errMsg = msg;
    }

    public Integer getErrCode() {
        return errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

}
