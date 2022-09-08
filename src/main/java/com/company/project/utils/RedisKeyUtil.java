package com.company.project.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import static com.company.project.constants.RedisConstants.*;

/**
 * 缓存工具类
 *
 * @author DanielQSL
 */
@Slf4j
public class RedisKeyUtil {

    /**
     * 单入参加盐过期时间
     * 加盐：24小时随机数+定制化入参
     *
     * @param seconds 不加盐过期时间
     * @return 加盐过期时间
     */
    public static int redisKeyRandomTime(int seconds) {
        try {
            int hours = ThreadLocalRandom.current().nextInt(HOURS_24);
            return seconds + hours * INT_EXPIRED_ONE_HOUR;
        } catch (Exception e) {
            log.error("单入参加盐过期时间异常 seconds: {}", seconds, e);
            return seconds;
        }
    }

    /**
     * 多入参加盐过期时间
     * 加盐：随机数+定制化入参
     *
     * @param seconds  不加盐过期时间
     * @param timeUnit 过期时间单位
     * @param time     过期时间值
     * @return 加盐过期时间
     */
    public static long redisKeyRandomTime(int seconds, TimeUnit timeUnit, int time) {
        try {
            if (Objects.isNull(timeUnit) || time <= 0) {
                return seconds;
            }
            long number = ThreadLocalRandom.current().nextInt(time);
            long randomTime;
            switch (timeUnit) {
                case DAYS:
                    randomTime = number * INT_EXPIRED_ONE_DAY;
                    break;
                case HOURS:
                    randomTime = number * INT_EXPIRED_ONE_HOUR;
                    break;
                case MINUTES:
                    randomTime = number * INT_EXPIRED_ONE_MINUTE;
                    break;
                default:
                    return seconds;
            }
            return seconds + randomTime;
        } catch (Exception e) {
            log.error("多入参加盐过期时间异常 seconds: {},timeUnit: {},time: {}", seconds, timeUnit, time, e);
            return seconds;
        }
    }

}
