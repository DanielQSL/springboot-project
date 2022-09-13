package com.company.project.utils;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.func.VoidFunc1;
import com.company.project.model.PageResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.*;

/**
 * Bean拷贝工具类
 *
 * @author DanielQSL
 */
public class BeanCopyUtil {

    private BeanCopyUtil() {
    }

    /**
     * 将给定源 bean 的属性值复制到目标 bean
     *
     * @param source 源 bean
     * @param target 目标 bean
     * @return 目标对象
     */
    public static <S, T> T copyProperties(S source, T target) {
        if (null == source) {
            return null;
        }
        BeanUtils.copyProperties(source, target);
        return target;
    }

    /**
     * 将给定源 bean 的属性值复制到目标 bean
     * 根据class自动创建实例
     *
     * @param source 源 bean
     * @param clazz  目标 bean 类
     * @return 目标对象
     */
    public static <S, T> T copyProperties(S source, Class<T> clazz) {
        if (null == source) {
            return null;
        }
        try {
            T target = clazz.newInstance();
            BeanUtils.copyProperties(source, target);
            return target;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 可回调的对象拷贝
     *
     * @param source        源 bean
     * @param clazz         目标 bean 类
     * @param doTargetAfter 回调方法
     * @return 目标对象
     */
    public static <S, T> T copyProperties(S source, Class<T> clazz, VoidFunc1<T> doTargetAfter) {
        T target = copyProperties(source, clazz);
        if (target == null) {
            return null;
        }
        if (doTargetAfter != null) {
            doTargetAfter.callWithRuntimeException(target);
        }
        return target;
    }

    /**
     * 将给定源集合 bean 的属性值复制到目标集合 bean
     * 根据class自动创建实例
     *
     * @param sources 源集合 bean
     * @param clazz   目标 bean 类
     * @return 目标对象集合
     */
    public static <S, T> List<T> copyProperties(Collection<S> sources, Class<T> clazz) {
        if (CollectionUtil.isEmpty(sources)) {
            return Collections.emptyList();
        }
        List<T> targets = new ArrayList<>(sources.size());
        try {
            for (S source : sources) {
                T target = clazz.newInstance();
                target = copyProperties(source, target);
                targets.add(target);
            }
            return targets;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 复制分页对象的所有属性
     * 根据class自动创建实例
     *
     * @param sources 源分页对象
     * @param clazz   目标 bean 类
     * @return 目标分页对象
     */
    public static <S, T> PageResult<T> copyProperties(PageResult<S> sources, Class<T> clazz) {
        if (sources == null) {
            return null;
        }

        PageResult<T> targetPage = new PageResult<>();
        targetPage.setPages(sources.getPages());
        targetPage.setTotal(sources.getTotal());
        targetPage.setPageSize(sources.getPageSize());
        targetPage.setPageNum(sources.getPageNum());
        if (CollectionUtil.isEmpty(sources.getList())) {
            targetPage.setList(Collections.emptyList());
            return targetPage;
        }

        List<T> targets = new ArrayList<>(16);
        try {
            for (S source : sources.getList()) {
                T target = clazz.newInstance();
                target = copyProperties(source, target);
                targets.add(target);
            }
            targetPage.setList(targets);
            return targetPage;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 复制S对象属性给T, 只有的T的属性值为null时才复制
     */
    public static <S, T> T copyPropertiesIfTargetAbsent(S source, T target) {
        if (null == source) {
            return null;
        }
        BeanUtils.copyProperties(source, target, getNonNullPropertyNames(target));
        return target;

    }

    /**
     * 获取属性值不为null的属性名
     */
    public static String[] getNonNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();
        Set<String> emptyNames = new LinkedHashSet<>();
        for (PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue != null) {
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    /**
     * 复制S对象属性给T, 只有的S不为null时才复制
     */
    public static <S, T> T copyPropertiesIfSourceNonNull(S source, T target) {
        if (null == source) {
            return null;
        }
        BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
        return target;
    }

    /**
     * 获取属性值为null的属性名
     */
    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();
        Set<String> emptyNames = new LinkedHashSet<>();
        for (PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

}
