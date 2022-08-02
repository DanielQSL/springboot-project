package com.company.project.utils;

import com.company.project.enums.ResponseCodeEnum;
import com.company.project.exception.BaseBizException;
import com.company.project.model.BaseCommonError;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Objects;

/**
 * 参数校验工具类
 *
 * @author DanielQSL
 */
public class ParamCheckUtil {

    public static void checkObjectNotNull(Object o) throws BaseBizException {
        if (Objects.isNull(o)) {
            throw new BaseBizException(ResponseCodeEnum.SERVER_ILLEGAL_ARGUMENT_ERROR);
        }
    }

    public static void checkObjectNotNull(Object o, String errorMsg) throws BaseBizException {
        if (Objects.isNull(o)) {
            throw new BaseBizException(ResponseCodeEnum.SERVER_ILLEGAL_ARGUMENT_ERROR, errorMsg);
        }
    }

    public static void checkObjectNotNull(Object o, BaseCommonError baseErrorCodeEnum, Object... arguments) throws BaseBizException {
        if (Objects.isNull(o)) {
            throw new BaseBizException(baseErrorCodeEnum.getErrorCode(), baseErrorCodeEnum.getErrorMsg(), arguments);
        }
    }

    public static void checkStringNotBlank(String str) throws BaseBizException {
        if (StringUtils.isBlank(str)) {
            throw new BaseBizException(ResponseCodeEnum.SERVER_ILLEGAL_ARGUMENT_ERROR);
        }
    }

    public static void checkStringNotBlank(String str, String errorMsg) throws BaseBizException {
        if (StringUtils.isBlank(str)) {
            throw new BaseBizException(ResponseCodeEnum.SERVER_ILLEGAL_ARGUMENT_ERROR, errorMsg);
        }
    }

    public static void checkStringNotBlank(String str, BaseCommonError baseErrorCodeEnum, Object... arguments) throws BaseBizException {
        if (StringUtils.isBlank(str)) {
            throw new BaseBizException(baseErrorCodeEnum.getErrorCode(), baseErrorCodeEnum.getErrorMsg(), arguments);
        }
    }

    public static void checkCollectionNotEmpty(Collection<?> collection) throws BaseBizException {
        if (CollectionUtils.isEmpty(collection)) {
            throw new BaseBizException(ResponseCodeEnum.SERVER_ILLEGAL_ARGUMENT_ERROR);
        }
    }

    public static void checkCollectionNotEmpty(Collection<?> collection, String errorMsg) throws BaseBizException {
        if (CollectionUtils.isEmpty(collection)) {
            throw new BaseBizException(ResponseCodeEnum.SERVER_ILLEGAL_ARGUMENT_ERROR, errorMsg);
        }
    }

    public static void checkCollectionNotEmpty(Collection<?> collection, BaseCommonError baseErrorCodeEnum, Object... arguments) throws BaseBizException {
        if (CollectionUtils.isEmpty(collection)) {
            throw new BaseBizException(baseErrorCodeEnum.getErrorCode(), baseErrorCodeEnum.getErrorMsg(), arguments);
        }
    }

}
