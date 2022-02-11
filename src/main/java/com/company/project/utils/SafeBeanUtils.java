package com.company.project.utils;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.func.Func1;
import cn.hutool.core.lang.func.VoidFunc1;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Predicate;

/**
 * Bean工具类
 *
 * @author DanielQSL
 */
public class SafeBeanUtils {

    /**
     * 复制已有对象的所有属性
     */
    public static <T1, T2> T2 copyProperties(T1 source, T2 target) {
        if (null == source) {
            return null;
        } else {
            BeanUtils.copyProperties(source, target);
            return target;
        }
    }

    /**
     * 复制对象的所有属性,根据class自动创建实例
     */
    public static <T1, T2> T2 copyProperties(T1 source, Class<T2> clazz) {
        try {
            if (null == source) {
                return null;
            } else {
                T2 target = clazz.newInstance();
                BeanUtils.copyProperties(source, target);
                return target;
            }
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 复制对象的所有属性,根据class自动创建实例,处理null的情况,并对结果执行一段方法
     */
    public static <T1, T2> T2 copyProperties(T1 source, Class<T2> clazz, VoidFunc1<T2> doTargetAfter) {
        T2 target = copyProperties(source, clazz);
        if (target == null) {
            return null;
        }
        try {
            doTargetAfter.call(target);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return target;
    }

    /**
     * 复制列表对象的所有属性,根据class自动创建列表
     */
    public static <T1, T2> List<T2> copyProperties(Collection<T1> sources, Class<T2> clazz) {
        if (sources == null || sources.isEmpty()) {
            return Lists.newArrayList();
        }
        try {
            List<T2> targets = new ArrayList<>(16);
            for (T1 source : sources) {
                T2 target = clazz.newInstance();
                target = copyProperties(source, target);
                targets.add(target);
            }
            return targets;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalArgumentException(e);
        }
    }


    /**
     * 复制列表对象的所有属性,根据predicate做过滤,根据class自动创建列表
     */
    public static <T1, T2> List<T2> copyProperties(Collection<T1> sources, Class<T2> clazz, Predicate<? super T1> filterPredicate) {
        if (sources == null || sources.isEmpty()) {
            return Lists.newArrayList();
        }
        try {
            List<T2> targets = new ArrayList<>(16);
            for (T1 source : sources) {
                if (filterPredicate.test(source)) {
                    T2 target = clazz.newInstance();
                    target = copyProperties(source, target);
                    targets.add(target);
                }
            }
            return targets;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalArgumentException(e);
        }
    }


    /**
     * 复制对象的所有属性,根据class自动创建实例
     */
    public static <T1, T2> T2 copyPropertiesAssertNotNull(T1 source, Class<T2> clazz) {
        try {
            Assert.notNull(source);
            T2 target = clazz.newInstance();
            BeanUtils.copyProperties(source, target);
            return target;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 复制T1对象属性给T2, 只有的T2的属性值为null时才复制
     */
    public static <T1, T2> T2 copyPropertiesIfTargetAbsent(T1 source, T2 target) {
        if (null == source) {
            return null;
        } else {
            BeanUtils.copyProperties(source, target, getNonNullPropertyNames(target));
            return target;
        }
    }

    /**
     * 复制T1对象属性给T2, 只有的T1不为null时才复制
     */
    public static <T1, T2> T2 copyPropertiesIfSourceNonNull(T1 source, T2 target) {
        if (null == source) {
            return null;
        } else {
            BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
            return target;
        }
    }

    /**
     * 通过类变量直接生成map
     */
    public static Map<String, Object> objectToMap(Object obj) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (obj == null) {
            return map;
        }
        Field[] declaredFields = obj.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            try {
                map.put(field.getName(), field.get(obj));
            } catch (IllegalAccessException e) {
                throw new IllegalArgumentException(e);
            }
        }
        return map;
    }

    /**
     * 通过类变量直接生成map
     */
    public static <R> Map<String, R> objectToMap(Object obj, Func1<Object, R> converter) {
        Map<String, R> map = new LinkedHashMap<>();
        if (obj == null) {
            return map;
        }
        Field[] declaredFields = obj.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            try {
                map.put(field.getName(), converter.call(field.get(obj)));
            } catch (IllegalAccessException e) {
                throw new IllegalArgumentException(e);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return map;
    }

    /**
     * 通过getter生成map
     */
    public static Map<String, Object> objectToMapWithGetter(Object obj) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (obj == null) {
            return map;
        }
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass(), Object.class);
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                if (key.compareToIgnoreCase("class") == 0) {
                    continue;
                }
                Method getter = property.getReadMethod();
                Object value = getter != null ? getter.invoke(obj) : null;
                map.put(key, value);
            }
            return map;
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
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

}
