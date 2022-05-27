package com.company.project.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
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

}
