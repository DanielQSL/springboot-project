package com.company.project.utils;

import cn.hutool.core.lang.Assert;
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

    private static volatile Validator validator;

    private ValidatorUtil() {
    }

    /**
     * 获取单例验证器
     *
     * @return 验证器
     */
    private static Validator getValidator() {
        if (validator == null) {
            synchronized (ValidatorUtil.class) {
                if (validator == null) {
                    validator = Validation.buildDefaultValidatorFactory().getValidator();
                }
            }
        }
        return validator;
    }

    /**
     * 参数校验
     */
    public static <T> void validate(T object) {
        Assert.notNull(object);
        Set<ConstraintViolation<T>> validates = getValidator().validate(object);
        if (CollectionUtils.isNotEmpty(validates)) {
            String message = validates.stream()
                    .map(o -> String.format("参数:[%s], 非法值:%s, %s", o.getPropertyPath(), o.getInvalidValue(), o.getMessage()))
                    .collect(Collectors.joining("; "));
            throw new IllegalArgumentException(message);
        }
    }

}
