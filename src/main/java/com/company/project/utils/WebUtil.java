package com.company.project.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * Web工具类
 *
 * @author DanielQSL
 */
public class WebUtil {

    private static final Logger logger = LoggerFactory.getLogger(WebUtil.class);

    public static final String USER_TOKEN = "user-token";

    private WebUtil() {

    }

    /**
     * 从Spring请求上下文中获取当前HttpServletRequest
     *
     * @return HttpServletRequest
     */
    public static HttpServletRequest getCurrentRequest() {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attrs == null) {
            logger.warn("get current request failed");
            return null;
        }
        return attrs.getRequest();
    }

    /**
     * 从Spring请求上下文中获取当前HttpServletResponse
     *
     * @return HttpServletResponse
     */
    public static HttpServletResponse getCurrentResponse() {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attrs == null) {
            logger.warn("get current response failed");
            return null;
        }
        return attrs.getResponse();
    }

    /**
     * 生成响应结果
     *
     * @param response HttpServletResponse
     * @param obj      信息
     * @throws IOException IO异常
     */
    public static void write2Response(HttpServletResponse response, Object obj)
            throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        try (OutputStream out = response.getOutputStream()) {
            out.write(JsonUtil.toJsonString(obj).getBytes(StandardCharsets.UTF_8));
            out.flush();
        }
    }

    /**
     * 根据请求头的key从当前Spring请求中获取请求头的值
     *
     * @param key 键
     * @return 值
     */
    public static String getHeaderByKey(String key) {
        HttpServletRequest request = getCurrentRequest();
        if (request == null) {
            logger.warn("get current request failed");
            return null;
        }
        return request.getHeader(key);
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
