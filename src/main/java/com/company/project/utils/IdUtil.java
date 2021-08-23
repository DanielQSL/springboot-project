package com.company.project.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.UUID;

/**
 * ID生成工具类
 *
 * @author DanielQSL
 */
public class IdUtil {

    private IdUtil() {
    }

    /**
     * 图片名生成
     */
    public static String genImageName() {
        //取当前时间的长整形值包含毫秒
        long millis = System.currentTimeMillis();
        //加上三位随机数
        Random random = new Random();
        int end3 = random.nextInt(999);
        //如果不足三位前面补0
        String str = millis + String.format("%03d", end3);
        return str;
    }

    /**
     * 生成请求ID
     * 规则：月日+uuid
     *
     * @return requestId
     */
    public static String generateRequestId() {
        return DateTimeFormatter.ofPattern("MMdd").format(LocalDateTime.now())
                + UUID.randomUUID().toString().replace("-", "");
    }

}
