package com.company.project.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * 优惠券类型枚举
 *
 * @author DanielQSL
 */
@Getter
@AllArgsConstructor
public enum CouponTypeEnum {

    /**
     * 优惠券类型
     */
    UNKNOWN("unknown", "000"),
    MONEY_OFF("满减券", "001"),
    DISCOUNT("打折", "002"),
    RANDOM_DISCOUNT("随机减", "003"),
    LONELY_NIGHT_MONEY_OFF("晚间双倍优惠券", "004"),
    ;

    /**
     * 优惠券类型描述
     */
    private final String description;

    /**
     * 优惠券类型编码
     */
    private final String code;

    /**
     * 根据编码获取枚举
     * 空则抛异常
     */
    public static CouponTypeEnum of(String code) {
        Objects.requireNonNull(code);
        return Stream.of(values())
                .filter(e -> e.code.equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(code + " not exists!"));
    }

    /**
     * 根据编码获取枚举
     * 不抛异常
     */
    public static CouponTypeEnum convert(String code) {
        return Stream.of(values())
                .filter(e -> e.code.equalsIgnoreCase(code))
                .findFirst()
                .orElse(UNKNOWN);
    }

}
