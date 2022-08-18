package com.company.project.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * IP工具类
 *
 * @author DanielQSL
 */
public class IpUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(IpUtil.class);

    private static final String IPV4_REGEX = "(((\\d{1,2})|(1\\d{1,2})|(2[0-4]\\d)|(25[0-5]))\\.){3}((\\d{1,2})|(1\\d{1,2})|(2[0-4]\\d)|(25[0-5]))";

    private static final String UNKNOWN = "unknown";

    private IpUtil() {

    }

    /**
     * 校验ip是否合法
     *
     * @param ip ip地址
     * @return 是否合法
     */
    public static boolean isValid(String ip) {
        return Pattern.matches(IPV4_REGEX, ip);
    }

    /**
     * 获取请求的IP地址
     *
     * @param request 请求
     * @return IP地址
     */
    public static String getIpAddress(HttpServletRequest request) {
        if (Objects.isNull(request)) {
            return StringUtils.EMPTY;
        }
        String ip;
        // 自定义：Header x-user-ip 记录PC用户ip
        ip = request.getHeader("x-user-ip");
        if (StringUtils.isNotBlank(ip) && !UNKNOWN.equalsIgnoreCase(ip)) {
            return ip;
        }
        // Header x-real-ip 记录app用户ip
        ip = request.getHeader("x-real-ip");
        if (StringUtils.isNotBlank(ip) && !UNKNOWN.equalsIgnoreCase(ip)) {
            return ip;
        }
        // Header x-forwarded-for 记录多层代理服务器ip，值并不止一个，而是一串IP地址。如 x-forwarded-for: client, proxy1, proxy2
        ip = request.getHeader("x-forwarded-for");
        if (StringUtils.isNotBlank(ip) && !UNKNOWN.equalsIgnoreCase(ip)) {
            return ip;
        }
        // Header request.getRemoteAddr() 若使用Nginx等反向代理软件，则不能通过此方法获取IP地址
        ip = request.getRemoteAddr();
        if (StringUtils.isNotBlank(ip) && !UNKNOWN.equalsIgnoreCase(ip)) {
            return ip;
        }
        LOGGER.error("Get IP from HttpServletRequest Header failed");
        return StringUtils.EMPTY;
    }

    // =========在保存IPv4地址时，一个IPv4最小需要7个字符，最大需要15个字符，而使用无符号整数来存储，只需要4个字节即可。============

    /**
     * 把字符串IP转换成long
     * （仅限于IPv4）
     *
     * @param ipStr 字符串IP
     * @return IP对应的long值
     */
    public static long ip2Long(String ipStr) {
        String[] ip = ipStr.split("\\.");
        return (Long.parseLong(ip[0]) << 24) + (Long.parseLong(ip[1]) << 16)
                + (Long.parseLong(ip[2]) << 8) + Long.parseLong(ip[3]);
    }

    /**
     * 把IP的long值转换成字符串
     * （仅限于IPv4）
     *
     * @param ipLong IP的long值
     * @return long值对应的字符串
     */
    public static String long2Ip(long ipLong) {
        return (ipLong >>> 24) + "." +
                ((ipLong >>> 16) & 0xFF) + "." +
                ((ipLong >>> 8) & 0xFF) + "." +
                (ipLong & 0xFF);
    }

    public static void main(String[] args) {
        System.out.println(ip2Long("192.168.0.1"));
        System.out.println(long2Ip(3232235521L));
        System.out.println(ip2Long("10.0.0.1"));
    }

}
