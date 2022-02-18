package com.company.project.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 休眠工具类
 *
 * @author DanielQSL
 */
public class SleepUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(SleepUtil.class);

    private SleepUtil() {

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
            LOGGER.error("sleep error.", e);
        }
    }

}
