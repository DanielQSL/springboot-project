package com.company.project.utils;

import java.util.Random;

/**
 * ID生成工具类
 *
 * @author DanielQSL
 */
public class IdUtil {

    private static final int[] WIDTH_4 = {10, 2, 11, 3, 0, 1, 9, 7, 12, 6, 4, 8, 5};

    private static final int[] WIDTH_5 = {4, 3, 13, 15, 7, 8, 6, 2, 1, 10, 5, 12, 0, 11, 14, 9};

    private static final int[] WIDTH_6 = {2, 7, 10, 9, 16, 3, 6, 8, 0, 4, 1, 12, 11, 13, 18, 5, 15, 17, 14};

    private static final int[] WIDTH_7 =
            {18, 0, 2, 22, 8, 3, 1, 14, 17, 12, 4, 19, 11, 9, 13, 5, 6, 15, 10, 16, 20, 7, 21};

    private static final int[] WIDTH_8 =
            {11, 8, 4, 0, 16, 14, 22, 7, 3, 5, 13, 18, 24, 25, 23, 10, 1, 12, 6, 21, 17, 2, 15, 9, 19, 20};

    private static final int[] WIDTH_9 =
            {24, 23, 27, 3, 9, 16, 25, 13, 28, 12, 0, 4, 10, 18, 11, 2, 17, 1, 21, 26, 5, 15, 7, 20, 22, 14, 19, 6, 8};

    private static final int[] WIDTH_10 = {32, 3, 1, 28, 21, 18, 30, 7, 12, 22, 20, 13, 16, 15, 6, 17, 9, 25, 11, 8, 4,
            27, 14, 31, 5, 23, 24, 29, 0, 10, 19, 26, 2};

    private static final int[] WIDTH_11 = {9, 13, 2, 29, 11, 32, 14, 33, 24, 8, 27, 4, 22, 20, 5, 0, 21, 25, 17, 28,
            34, 6, 23, 26, 30, 3, 7, 19, 16, 15, 12, 31, 1, 35, 10, 18};

    private static final int[] WIDTH_12 = {31, 4, 16, 33, 35, 29, 17, 37, 12, 28, 32, 22, 7, 10, 14, 26, 0, 9, 8, 3,
            20, 2, 13, 5, 36, 27, 23, 15, 19, 34, 38, 11, 24, 25, 30, 21, 18, 6, 1};

    private static final int[][] WIDTH_ARRAY =
            {WIDTH_4, WIDTH_5, WIDTH_6, WIDTH_7, WIDTH_8, WIDTH_9, WIDTH_10, WIDTH_11, WIDTH_12};

    private IdUtil() {
    }

    public static long genId(long id, int width) {
        long maxValue = (long) Math.pow(10, width) - 1;
        int superScript = (int) (Math.log(maxValue) / Math.log(2));
        long r = 0;
        long sign = (long) Math.pow(2, superScript);

        id |= sign;

        int[] mapBit = WIDTH_ARRAY[width - 4];

        for (int x = 0; x < superScript; x++) {
            long v = (id >> x) & 0x1;
            r |= (v << mapBit[x]);
        }

        r += maxValue - Math.pow(2, superScript) + 1;
        return r;
    }

    /**
     * 图片名生成
     */
    public static String genImageName() {
        // 取当前时间的长整形值包含毫秒
        long millis = System.currentTimeMillis();
        // 加上三位随机数
        Random random = new Random();
        int end3 = random.nextInt(999);
        // 如果不足三位前面补0
        return millis + String.format("%03d", end3);
    }

}
