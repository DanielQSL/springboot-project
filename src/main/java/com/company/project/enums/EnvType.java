package com.company.project.enums;

/**
 * 环境类型枚举
 *
 * @author DanielQSL
 */
public enum EnvType {

    /**
     * 环境类型
     */
    DEV("dev"), QA("qa"), PL("pl"), ONLINE("online");

    /**
     * 值
     */
    private final String value;

    EnvType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
