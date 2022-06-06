package com.company.project.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

/**
 * IP工具类
 *
 * @author DanielQSL
 */
@Slf4j
public class IpUtil {

    private static final String IPV4_REGEX = "(((\\d{1,2})|(1\\d{1,2})|(2[0-4]\\d)|(25[0-5]))\\.){3}((\\d{1,2})|(1\\d{1,2})|(2[0-4]\\d)|(25[0-5]))";

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
        // 自定义：Header x-user-ip 记录PC，M站用户real-ip
        if (StringUtils.isNotBlank(request.getHeader("x-user-ip"))) {
            return request.getHeader("x-user-ip");
        }
        // Header x-real-ip 记录app用户
        if (StringUtils.isNotBlank(request.getHeader("x-real-ip"))) {
            return request.getHeader("x-real-ip");
        }
        // Header x-forwarded-for 记录多层代理服务器ip
        if (StringUtils.isNotBlank(request.getHeader("x-forwarded-for"))) {
            return request.getHeader("x-forwarded-for");
        }
        // Header remoteip 记录远程连接地址
        if (StringUtils.isNotBlank(request.getHeader("remoteip"))) {
            return request.getHeader("remoteip");
        }
        log.error("get ip from HttpServletRequest Header is null");
        return StringUtils.EMPTY;
    }

    /**
     * 获取IP地址
     * <p>
     * 使用Nginx等反向代理软件， 则不能通过request.getRemoteAddr()获取IP地址
     * 如果使用了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP地址，X-Forwarded-For中第一个非unknown的有效IP字符串，则为真实IP地址
     */
    public static String getIpAddr(HttpServletRequest request) {
        String unknown = "unknown";
        String ip = null;
        try {
            ip = request.getHeader("x-forwarded-for");
            if (StringUtils.isEmpty(ip) || unknown.equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (StringUtils.isEmpty(ip) || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (StringUtils.isEmpty(ip) || unknown.equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (StringUtils.isEmpty(ip) || unknown.equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (StringUtils.isEmpty(ip) || unknown.equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
        } catch (Exception e) {
            log.error("IpUtil#getIpAddr error ", e);
        }
        return ip;
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
        StringBuilder ip = new StringBuilder();
        ip.append(ipLong >>> 24).append(".");
        ip.append((ipLong >>> 16) & 0xFF).append(".");
        ip.append((ipLong >>> 8) & 0xFF).append(".");
        ip.append(ipLong & 0xFF);
        return ip.toString();
    }

    public static void main(String[] args) {
        System.out.println(ip2Long("192.168.0.1"));
        System.out.println(long2Ip(3232235521L));
        System.out.println(ip2Long("10.0.0.1"));
    }

}
