package com.company.project.utils;

import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * 通用工具类
 *
 * @author DanielQSL
 * @date 2019/11/6
 */
public class CommonUtil {

    private static final int MOBILE_LENGTH = 11;

    private CommonUtil() {
    }

    /**
     * 手机号码前三后四脱敏
     *
     * @param mobile 手机号
     * @return 脱敏结果
     */
    public static String mobileEncrypt(String mobile) {
        if (StringUtils.isEmpty(mobile) || (mobile.length() != MOBILE_LENGTH)) {
            return mobile;
        }
        return mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }

}
