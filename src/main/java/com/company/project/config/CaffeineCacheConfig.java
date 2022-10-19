package com.company.project.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.google.common.cache.CacheBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * Caffeine本地缓存配置
 *
 * @author DanielQSL
 */
@Configuration
public class CaffeineCacheConfig {

    @Bean("configCache")
    public Cache<String, Object> configCache() {
        return Caffeine.newBuilder()
                // 最后一次写入后经过固定时间过期
                .expireAfterWrite(60, TimeUnit.SECONDS)
                // 最后一次写入或访问后经过固定时间过期
                .expireAfterAccess(60, TimeUnit.SECONDS)
                // 初始的缓存空间大小
                .initialCapacity(200)
                // 缓存的最大条数
                .maximumSize(2000)
                .build();
    }

    @Bean("guavaCache")
    public com.google.common.cache.Cache<String, String> guavaCache() {
        return CacheBuilder.newBuilder()
                // 设置缓存的最大容量
                .maximumSize(1000)
                // 最后一次写入后经过固定时间过期
                .expireAfterWrite(60, TimeUnit.SECONDS)
                // 最后一次写入或访问后经过固定时间过期
                .expireAfterAccess(60, TimeUnit.SECONDS)
                // 设置并发级别，默认为4
                .concurrencyLevel(10)
                // 开启统计功能
                .recordStats()
                .build();
    }

}
