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

    private final String desc;

    EnvType(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

}
