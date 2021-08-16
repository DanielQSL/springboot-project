package com.company.project.context;

import lombok.Data;

import java.util.*;

/**
 * 用户上下文信息
 *
 * @author DanielQSL
 */
@Data
public class UserContext {

    /**
     * 标识用户唯一编码
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 角色
     */
    private List<String> roles;

    /**
     * 权限
     */
    private Set<String> authorities;

    /**
     * 菜单
     */
    private List<String> menus;

    /**
     * 机构编码
     */
    private String orgCode;

    /**
     * 菜单类型，web还是终端
     */
    private String sysCode;

    /**
     * 语言
     */
    private Locale locale;

    /**
     * 用户类型 0：平台，1：租户，2：普通
     */
    private Integer type;

    /**
     * 租户
     */
    private String tenantCode;

    /**
     * 动态参数
     */
    private Map<String, Object> params = new HashMap<>();

}
