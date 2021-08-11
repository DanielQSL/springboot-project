package com.company.project.interceptor;

import com.company.project.common.CommonResponse;
import com.company.project.context.UserContextHolder;
import com.company.project.dto.UserDTO;
import com.company.project.enums.ResponseCodeEnum;
import com.company.project.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * 用户Session 拦截器
 *
 * @author DanielQSL
 */
@Slf4j
@Component
public class UserSessionInterceptor implements HandlerInterceptor {

    private static final String HEADER_USER_TOKEN = "user-token";

    private static final String USER_INFO_CACHE_PREFIX = "user:info:";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 拦截请求，在Controller方法处理之前
     *
     * @return 返回false：请求被拦截，返回。返回true：请求OK，继续执行
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userToken = this.getToken(request);

        if (StringUtils.isBlank(userToken)) {
            this.returnErrorResponse(response, CommonResponse.fail(ResponseCodeEnum.UNAUTHORIZED));
            return false;
        }
        // 缓存中获取用户信息
        UserDTO userInfo = (UserDTO) redisTemplate.opsForValue().get(USER_INFO_CACHE_PREFIX + userToken);
        if (Objects.isNull(userInfo) || Objects.isNull(userInfo.getId())) {
            this.returnErrorResponse(response, CommonResponse.fail(ResponseCodeEnum.UNAUTHORIZED));
            return false;
        }
        // 设置UserThreadLocal
        UserContextHolder.setCurrentUser(userInfo);
        return true;
    }

    /**
     * 抛出错误信息
     *
     * @param response HttpServletResponse
     * @param result   响应结果
     * @throws IOException IO异常
     */
    public void returnErrorResponse(HttpServletResponse response, CommonResponse result)
            throws IOException {
        try (OutputStream out = response.getOutputStream()) {
//            response.setHeader("content-type", "application/json;charset=UTF-8");
            response.setContentType("application/json;charset=UTF-8");
            out.write(JsonUtil.toJsonString(result).getBytes(StandardCharsets.UTF_8));
            out.flush();
        }
    }

    /**
     * 获取请求头中的token
     *
     * @param request 请求参数
     * @return token
     */
    private String getToken(HttpServletRequest request) {
        return request.getHeader(HEADER_USER_TOKEN);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserContextHolder.remove();
    }

}
