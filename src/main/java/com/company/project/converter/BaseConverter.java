package com.company.project.converter;

import org.mapstruct.InheritConfiguration;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.MapperConfig;

import java.util.List;
import java.util.stream.Stream;

/**
 * 基础转化类
 *
 * @author qianshuailong
 * @date 2020-10-19
 */
@MapperConfig
public interface BaseConverter<SOURCE, TARGET> {

    /**
     * 映射同名属性
     *
     * @param var1 源对象
     * @return 目标对象
     */
    TARGET sourceToTarget(SOURCE var1);

    /**
     * 反向，映射同名属性
     *
     * @param var1 目标对象
     * @return 源对象
     */
    @InheritInverseConfiguration(name = "sourceToTarget")
    SOURCE targetToSource(TARGET var1);

    /**
     * 映射同名属性，集合形式
     *
     * @param var1 源对象集合
     * @return 目标对象集合
     */
    @InheritConfiguration(name = "sourceToTarget")
    List<TARGET> sourceToTarget(List<SOURCE> var1);

    /**
     * 反向，映射同名属性，集合形式
     *
     * @param var1 目标对象集合
     * @return 源对象集合
     */
    @InheritConfiguration(name = "targetToSource")
    List<SOURCE> targetToSource(List<TARGET> var1);

    /**
     * 映射同名属性，集合流形式
     *
     * @param stream 源对象集合流
     * @return 目标对象集合流
     */
    List<TARGET> sourceToTarget(Stream<SOURCE> stream);

    /**
     * 反向，映射同名属性，集合流形式
     *
     * @param stream 目标对象集合流
     * @return 源对象集合流
     */
    List<SOURCE> targetToSource(Stream<TARGET> stream);
}
