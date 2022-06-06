package com.company.project.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 时间拦截器
 * {@link com.company.project.config.WebMvcConfig#addInterceptors(InterceptorRegistry)}
 *
 * @author DanielQSL
 * @date 2021/1/20
 */
@Slf4j
public class TimeInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("在请求处理之前进行调用（Controller方法调用之前）");
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        log.info("controller object is {}", handlerMethod.getBean().getClass().getName());
        log.info("controller method is {}", handlerMethod.getMethod());

        request.setAttribute("startTime", System.currentTimeMillis());
        // 需要返回true，否则请求不会被控制器处理
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("执行完方法之后执行(Controller方法调用之后)，但是此时还没进行视图渲染");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）");
        Long startTime = (Long) request.getAttribute("startTime");
        log.info("time interceptor 耗时 {}", (System.currentTimeMillis() - startTime));
    }

}
