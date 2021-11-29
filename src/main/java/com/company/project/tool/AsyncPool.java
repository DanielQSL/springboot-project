package com.company.project.tool;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 异步线程池
 *
 * @author DanielQSL
 */
public class AsyncPool {

    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncPool.class);

    private static ExecutorService threadPoolExecutor = new ThreadPoolExecutor(
            10, 20,
            60, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(10_000),
            new ThreadFactoryBuilder()
                    .setNameFormat("async-pool-%d")
                    .setUncaughtExceptionHandler((thread, throwable) -> LOGGER.error("AsyncPool got exception [{}]", thread, throwable))
                    .build(),
            new ThreadPoolExecutor.AbortPolicy());

    public static void asyncDo(Runnable runnable) {
        threadPoolExecutor.submit(runnable);
    }

    public static void shutDown() {
        threadPoolExecutor.shutdown();
    }

}
