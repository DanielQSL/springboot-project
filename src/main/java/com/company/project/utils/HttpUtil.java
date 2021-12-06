package com.company.project.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Http工具类
 *
 * @author DanielQSL
 */
public class HttpUtil {

    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    private HttpUtil() {

    }

    /**
     * 从Spring请求上下文中获取当前HttpServletRequest
     *
     * @return HttpServletRequest
     */
    public static HttpServletRequest getCurrentRequest() {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attrs == null) {
            logger.debug("get current request failed");
            return null;
        }
        return attrs.getRequest();
    }

}
