package com.company.project.common;

import com.company.project.enums.ResponseCodeEnum;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * 通用返回
 *
 * @author DanielQSL
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonResponse<T> implements Serializable {

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
    public CommonResponse() {
    }

    private CommonResponse(int code) {
        this.code = code;
    }

    private CommonResponse(int code, T data) {
        this.code = code;
        this.data = data;
    }

    private CommonResponse(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    private CommonResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static <T> CommonResponse<T> success() {
        return new CommonResponse<>(ResponseCodeEnum.SUCCESS.getCode(), ResponseCodeEnum.SUCCESS.getMsg());
    }

    public static <T> CommonResponse<T> success(T data) {
        return new CommonResponse<>(ResponseCodeEnum.SUCCESS.getCode(), ResponseCodeEnum.SUCCESS.getMsg(), data);
    }

    public static <T> CommonResponse<T> successMsg(String msg) {
        return new CommonResponse<>(ResponseCodeEnum.SUCCESS.getCode(), msg);
    }

    public static <T> CommonResponse<T> success(String msg, T data) {
        return new CommonResponse<>(ResponseCodeEnum.SUCCESS.getCode(), msg, data);
    }

    public static <T> CommonResponse<T> fail() {
        return new CommonResponse<>(ResponseCodeEnum.ERROR.getCode(), ResponseCodeEnum.ERROR.getMsg());
    }

    public static <T> CommonResponse<T> fail(String errorMessage) {
        return new CommonResponse<>(ResponseCodeEnum.ERROR.getCode(), errorMessage);
    }

    public static <T> CommonResponse<T> fail(BaseCommonError commonError) {
        return new CommonResponse<>(commonError.getErrorCode(), commonError.getErrorMsg());
    }

    public static <T> CommonResponse<T> fail(int errorCode, String errorMessage) {
        return new CommonResponse<>(errorCode, errorMessage);
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

}
