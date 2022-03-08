package com.company.project.utils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

/**
 * 日期时间工具类（LocalDateTime、LocalDate、LocalTime）
 * 日期格式见 {@link cn.hutool.core.date.DatePattern}
 *
 * @author DanielQSL
 * @date 2022/2/11
 */
public class DateTimeUtil {

    /**
     * 标准日期时间格式，精确到秒：yyyy-MM-dd HH:mm:ss
     */
    public static final String NORM_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 标准日期格式：yyyy-MM-dd
     */
    public static final String NORM_DATE_PATTERN = "yyyy-MM-dd";

    /**
     * 标准时间格式：HH:mm:ss
     */
    public static final String NORM_TIME_PATTERN = "HH:mm:ss";

    private DateTimeUtil() {
    }

    /**
     * Date转换为LocalDateTime
     *
     * @param date 日期
     * @return LocalDateTime类型日期
     */
    public static LocalDateTime toLocalDateTime(Date date) {
        if (date == null) {
            return null;
        }
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    /**
     * LocalDateTime转换为Date
     *
     * @param dateTime 日期时间
     * @return Date类型日期
     */
    public static Date toDate(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 时间戳（毫秒）转换为LocalDateTime
     *
     * @param milliTimestamp 时间戳（毫秒）
     * @return LocalDateTime类型日期
     */
    public static LocalDateTime toLocalDateTime(Long milliTimestamp) {
        if (milliTimestamp == null) {
            return null;
        }
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(milliTimestamp), ZoneId.systemDefault());
    }

    /**
     * 时间戳（秒）转换为LocalDateTime
     *
     * @param secondTimestamp 时间戳（秒）
     * @return LocalDateTime类型日期
     */
    public static LocalDateTime secondTimestampToLocalDateTime(Long secondTimestamp) {
        if (secondTimestamp == null) {
            return null;
        }
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(secondTimestamp), ZoneId.systemDefault());
    }

