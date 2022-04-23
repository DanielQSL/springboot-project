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
     * @return 随机数
     */
    public static int genNum(int min, int max) {
        if (min >= max - 1) {
            throw new IllegalArgumentException("invalid params");
        }
        Random random = new Random();
        return random.nextInt(max - min - 1) + min + 1;
    }

    /**
     * 生成指定区间随机数 [min, max)
     *
     * @param min 区间最小值(包含)
     * @param max 区间最大值(不包含)
     * @return 随机数
     */
    public static int genNumIncludeMin(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("invalid params");
        }
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

    /**
     * 生成指定区间随机数 (min, max]
     *
     * @param min 区间最小值(不包含)
     * @param max 区间最大值(包含)
     * @return 随机数
     */
    public static int genNumIncludeMax(int min, int max) {
        return genNumIncludeMin(min, max) + 1;
    }

    /**
     * 生成指定区间随机数 [min, max]
     *
     * @param min 区间最小值(包含)
     * @param max 区间最大值(包含)
     * @return 随机数
     */
    public static int genNumIncludeMinAndMax(int min, int max) {
        if (min >= max + 1) {
            throw new IllegalArgumentException("invalid params");
        }
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }

    /**
     * 生成指定长度随机数
     *
     * @param len 指定长度
     * @return 随机数
     */
    public static int genNumByLen(int len) {
        if (len < 1 || len > 9) {
            throw new IllegalArgumentException("invalid params");
        }
        return Integer.parseInt(genNumStrByLen(len));
    }

    /**
     * 生成指定长度随机数
     *
     * @param len 指定长度
     * @return 随机数字符串
     */
    public static String genNumStrByLen(int len) {
        if (len < 1) {
            throw new IllegalArgumentException("invalid params");
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
