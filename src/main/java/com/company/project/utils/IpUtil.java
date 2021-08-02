package com.company.project.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 获取用户IP
 *
 * @author qianshuailong
 * @date 2020/11/28
 */
@Slf4j
public class IpUtil {

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
