package com.company.project.utils;

import com.qsl.project.base.enums.ResponseCodeEnum;
import com.qsl.project.base.exception.BusinessException;
import com.qsl.project.base.exception.SoaException;
import com.qsl.project.base.model.BaseCommonError;
import com.qsl.project.base.model.SoaResponse;

/**
 * RPC响应工具类
 *
 * @author DanielQSL
 */
public class SoaUtil {

    private final static Integer SUCCESS_CODE = ResponseCodeEnum.SUCCESS.getErrorCode();
    private final static String SUCCESS_MESSAGE = ResponseCodeEnum.SUCCESS.getErrorMsg();

    private final static Integer ERROR_CODE = ResponseCodeEnum.ERROR.getErrorCode();
    private final static String ERROR_MESSAGE = ResponseCodeEnum.ERROR.getErrorMsg();

    public static <T, E> SoaResponse<T, E> success(String returnMsg, T data) {
        SoaResponse<T, E> soaResponse = new SoaResponse<>();
        soaResponse.setCode(SUCCESS_CODE);
        soaResponse.setMsg(returnMsg);
        soaResponse.setData(data);
        return soaResponse;
    }

    public static <T, E> SoaResponse<T, E> success(T data) {
        return success(SUCCESS_MESSAGE, data);
    }

    public static <T, E> SoaResponse<T, E> error(Integer returnCode, String returnMsg, T data) {
        SoaResponse<T, E> soaResponse = new SoaResponse<>();
        soaResponse.setCode(returnCode);
        soaResponse.setMsg(returnMsg);
        soaResponse.setData(data);
        return soaResponse;
    }

    public static <T, E> SoaResponse<T, E> error(Integer returnCode, String returnMsg) {
        return error(returnCode, returnMsg, null);
    }

    public static <T, E> SoaResponse<T, E> error(BaseCommonError errorEnum, T data) {
        return error(errorEnum.getErrorCode(), errorEnum.getErrorMsg(), data);
    }

    public static <T, E> SoaResponse<T, E> error(BaseCommonError errorEnum) {
        return error(errorEnum, null);
    }

    public static <T, E> SoaResponse<T, E> error(SoaException e, T data) {
        return error(e.getErrCode(), e.getErrMsg(), data);
    }

    public static <T, E> SoaResponse<T, E> error(SoaException e) {
        return error(e, null);
    }

    public static <T, E> SoaResponse<T, E> error(BusinessException e, T data) {
        return error(e.getCode(), e.getMessage(), data);
    }

    public static <T, E> SoaResponse<T, E> error(BusinessException e) {
        return error(e, null);
    }

    public static <T> boolean isSuccess(SoaResponse<T, ?> soaResponse) {
        return soaResponse != null && SUCCESS_CODE.equals(soaResponse.getCode());
    }

    public static <T> boolean isSuccess(SoaResponse<T, ?> soaResponse, Integer successCode) {
        return soaResponse != null && soaResponse.getCode().equals(successCode);
    }

    public static <T> boolean isSuccessNotNull(SoaResponse<T, ?> soaResponse) {
        return isSuccess(soaResponse) && soaResponse.getData() != null;
    }

    public static <T> boolean isSuccessNotNull(SoaResponse<T, ?> soaResponse, Integer successCode) {
        return isSuccess(soaResponse, successCode) && soaResponse.getData() != null;
    }

    public static <T> T unpack(SoaResponse<T, ?> soaResponse) {
        if (null == soaResponse) {
            throw new SoaException(ResponseCodeEnum.RPC_RETURN_NULL.getErrorCode(), ResponseCodeEnum.RPC_RETURN_NULL.getErrorMsg());
        }
        if (!SUCCESS_CODE.equals(soaResponse.getCode())) {
            throw new SoaException(soaResponse.getCode(), soaResponse.getMsg());
        }
        return soaResponse.getData();
    }

    public static <T> T unpackNotNull(SoaResponse<T, ?> soaResponse) {
        if (null == soaResponse) {
            throw new SoaException(ResponseCodeEnum.RPC_RETURN_NULL.getErrorCode(), ResponseCodeEnum.RPC_RETURN_NULL.getErrorMsg());
        }
        if (!SUCCESS_CODE.equals(soaResponse.getCode())) {
            throw new SoaException(soaResponse.getCode(), soaResponse.getMsg());
        }
        if (null == soaResponse.getData()) {
            throw new SoaException(ResponseCodeEnum.RPC_RETURN_DATA_NULL.getErrorCode(), ResponseCodeEnum.RPC_RETURN_DATA_NULL.getErrorMsg());
        }
        return soaResponse.getData();
    }

    public static <T, E> SoaResponse<T, E> buildErrorResponse(Throwable e) {
        if (e instanceof SoaException) {
            return error((SoaException) e);
        } else if (e instanceof BusinessException) {
            return error((BusinessException) e);
        } else {
            return error(ERROR_CODE, ERROR_MESSAGE, null);
        }
    }

}
