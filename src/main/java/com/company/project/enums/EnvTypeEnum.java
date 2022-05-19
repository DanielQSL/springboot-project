package com.company.project.enums;

/**
 * 环境类型枚举
 *
 * @author DanielQSL
 */
public enum EnvTypeEnum {

    /**
     * 开发环境
     */
    DEV("dev"),

    /**
     * 测试环境
     */
    QA("qa"),

    /**
     * 预发布环境
     */
    PL("pl"),

    /**
     * 线上环境
     */
    ONLINE("online"),
    ;

    private final String desc;

    EnvTypeEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

}
