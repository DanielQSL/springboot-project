package com.company.project.utils;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.company.project.enums.ResponseCodeEnum;
import com.company.project.exception.BaseBizException;
import com.company.project.model.BaseCommonError;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;

/**
 * 参数校验工具类
 *
 * @author DanielQSL
 */
@Slf4j
public class ParamCheckUtil {

    public static void checkObjectNonNull(Object o) throws BaseBizException {
        if (Objects.isNull(o)) {
            throw new BaseBizException(ResponseCodeEnum.SERVER_ILLEGAL_ARGUMENT_ERROR);
        }
    }

    public static void checkObjectNonNull(Object o, BaseCommonError baseErrorCodeEnum, Object... arguments) throws BaseBizException {
        if (Objects.isNull(o)) {
            throw new BaseBizException(baseErrorCodeEnum.getErrorCode(), baseErrorCodeEnum.getErrorMsg(), arguments);
        }
    }

    public static void checkObjectNull(Object o, BaseCommonError baseErrorCodeEnum, Object... arguments) throws BaseBizException {
        if (Objects.nonNull(o)) {
            throw new BaseBizException(baseErrorCodeEnum.getErrorCode(), baseErrorCodeEnum.getErrorMsg(), arguments);
        }
    }

    public static void checkStringNonEmpty(String s) throws BaseBizException {
        if (StringUtils.isBlank(s)) {
            throw new BaseBizException(ResponseCodeEnum.SERVER_ILLEGAL_ARGUMENT_ERROR);
        }
    }

    public static void checkStringNonEmpty(String s, BaseCommonError baseErrorCodeEnum, Object... arguments) throws BaseBizException {
        if (StringUtils.isBlank(s)) {
            throw new BaseBizException(baseErrorCodeEnum.getErrorCode(), baseErrorCodeEnum.getErrorMsg(), arguments);
        }
    }

    public static void checkIntAllowableValues(Integer i, Set<Integer> allowableValues, BaseCommonError baseErrorCodeEnum, Object... arguments) throws BaseBizException {
        if (Objects.nonNull(i) && !allowableValues.contains(i)) {
            throw new BaseBizException(baseErrorCodeEnum.getErrorCode(), baseErrorCodeEnum.getErrorMsg(), arguments);
        }
    }

    public static void checkIntMin(Integer i, int min, BaseCommonError baseErrorCodeEnum, Object... arguments) throws BaseBizException {
        if (Objects.isNull(i) || i < min) {
            throw new BaseBizException(baseErrorCodeEnum.getErrorCode(), baseErrorCodeEnum.getErrorMsg(), arguments);
        }
    }

    public static void checkLongMin(Long i, Long min, BaseCommonError baseErrorCodeEnum, Object... arguments) throws BaseBizException {
        if (Objects.isNull(i) || i < min) {
            throw new BaseBizException(baseErrorCodeEnum.getErrorCode(), baseErrorCodeEnum.getErrorMsg(), arguments);
        }
    }

    public static void checkCollectionNonEmpty(Collection<?> collection) throws BaseBizException {
        if (CollectionUtils.isEmpty(collection)) {
            throw new BaseBizException(ResponseCodeEnum.SERVER_ILLEGAL_ARGUMENT_ERROR);
        }
    }

    public static void checkCollectionNonEmpty(Collection<?> collection, BaseCommonError baseErrorCodeEnum, Object... arguments) throws BaseBizException {
        if (CollectionUtils.isEmpty(collection)) {
            throw new BaseBizException(baseErrorCodeEnum.getErrorCode(), baseErrorCodeEnum.getErrorMsg(), arguments);
        }
    }

}
