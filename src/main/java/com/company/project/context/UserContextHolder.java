package com.company.project.context;

import java.util.Objects;

/**
 * 用户信息上下文
 *
 * @author DanielQSL
 */
public class UserContextHolder {

    /**
     * 线程变量，存放user实体类信息
     */
    private static final ThreadLocal<UserContext> userInfoHolder = new InheritableThreadLocal<>();

    /**
     * 从当前线程变量中获取用户信息
     *
     * @return 用户信息
     */
    public static UserContext getCurrentUser() {
        return userInfoHolder.get();
    }

    /**
     * 为当前的线程变量赋值上用户信息
     *
     * @param user 用户信息
     */
    public static void setCurrentUser(UserContext user) {
        userInfoHolder.set(user);
    }

    /**
     * 清除当前线程的用户信息
     */
    public static void remove() {
        userInfoHolder.remove();
    }

    /**
     * 获取当前用户的ID
     * 未登录返回null
     *
     * @return 用户ID
     */
    public static Long getUserId() {
        UserContext user = getCurrentUser();
        if (Objects.nonNull(user)) {
            return user.getId();
        }
        return null;
    }

    /**
     * 获取当前用户的用户名
     * 未登录返回null
     *
     * @return 用户名
     */
    public static String getUsername() {
        UserContext user = getCurrentUser();
        if (Objects.nonNull(user)) {
            return user.getUsername();
        }
        return null;
    }

}
