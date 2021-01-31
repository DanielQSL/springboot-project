package com.company.project.interceptor;

import com.alibaba.fastjson.JSON;
import com.company.project.common.ServerResponse;
import com.company.project.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * 拦截器
 *
 * @author DanielQSL
 */
@Slf4j
@Component
public class MiniInterceptor implements HandlerInterceptor {

    @Autowired
    public RedisUtil redisUtil;

    public static final String USER_REDIS_SESSION = "user-redis-session";

    /**
     * 拦截请求，在controller调用之前
     *
     * @param request
     * @param response
     * @param handler
     * @return 返回false：请求被拦截，返回
     * 返回true：请求OK，继续执行
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userId = request.getHeader("userId");
        String userToken = request.getHeader("userToken");
        if (StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(userToken)) {
            String uniqueToken = (String) redisUtil.get(USER_REDIS_SESSION + ":" + userId);
            if (StringUtils.isBlank(uniqueToken)) {
                log.info("{}请先登陆", userId);
                returnErrorResponse(response, ServerResponse.fail(502, "请先登陆"));
                return false;
            } else {
                if (!uniqueToken.equals(userToken)) {
                    log.info("{}账号在别的设备登陆", userId);
                    returnErrorResponse(response, ServerResponse.fail(502, "账号被挤出"));
                }
            }
            return true;
        } else {
            log.info("{}请先登陆", userId);
            returnErrorResponse(response, ServerResponse.fail(502, "请先登陆"));
            return false;
        }
    }

    /**
     * 抛出错误信息
     *
     * @param response HttpServletResponse
     * @param result   响应结果
     * @throws IOException IO异常
     */
    public void returnErrorResponse(HttpServletResponse response, ServerResponse result)
            throws IOException {
        try (OutputStream out = response.getOutputStream()) {
            response.setContentType("application/json;charset=UTF-8");
            out.write(JSON.toJSONString(result).getBytes(StandardCharsets.UTF_8));
            out.flush();
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

}
