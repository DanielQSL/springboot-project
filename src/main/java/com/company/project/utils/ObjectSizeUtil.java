package com.company.project.utils;

import com.carrotsearch.sizeof.RamUsageEstimator;

/**
 * 计算对象大小
 *
 * @author danielqsl
 */
public class ObjectSizeUtil {

    public static void main(String[] args) {
        String object = "test";
        System.out.println(RamUsageEstimator.sizeOf(object));
    }

}
