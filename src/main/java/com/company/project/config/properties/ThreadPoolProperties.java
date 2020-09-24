package com.company.project.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 线程池配置参数
 *
 * @author DanielQSL
 * @date 2020/4/24
 */
@Data
@ConfigurationProperties(prefix = "thread.pool")
public class ThreadPoolProperties {

    /**
     * 核心线程数
     */
    private int corePoolSize;

    /**
     * 最大线程数
     */
    private int maxPoolSize;

    /**
     * 非核心线程数存活时间
     */
    private long keepAliveSeconds;

    /**
     * 队列大小
     */
    private int queueCapacity;

    /**
     * 线程池命名前缀
     */
    private String prefix;

}
