package com.company.project.model;

import com.company.project.enums.ResponseCodeEnum;
import com.company.project.exception.BusinessException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * 通用返回 V2详细版本
 *
 * @author DanielQSL
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonResponseV2<T> implements Serializable {

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
     * 返回数据
     */
    private T data;

    /**
     * 空参构造函数
     * 注：Jackson反序列化需要无参构造函数
     */
    public CommonResponseV2() {

    }

    private CommonResponseV2(int code) {
        this.code = code;
    }

    private CommonResponseV2(int code, T data) {
        this.code = code;
        this.data = data;
    }

    private CommonResponseV2(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private CommonResponseV2(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    @JsonIgnore
    public boolean isSuccess() {
        return ResponseCodeEnum.SUCCESS.getCode().equals(this.code);
    }

    public static <T> CommonResponseV2<T> success() {
        return new CommonResponseV2<>(ResponseCodeEnum.SUCCESS.getCode(), ResponseCodeEnum.SUCCESS.getMsg());
    }

    public static <T> CommonResponseV2<T> success(T data) {
        return new CommonResponseV2<>(ResponseCodeEnum.SUCCESS.getCode(), ResponseCodeEnum.SUCCESS.getMsg(), data);
    }

    public static <T> CommonResponseV2<T> successMsg(String message) {
        return new CommonResponseV2<>(ResponseCodeEnum.SUCCESS.getCode(), message);
    }

    public static <T> CommonResponseV2<T> success(String message, T data) {
        return new CommonResponseV2<>(ResponseCodeEnum.SUCCESS.getCode(), message, data);
    }

    public static <T> CommonResponseV2<T> fail() {
        return new CommonResponseV2<>(ResponseCodeEnum.ERROR.getCode(), ResponseCodeEnum.ERROR.getMsg());
    }

    public static <T> CommonResponseV2<T> fail(String errorMessage) {
        return new CommonResponseV2<>(ResponseCodeEnum.ERROR.getCode(), errorMessage);
    }

    public static <T> CommonResponseV2<T> fail(int errorCode, String errorMessage) {
        return new CommonResponseV2<>(errorCode, errorMessage);
    }

    public static <T> CommonResponseV2<T> fail(BaseCommonError commonError) {
        return new CommonResponseV2<>(commonError.getErrorCode(), commonError.getErrorMsg());
    }

    public static <T> CommonResponseV2<T> fail(BaseCommonError commonError, String errorMessage) {
        return new CommonResponseV2<>(commonError.getErrorCode(), errorMessage);
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "CommonResponse{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    // ========= 和 Exception 异常体系集成 =========

    /**
     * 判断是否有异常。如果有，则抛出 {@link BusinessException} 异常
     */
    public void checkError() throws Exception {
        if (isSuccess()) {
            return;
        }
        throw new BusinessException(code, msg);
    }

}
