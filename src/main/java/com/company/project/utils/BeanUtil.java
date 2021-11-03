package com.company.project.utils;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Bean对象工具类
 *
 * @author qianshuailong
 * @date 2021/7/21
 */
public class BeanUtil {

    private BeanUtil() {
    }

    /**
     * 对象属性拷贝
     * <p>
     * 将源对象的属性拷贝到目标对象
     *
     * @param source 源对象
     * @param target 目标对象
     */
    public static void copyProperties(Object source, Object target) {
        if (source == null) {
            return;
        }
        BeanUtils.copyProperties(source, target);
    }

    /**
     * List对象属性拷贝
     *
     * @param sources 源List对象
     * @param clazz   输出集合类型
     * @return 返回目标集合
     */
    public static <E, T> List<T> copyListProperties(List<E> sources, Class<T> clazz) {
        if (sources == null || sources.isEmpty()) {
            return Collections.emptyList();
        }
        List<T> targets = new ArrayList<>(sources.size());
        if (CollectionUtils.isNotEmpty(sources)) {
            for (E source : sources) {
                T target = BeanUtils.instantiate(clazz);
                BeanUtil.copyProperties(source, target);
                targets.add(target);
            }
        }
        return targets;
    }

}
