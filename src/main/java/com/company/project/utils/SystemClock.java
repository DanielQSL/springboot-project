package com.company.project.utils;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 用于解决高并发下System.currentTimeMillis卡顿
 *
 * @author DanielQSL
 */
public class SystemClock {

    private final int period;

    private final AtomicLong now;

    private SystemClock(int period) {
        // 定时任务设置1毫秒
        this.period = period;
        this.now = new AtomicLong(System.currentTimeMillis());
        scheduleClockUpdating();
    }

    private static class InstanceHolder {
        private static final SystemClock INSTANCE = new SystemClock(1);
    }

    private static SystemClock instance() {
        return InstanceHolder.INSTANCE;
    }

    private void scheduleClockUpdating() {
        // 周期执行线程池
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor(runnable -> {
            Thread thread = new Thread(runnable, "System Clock");
            // 守护线程
            thread.setDaemon(true);
            return thread;
        });
        // 任务，开始时间，间隔时间=周期执行，时间单位
        scheduler.scheduleAtFixedRate(() -> now.set(System.currentTimeMillis()), period, period, TimeUnit.MILLISECONDS);
    }

    private long currentTimeMillis() {
        return now.get();
    }

    /**
     * 用来替换原来的System.currentTimeMillis()
     */
    public static long now() {
        return instance().currentTimeMillis();
    }

}