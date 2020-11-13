package com.company.project.config;

import com.company.project.config.properties.ThreadPoolProperties;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池配置
 *
 * @author DanielQSL
 * @date 2020/4/24
 */
@Slf4j
@EnableConfigurationProperties(ThreadPoolProperties.class)
@Configuration
public class ThreadPoolConfig {

    @Bean("threadPoolExecutor")
    public ExecutorService threadPoolExecutor(ThreadPoolProperties threadPoolProperties) {
        return new ThreadPoolExecutor(
                threadPoolProperties.getCorePoolSize(), threadPoolProperties.getMaxPoolSize(),
                threadPoolProperties.getKeepAliveSeconds(), TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(threadPoolProperties.getQueueCapacity()),
                new ThreadFactoryBuilder()
                        .setNameFormat("test-pool-%d")
                        .setUncaughtExceptionHandler((thread, throwable) -> log.error("test-pool got exception [{}]", thread, throwable))
                        .build(),
                new ThreadPoolExecutor.AbortPolicy());
    }

//    /**
//     * 第二种创建线程池方法：Spring中ThreadPoolTaskExecutor
//     *
//     * @return
//     */
//    @Bean("taskExecutor")
//    public Executor taskExecutor() {
//        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//        executor.setCorePoolSize(threadPoolProperties.getCorePoolSize());
//        executor.setMaxPoolSize(threadPoolProperties.getMaxPoolSize());
//        executor.setQueueCapacity(threadPoolProperties.getQueueCapacity());
//        executor.setKeepAliveSeconds((int) threadPoolProperties.getKeepAliveSeconds());
//        executor.setThreadNamePrefix(threadPoolProperties.getPrefix());
//        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
//        executor.initialize();
//        return executor;
//    }

}
