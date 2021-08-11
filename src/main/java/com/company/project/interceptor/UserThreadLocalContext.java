package com.company.project.interceptor;

import com.company.project.dto.UserDTO;

import java.util.Objects;

/**
 * 当前线程的用户信息上下文
 *
 * @author DanielQSL
 */
public class UserThreadLocalContext {

    /**
     * 线程变量，存放user实体类信息
     */
    private static ThreadLocal<UserDTO> userThreadLocal = new ThreadLocal<>();

    /**
     * 从当前线程变量中获取用户信息
     *
     * @return 用户信息
     */
    public static UserDTO getUserThreadLocal() {
        return userThreadLocal.get();
    }

    /**
     * 为当前的线程变量赋值上用户信息
     *
     * @param user 用户信息
     */
    public static void setUserThreadLocal(UserDTO user) {
        userThreadLocal.set(user);
    }

    /**
     * 清除当前线程的用户信息
     */
    public static void remove() {
        userThreadLocal.remove();
    }

    /**
     * 获取当前登录用户的ID
     * 未登录返回null
     *
     * @return 用户ID
     */
    public static Long getLoginUserId() {
        UserDTO user = userThreadLocal.get();
        if (Objects.nonNull(user) && Objects.nonNull(user.getId())) {
            return user.getId();
        }
        return null;
    }

}
