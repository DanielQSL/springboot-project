package com.company.project.utils;

import org.apache.commons.lang3.StringUtils;
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

    public static final String USER_TOKEN = "user-token";

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

    /**
     * 获取用户token
     */
    public static String getUserToken() {
        HttpServletRequest request = getCurrentRequest();
        if (request == null) {
            return null;
        }
        // 先从header中获取，header中没有从Cookie中获取
        String token = request.getHeader(USER_TOKEN);
        if (StringUtils.isBlank(token)) {
            token = CookieUtil.getValue(request, USER_TOKEN);
        }
        return token;
    }

}
