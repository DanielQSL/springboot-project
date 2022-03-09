package com.company.project.utils;

import java.util.Random;

/**
 * 随机数工具类
 *
 * @author DanielQSL
 */
public class RandomUtil {

    /**
     * 生成指定区间随机数 (min, max)
     *
     * @param min 区间最小值(不包含)
     * @param max 区间最大值(不包含)
     * @return
     */
    public static int genNum(int min, int max) {
        if (min >= max - 1) {
            // 自行异常处理, 此时简单返回0
            return 0;
        }
        Random random = new Random();
        return random.nextInt(max - min - 1) + min + 1;
    }

    /**
     * 生成指定区间随机数 [min, max)
     *
     * @param min 区间最小值(包含)
     * @param max 区间最大值(不包含)
     * @return
     */
    public static int genNumIncludeMin(int min, int max) {
        if (min >= max) {
            // 自行异常处理, 此时简单返回0
            return 0;
        }
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

    /**
     * 生成指定区间随机数 (min, max]
     *
     * @param min 区间最小值(不包含)
     * @param max 区间最大值(包含)
     * @return
     */
    public static int genNumIncludeMax(int min, int max) {
        return genNumIncludeMin(min, max) + 1;
    }

    /**
     * 生成指定区间随机数 [min, max]
     *
     * @param min 区间最小值(包含)
     * @param max 区间最大值(包含)
     * @return
     */
    public static int genNumIncludeMinAndMax(int min, int max) {
        if (min >= max + 1) {
            // 自行异常处理, 此时简单返回0
            return 0;
        }
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }

    /**
     * 生成指定长度随机数
     *
     * @param len 指定长度
     * @return
     */
    public static int genNumByLen(int len) {
        if (len < 1 || len > 9) {
            // 自行异常处理, 此时简单返回0
            return 0;
        }
        return Integer.parseInt(genNumStrByLen(len));
    }

    /**
     * 生成指定长度随机数
     *
     * @param len 指定长度
     * @return
     */
    public static String genNumStrByLen(int len) {
        if (len < 1) {
            // 自行异常处理, 此时简单返回"0"
            return "0";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            if (i == 0) {
                sb.append(genNumIncludeMax(0, 9));
            } else {
                sb.append(genNumIncludeMinAndMax(0, 9));
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(genNum(2, 4));    // 3
        System.out.println(genNumIncludeMin(2, 4));    // 2,3中随机一个数
        System.out.println(genNumIncludeMax(2, 4));    // 3,4中随机一个数
        System.out.println(genNumIncludeMinAndMax(2, 4));    // 2,3,4中随机一个数
        System.out.println(genNumByLen(3));    // 100至999中随机一个数, 返回结果为数字
        System.out.println(genNumStrByLen(3));    // 100至999中随机一个数, 返回结果为字符串
    }

}
