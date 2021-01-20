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

}
