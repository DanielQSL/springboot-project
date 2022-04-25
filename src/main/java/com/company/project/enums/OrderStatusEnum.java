package com.company.project.enums;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单号状态枚举
 *
 * @author DanielQSL
 */
@Getter
@AllArgsConstructor
public enum OrderStatusEnum {

    /**
     * 订单号状态
     */
    NULL(0, "未知"),
    CREATED(10, "已创建"),
    PAID(20, "已支付"),
    FULFILL(30, "已履约"),
    OUT_STOCK(40, "出库"),
    DELIVERY(50, "配送中"),
    SIGNED(60, "已签收"),
    CANCELED(70, "已取消"),
    REFUSED(100, "已拒收"),
    INVALID(127, "无效订单"),
    ;

    /**
     * 代码
     */
    private final Integer code;

    /**
     * 描述
     */
    private final String desc;

    /**
     * 根据 code 获取枚举
     *
     * @param code code
     * @return 枚举
     */
    public static OrderStatusEnum getByCode(int code) {
        for (OrderStatusEnum e : values()) {
            if (e.getCode() == code) {
                return e;
            }
        }
        return null;
    }

    /**
     * 根据 code 获取描述
     *
     * @param code code
     * @return 描述
     */
    public static String getDescByCode(int code) {
        OrderStatusEnum e = getByCode(code);
        return null == e ? null : e.getDesc();
    }

    /**
     * 根据描述获取 code
     *
     * @param desc 描述
     * @return code
     */
    public static Integer getCodeByDesc(String desc) {
        for (OrderStatusEnum e : values()) {
            if (e.getDesc().equals(desc)) {
                return e.getCode();
            }
        }
        return null;
    }

    /**
     * 转化为Map结构
     *
     * @return Map
     */
    public static Map<Integer, String> toMap() {
        Map<Integer, String> map = new HashMap<>(16);
        for (OrderStatusEnum e : values()) {
            map.put(e.getCode(), e.getDesc());
        }
        return map;
    }

    /**
     * 未出库订单状态列表
     */
    public static List<Integer> unOutStockStatus() {
        return Lists.newArrayList(
                CREATED.code,
                PAID.code,
                FULFILL.code
        );
    }

    /**
     * 可以移除的订单状态
     */
    public static List<Integer> canRemoveStatus() {
        return Lists.newArrayList(
                SIGNED.code,
                CANCELED.code,
                REFUSED.code,
                INVALID.code
        );
    }

}
