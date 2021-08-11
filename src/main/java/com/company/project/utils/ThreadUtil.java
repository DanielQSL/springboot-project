package com.company.project.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 线程工具类
 *
 * @author DanielQSL
 */
public class ThreadUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadUtil.class);

    private ThreadUtil() {

    }

    /**
     * 线程休眠
     *
     * @param millisSeconds 毫秒
     */
    public static void sleep(long millisSeconds) {
        try {
            Thread.sleep(millisSeconds);
        } catch (InterruptedException e) {
            LOGGER.error("thread sleep error", e);
        }
    }

}
