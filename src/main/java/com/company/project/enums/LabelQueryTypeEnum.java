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

    private final Integer code;
    private final String desc;

    /**
     * 根据code获取描述
     *
     * @param code code
     * @return 描述
     */
    public static String getDescByCode(int code) {
        for (LabelQueryTypeEnum labelQueryTypeEnum : values()) {
            if (labelQueryTypeEnum.getCode() == code) {
                return labelQueryTypeEnum.getDesc();
            }
        }
        return null;
    }

    /**
     * 根据描述获取code
     *
     * @param desc 描述
     * @return code
     */
    public static Integer getCodeByDesc(String desc) {
        for (LabelQueryTypeEnum labelQueryTypeEnum : values()) {
            if (labelQueryTypeEnum.getDesc().equals(desc)) {
                return labelQueryTypeEnum.getCode();
            }
        }
        return null;
    }

    /**
     * 根据code找到对应的枚举
     *
     * @param code 编码
     * @return 枚举类型
     */
    public static LabelQueryTypeEnum codeParse(int code) {
        for (LabelQueryTypeEnum labelQueryTypeEnum : values()) {
            if (labelQueryTypeEnum.getCode() == code) {
                return labelQueryTypeEnum;
            }
        }
        return null;
    }

}
