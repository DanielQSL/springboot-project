package com.company.project.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Cookie工具类
 *
 * @author DanielQSL
 */
public class CookieUtil {

    /**
     * 永久保存年龄
     */
    public static final int PERMANENT_AGE = -1;

    /**
     * 最大缓存时间：单位/秒
     */
    public static final int COOKIE_MAX_AGE = Integer.MAX_VALUE;

    /**
     * 默认保存路径：根路径
     */
    private static final String DEFAULT_COOKIE_PATH = "/";

    private CookieUtil() {

    }

    /**
     * 保存
     *
     * @param response 响应
     * @param key      键
     * @param value    值
     * @param maxAge   最大年龄 -1代表永久
     */
    public static void set(HttpServletResponse response, String key, String value, int maxAge) {
        set(response, key, value, null, DEFAULT_COOKIE_PATH, maxAge, true);
    }

    /**
     * 保存
     *
     * @param response 响应
     * @param key      键
     * @param value    值
     * @param domain   域
     * @param maxAge   最大年龄 -1代表永久
     */
    public static void set(HttpServletResponse response, String key, String value, String domain, int maxAge) {
        set(response, key, value, domain, DEFAULT_COOKIE_PATH, maxAge, true);
    }

    /**
     * 保存
     *
     * @param response   响应
     * @param key        键
     * @param value      值
     * @param domain     域
     * @param path       路径
     * @param maxAge     最大年龄 -1代表永久
     * @param isHttpOnly 是否对客户端脚本隐藏的标志
     */
    public static void set(HttpServletResponse response, String key, String value, String domain, String path, int maxAge, boolean isHttpOnly) {
        Cookie cookie = new Cookie(key, value);
        if (domain != null) {
            cookie.setDomain(domain);
        }
        cookie.setPath(path);
        cookie.setMaxAge(maxAge);
        cookie.setHttpOnly(isHttpOnly);
        response.addCookie(cookie);
    }

    /**
     * 查询value
     *
     * @param request 请求
     * @param key     键
     * @return 值
     */
    public static String getValue(HttpServletRequest request, String key) {
        Cookie cookie = get(request, key);
        if (cookie != null) {
            return cookie.getValue();
        }
        return null;
    }

    /**
     * 查询Cookie
     *
     * @param request 请求
     * @param key     键
     * @return Cookie
     */
    private static Cookie get(HttpServletRequest request, String key) {
        Cookie[] arrCookie = request.getCookies();
        if (arrCookie != null && arrCookie.length > 0) {
            for (Cookie cookie : arrCookie) {
                if (cookie.getName().equals(key)) {
                    return cookie;
                }
            }
        }
        return null;
    }

    /**
     * 删除Cookie
     *
     * @param request  请求
     * @param response 响应
     * @param domain   域
     * @param path     路径
     * @param key      键
     */
    public static void remove(HttpServletRequest request, HttpServletResponse response, String domain, String path, String key) {
        Cookie cookie = get(request, key);
        if (cookie != null) {
            set(response, key, "", domain, path, 0, true);
        }
    }

    /**
     * 删除Cookie
     *
     * @param request  请求
     * @param response 响应
     * @param domain   域
     * @param key      键
     */
    public static void remove(HttpServletRequest request, HttpServletResponse response, String domain, String key) {
        remove(request, response, domain, DEFAULT_COOKIE_PATH, key);
    }

    /**
     * 删除Cookie
     *
     * @param request  请求
     * @param response 响应
     * @param key      键
     */
    public static void remove(HttpServletRequest request, HttpServletResponse response, String key) {
        remove(request, response, null, DEFAULT_COOKIE_PATH, key);
    }

}
