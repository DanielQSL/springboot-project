package com.company.project.constants;

/**
 * Redis常量
 *
 * @author DanielQSL
 */
public final class RedisConstants {

    /**
     * 缓存过期时间
     * 7天
     */
    public static final long LONG_EXPIRED_SEVEN_DAYS = 7 * 24 * 60 * 60 * 1000;

    /**
     * 缓存过期时间
     * 1天
     */
    public static final long LONG_EXPIRED_ONE_DAY = 24 * 60 * 60 * 1000;

    /**
     * 缓存过期时间
     * 1天
     */
    public static final int INT_EXPIRED_ONE_DAY = 24 * 60 * 60;

    /**
     * 缓存过期时间
     * 1小时
     */
    public static final int INT_EXPIRED_ONE_HOUR = 60 * 60;

    /**
     * 缓存过期时间
     * 1分钟
     */
    public static final int INT_EXPIRED_ONE_MINUTE = 60;

    /**
     * 5分钟
     */
    public static final long LONG_FIVE_MINUTES = 5 * 60 * 1000L;

    /**
     * 缓存过期时间
     * 7天
     */
    public static final int INT_EXPIRED_SEVEN_DAYS = 7 * 24 * 60 * 60;

    /**
     * 24小时
     */
    public static final int HOURS_24 = 24;

    /**
     * 5分钟
     */
    public static final int MINUTES_5 = 5;

    /**
     * 休眠时间
     */
    public static final int SLEEP_100 = 100;

    /**
     * 空对象缓存字符串
     */
    public static final String EMPTY_OBJECT_STRING = "{}";

    /**
     * 初始容量
     */
    public static final int INITIAL_CAPACITY = 16;

    /**
     * 数字24
     */
    public static final int NUMBER_24 = 24;

    /**
     * 分页默认当前页
     */
    public static final int PAGE_NO_1 = 1;

}
