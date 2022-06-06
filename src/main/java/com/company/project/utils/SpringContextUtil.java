package com.company.project.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Spring应用上下文工具类
 *
 * @author DanielQSL
 */
@Slf4j
@Component
public class SpringContextUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext = null;

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        if (Objects.isNull(SpringContextUtil.applicationContext)) {
            SpringContextUtil.applicationContext = context;
        }
    }

    /**
     * 获取应用上下文
     *
     * @return 应用上下文
     */
    public static ApplicationContext getApplicationContext() {
        return SpringContextUtil.applicationContext;
    }

    /**
     * 通过name获取容器中的 Bean
     *
     * @param name 名字
     * @return Bean对象
     */
    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    /**
     * 通过 class 获取 Bean
     *
     * @param clazz Bean的类型
     * @param <T>   类型
     * @return Bean对象
     */
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    /**
     * 通过name + class获取 Bean
     *
     * @param name  名字
     * @param clazz 类型
     * @param <T>   类型
     * @return Bean对象
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }

    /**
     * 通过name判断容器中的是否存在该 Bean
     *
     * @param name 名字
     * @return 是否存在
     */
    public static boolean containsBean(String name) {
        return getApplicationContext().containsBean(name);
    }

    /**
     * 根据 bean 的名称判断该 bean 是否为单例
     *
     * @param name bean的名称
     * @return 是否为单例
     */
    public static boolean isSingleton(String name) {
        return applicationContext.isSingleton(name);
    }

    /**
     * 获取给定名称的 bean 的类型
     *
     * @param name bean的名称
     * @return bean的类型
     */
    public static Class<?> getType(String name) {
        return applicationContext.getType(name);
    }

}
