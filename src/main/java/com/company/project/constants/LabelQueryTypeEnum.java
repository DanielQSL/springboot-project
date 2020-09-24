package com.company.project.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * 标签查询类型枚举
 *
 * @author qianshuailong
 * @date 2020/9/18
 */
@Getter
@AllArgsConstructor
public enum LabelQueryTypeEnum {
    /**
     * 标签查询类型
     */
    CLIENT_CODE(1, "client_code"),
    CLIENT_ID(2, "client_id"),
    PUID(3, "user_id"),
    ;

    private int code;
    private String desc;

    /**
     * 根据描述获取code
     *
     * @param desc 描述
     * @return code
     */
    public static int getCodeByDesc(String desc) {
        for (LabelQueryTypeEnum labelQueryTypeEnum : LabelQueryTypeEnum.values()) {
            if (labelQueryTypeEnum.getDesc().equals(desc)) {
                return labelQueryTypeEnum.getCode();
            }
        }
        return 0;
    }

    /**
     * 根据code获取描述
     *
     * @param code code
     * @return 描述
     */
    public static String getDescByCode(int code) {
        for (LabelQueryTypeEnum labelQueryTypeEnum : LabelQueryTypeEnum.values()) {
            if (labelQueryTypeEnum.getCode() == code) {
                return labelQueryTypeEnum.getDesc();
            }
        }
        return StringUtils.EMPTY;
    }

}
