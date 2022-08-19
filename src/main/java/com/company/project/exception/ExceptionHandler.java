package com.company.project.exception;

import lombok.extern.slf4j.Slf4j;

/**
 * 线程自定义异常处理
 * 使用方式: Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler());
 *
 * @author DanielQSL
 */
@Slf4j
public class ExceptionHandler implements Thread.UncaughtExceptionHandler {

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        log.error("thread[{}] got exception.", t, e);
    }

}
