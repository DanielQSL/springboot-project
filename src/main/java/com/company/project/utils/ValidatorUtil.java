package com.company.project.utils;

import org.apache.commons.collections4.CollectionUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 参数校验工具类
 *
 * @author DanielQSL
 */
public class ValidatorUtil {

    private ValidatorUtil() {
    }

    private static class ValidatorHolder {
        private static final Validator INSTANCE = Validation.buildDefaultValidatorFactory().getValidator();
    }

    /**
     * 获取单例验证器
     *
     * @return 验证器
     */
    private static Validator getValidator() {
        return ValidatorHolder.INSTANCE;
    }

    /**
     * 参数校验
     */
    public static <T> void validate(T object) {
        if (null == object) {
            throw new IllegalArgumentException("this argument must not be null");
        }
        Set<ConstraintViolation<T>> validates = getValidator().validate(object);
        if (CollectionUtils.isNotEmpty(validates)) {
            String message = validates.stream()
                    .map(o -> String.format("参数:[%s], 非法值:%s, %s", o.getPropertyPath(), o.getInvalidValue(), o.getMessage()))
                    .collect(Collectors.joining("; "));
            throw new IllegalArgumentException(message);
        }
    }

}
