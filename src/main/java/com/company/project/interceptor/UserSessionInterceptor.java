package com.company.project.interceptor;

import com.company.project.context.UserContext;
import com.company.project.context.UserContextHolder;
import com.company.project.enums.ResponseCodeEnum;
import com.company.project.model.CommonResponse;
import com.company.project.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

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

    private static final String HEADER_TRACE_ID = "trace-id";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Value("${login.auth.enabled:true}")
    private Boolean loginEnabled;

    @Value("#{'${login.url.whiteList:}'.split(',')}")
    private List<String> loginUrlWhiteList;

    /**
     * 拦截请求，在Controller方法处理之前
     *
     * @return 返回false：请求被拦截，返回。返回true：请求OK，继续执行
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 是否开启登录验证
        if (!loginEnabled) {
            return true;
        }
        // 判断url是否为登录白名单地址
        if (isWhiteListLoginUrl(request)) {
            return true;
        }

        String userToken = this.getToken(request);

        if (StringUtils.isBlank(userToken)) {
            this.generateResponse(response, CommonResponse.fail(ResponseCodeEnum.UNAUTHORIZED));
            return false;
        }
        // 缓存中获取用户信息
        UserContext userInfo = (UserContext) redisTemplate.opsForValue().get(USER_INFO_CACHE_PREFIX + userToken);
        if (Objects.isNull(userInfo) || Objects.isNull(userInfo.getId())) {
            this.generateResponse(response, CommonResponse.fail(ResponseCodeEnum.UNAUTHORIZED));
            return false;
        }
        // 设置UserThreadLocal
        UserContextHolder.setCurrentUser(userInfo);
        return true;
    }

    /**
     * 判断url是否为登录白名单地址
     *
     * @param request HttpServletRequest
     * @return 是否
     */
    private boolean isWhiteListLoginUrl(HttpServletRequest request) {
        if (CollectionUtils.isEmpty(loginUrlWhiteList)) {
            return false;
        }
        for (String url : loginUrlWhiteList) {
            if (url.equals(request.getRequestURI())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取请求唯一标识
     *
     * @param request 请求
     * @return 唯一标识
     */
    private String getTraceId(HttpServletRequest request) {
        String traceId = request.getHeader(HEADER_TRACE_ID);
        if (traceId == null) {
            traceId = UUID.randomUUID().toString().replace("-", "");
        }
        // 方便日志调用链路的跟踪
        MDC.put("trace_id", traceId);
        return traceId;
    }

    /**
     * 生成响应结果
     *
     * @param response HttpServletResponse
     * @param result   响应结果
     * @throws IOException IO异常
     */
    public void generateResponse(HttpServletResponse response, CommonResponse result)
            throws IOException {
        try (OutputStream out = response.getOutputStream()) {
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
