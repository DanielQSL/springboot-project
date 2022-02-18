package com.company.project.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 标签查询类型枚举
 *
 * @author DanielQSL
 */
@Getter
@AllArgsConstructor
public enum LabelQueryTypeEnum {
    /**
     * 标签查询类型
     */
    CLIENT_CODE(1, "client_code"),
    CLIENT_ID(2, "client_id"),
    USER_ID(3, "user_id"),
    ;

    /**
     * 值
     */
    private final Integer value;

    /**
     * 描述
     */
    private final String desc;

    /**
     * 根据 value 获取枚举
     *
     * @param value value
     * @return 枚举
     */
    public static LabelQueryTypeEnum getEnumByValue(int value) {
        for (LabelQueryTypeEnum labelQueryTypeEnum : values()) {
            if (labelQueryTypeEnum.getValue() == value) {
                return labelQueryTypeEnum;
            }
        }
        return null;
    }

    /**
     * 根据 value 获取描述
     *
     * @param value value
     * @return 描述
     */
    public static String getDescByValue(int value) {
        LabelQueryTypeEnum e = getEnumByValue(value);
        return null == e ? null : e.getDesc();
    }

    /**
     * 根据描述获取 value
     *
     * @param desc 描述
     * @return value
     */
    public static Integer getValueByDesc(String desc) {
        for (LabelQueryTypeEnum labelQueryTypeEnum : values()) {
            if (labelQueryTypeEnum.getDesc().equals(desc)) {
                return labelQueryTypeEnum.getValue();
            }
        }
        return null;
    }

}