    /**
     * LocalDateTime转换为时间戳（毫秒）
     *
     * @param dateTime 时期时间
     * @return 时间戳（毫秒）
     */
    public static Long toMilliTimestamp(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * LocalDateTime转换为时间戳（秒）
     *
     * @param dateTime 时期时间
     * @return 时间戳（秒）
     */
    public static Long toSecondTimestamp(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();
    }

    /**
     * 获取指定格式的日期时间字符串
     *
     * @param dateTime 日期时间
     * @param pattern  指定格式
     * @return 指定格式的日期时间字符串
     */
    public static String formatDateTime(LocalDateTime dateTime, String pattern) {
        return dateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 获取默认格式的日期时间字符串
     *
     * @param dateTime 日期时间
     * @return 默认格式的日期时间字符串
     */
    public static String formatDateTime(LocalDateTime dateTime) {
        return formatDateTime(dateTime, NORM_DATETIME_PATTERN);
    }

    /**
     * 获取指定格式的日期字符串
     *
     * @param date    日期
     * @param pattern 指定格式
     * @return 指定格式的日期字符串
     */
    public static String formatDate(LocalDate date, String pattern) {
        return date.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 获取默认格式的日期字符串
     *
     * @param date 日期
     * @return 默认格式的日期字符串
     */
    public static String formatDate(LocalDate date) {
        return formatDate(date, NORM_DATE_PATTERN);
    }

    /**
     * 获取指定格式的时间字符串
     *
     * @param time    时间
     * @param pattern 指定格式
     * @return 指定格式的时间字符串
     */
    public static String formatTime(LocalTime time, String pattern) {
        return time.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 获取默认格式的时间字符串
     *
     * @param time 时间
     * @return 默认格式的时间字符串
     */
    public static String formatTime(LocalTime time) {
        return formatTime(time, NORM_TIME_PATTERN);
    }

    /**
     * 获取当前时间标准格式字符串
     *
     * @return 当前时间标准格式字符串
     */
    public static String getNowDateTimeStr() {
        return formatDateTime(LocalDateTime.now(), NORM_DATETIME_PATTERN);
    }

    /**
     * 将字符串日期时间转换为LocalDateTime格式
     *
     * @param dateTime 日期时间
     * @param pattern  指定格式
     * @return LocalDateTime
     */
    public static LocalDateTime parseDateTime(String dateTime, String pattern) {
        return LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 将字符串日期时间转换为LocalDateTime格式
     *
     * @param dateTime 日期时间
     * @return LocalDateTime
     */
    public static LocalDateTime parseDateTime(String dateTime) {
        return parseDateTime(dateTime, NORM_DATETIME_PATTERN);
    }

    /**
     * 将字符串日期转换为LocalDate格式
     *
     * @param date    日期
     * @param pattern 指定格式
     * @return LocalDate
     */
    public static LocalDate parseDate(String date, String pattern) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 将字符串日期转换为LocalDate格式
     *
     * @param date 日期
     * @return LocalDate
     */
    public static LocalDate parseDate(String date) {
        return parseDate(date, NORM_DATE_PATTERN);
    }

    /**
     * 将字符串时间转换为LocalTime格式
     *
     * @param time    时间
     * @param pattern 指定格式
     * @return LocalTime
     */
    public static LocalTime parseTime(String time, String pattern) {
        return LocalTime.parse(time, DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 将字符串时间转换为LocalTime格式
     *
     * @param time 时间
     * @return LocalTime
     */
    public static LocalTime parseTime(String time) {
        return parseTime(time, NORM_TIME_PATTERN);
    }

    /**
     * 判断dateTime1与dateTime2是否是同一时间
     *
     * @param dateTime1 日期时间1
     * @param dateTime2 日期时间2
     * @return 判断结果
     */
    public static boolean isEqual(LocalDateTime dateTime1, LocalDateTime dateTime2) {
        return dateTime1.isEqual(dateTime2);
    }

    /**
     * 判断dateTime1是否早于dateTime2
     *
     * @param dateTime1 日期时间1
     * @param dateTime2 日期时间2
     * @return 判断结果
     */
    public static boolean isBefore(LocalDateTime dateTime1, LocalDateTime dateTime2) {
        return dateTime1.isBefore(dateTime2);
    }

    /**
     * 判断dateTime1是否晚于dateTime2
     *
     * @param dateTime1 日期时间1
     * @param dateTime2 日期时间2
     * @return 判断结果
     */
    public static boolean isAfter(LocalDateTime dateTime1, LocalDateTime dateTime2) {
        return dateTime1.isAfter(dateTime2);
    }

    /**
     * 计算两个时间间隔多久
     *
     * @param startInclusive 开始的日期时间
     * @param endExclusive   结束的日期时间
     * @param unit           计算单位
     * @return 间隔多久
     */
    public static long between(Temporal startInclusive, Temporal endExclusive, ChronoUnit unit) {
        return unit.between(startInclusive, endExclusive);
    }

    /**
     * 获取某天的开始时间，例如：yyyy-MM-dd 00:00:00
     *
     * @param dateTime 某天的日期时间
     * @return 某天的开始时间
     */
    public static LocalDateTime getDayStart(LocalDateTime dateTime) {
        return LocalDateTime.of(dateTime.toLocalDate(), LocalTime.MIN);
    }

    /**
     * 获取某天的结束时间，例如：yyy-MM-dd 23:59:59.999999999
     *
     * @param dateTime 某天的日期时间
     * @return 某天的结束时间
     */
    public static LocalDateTime getDayEnd(LocalDateTime dateTime) {
        return LocalDateTime.of(dateTime.toLocalDate(), LocalTime.MAX);
    }

    /**
     * 根据某日期获取其所在月的第一天
     *
     * @param date 日期
     * @return 所在月的第一天
     */
    public static LocalDate getFirstDayOfMonth(LocalDate date) {
        return date.atStartOfDay().with(TemporalAdjusters.firstDayOfMonth()).toLocalDate();
    }

    /**
     * 根据某日期获取其所在月的最后一天
     *
     * @param date 日期
     * @return 所在月的最后一天
     */
    public static LocalDate getLastDayOfMonth(LocalDate date) {
        return date.atStartOfDay().with(TemporalAdjusters.lastDayOfMonth()).toLocalDate();
    }

    /**
     * 根据某日期获取其所在周的第一天
     *
     * @param date 日期
     * @return 所在周的第一天
     */
    public static LocalDate getStartDayOfWeek(LocalDate date) {
        TemporalAdjuster firstOfWeek = TemporalAdjusters.ofDateAdjuster(localDate ->
                localDate.minusDays(localDate.getDayOfWeek().getValue() - DayOfWeek.MONDAY.getValue()));
        return date.with(firstOfWeek);
    }

    /**
     * 根据某日期获取其所在周的最后一天
     *
     * @param date 日期
     * @return 所在周的最后一天
     */
    public static LocalDate getEndDayOfWeek(LocalDate date) {
        TemporalAdjuster lastOfWeek = TemporalAdjusters.ofDateAdjuster(localDate -> localDate.plusDays(
                DayOfWeek.SUNDAY.getValue() - localDate.getDayOfWeek().getValue()));
        return date.with(lastOfWeek);
    }

    /**
     * 获取设置年最初时间
     *
     * @param year 年
     * @return 该年最初时间
     */
    public static LocalDateTime getFirstDayOfYear(int year) {
        return LocalDateTime.now().withYear(year).with(TemporalAdjusters.firstDayOfYear()).with(LocalTime.MIN);
    }

    /**
     * 获取设置年最后时间
     *
     * @param year 年
     * @return 该年最后时间
     */
    public static LocalDateTime getLastDayOfYear(int year) {
        return LocalDateTime.now().withYear(year).with(TemporalAdjusters.lastDayOfYear()).with(LocalTime.MAX);
    }

}
