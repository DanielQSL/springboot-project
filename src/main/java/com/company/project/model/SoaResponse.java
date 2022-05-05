package com.company.project.model;

import java.io.Serializable;

/**
 * RPC通用响应处理对象
 *
 * @author DanielQSL
 */
public class SoaResponse<T, ErrT> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 错误描述
     */
    private String msg;

    /**
     * 错误明细，内部调试错误
     */
    private String detailMsg;

    /**
     * 返回数据
     */
    private T data;

    /**
     * 异常信息
     */
    private ErrT errT;

    public SoaResponse() {
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getDetailMsg() {
        return detailMsg;
    }

    public void setDetailMsg(String detailMsg) {
        this.detailMsg = detailMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public ErrT getErrT() {
        return errT;
    }

    public void setErrT(ErrT errT) {
        this.errT = errT;
    }

    @Override
    public String toString() {
        return "RpcResponse{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", detailMsg='" + detailMsg + '\'' +
                ", data=" + data +
                ", errT=" + errT +
                '}';
    }

}
