package com.company.project.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * 优惠券分类枚举
 *
 * @author DanielQSL
 */
@Getter
@AllArgsConstructor
public enum CouponCategoryEnum {

    /**
     * 优惠券分类
     */
    MANJIAN("001", "满减券"),
    ZHEKOU("002", "折扣券"),
    LIJIAN("003", "立减券"),
    ;

    /**
     * 优惠券分类编码
     */
    private final String code;

    /**
     * 优惠券描述(分类)
     */
    private final String description;

    /**
     * 根据编码获取枚举
     * 空则抛异常
     */
    public static CouponCategoryEnum of(String code) {
        Objects.requireNonNull(code);
        return Stream.of(values())
                .filter(e -> e.code.equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(code + " not exists!"));
    }

}
