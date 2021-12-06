package com.company.project.enums;

import com.company.project.model.BaseCommonError;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 订单错误码
 * <p>
 * 错误码区间 [108_00_0000 ~ 109_00_0000)
 *
 * @author DanielQSL
 */
@Getter
@AllArgsConstructor
public enum OrderErrorCodeEnum implements BaseCommonError {

    // ========== 订单 ==========
    ORDER_NOT_EXISTENT(108_00_0000, "获取订单不存在!"),
    ORDER_GET_SKU_FAIL(108_00_0001, "获取商品失败!"),
    ORDER_GET_SKU_NOT_EXISTENT(108_00_0002, "获取的商品不存在!"),
    ORDER_PAY_AMOUNT_NOT_NEGATIVE(108_00_0003, "支付金额不能为负数!"),
    ORDER_STATUS_NOT_CANCEL(108_00_0004, "订单状态不能取消!"),
    ORDER_DELIVERY_INCORRECT_DATA(108_00_0005, "订单发货数据不正确!"),
    ORDER_INSUFFICIENT_INVENTORY(108_00_00006, "库存不足!"),
    ORDER_GOODS_AMOUNT_INCORRECT(108_00_0007, "商品金额非法!"),
    ORDER_GET_GOODS_INFO_INCORRECT(108_00_0008, "获取额商品信息不正确!"),
    ORDER_GET_USER_ADDRESS_FAIL(108_00_0009, "获取用户地址失败!"),
    ORDER_GET_PAY_FAIL(108_00_0010, "调用pay失败!"),
    ORDER_NOT_USER_ORDER(108_00_0011, "不是该用户的订单!"),
    ORDER_UNABLE_CONFIRM_ORDER(108_00_0012, "状态不对不能确认订单!"),
    ORDER_CREATE_CART_IS_EMPTY(108_00_0013, "购物车无选中的商品，无法创建订单"),
    ORDER_STATUS_NOT_WAITING_PAYMENT(108_00_0014, "订单不处于等待支付状态"),
    ORDER_PAY_AMOUNT_ERROR(108_00_0015, "订单金额不正确"),

    // ========== 订单项 ==========
    ORDER_ITEM_ONLY_ONE(108_00_0200, "订单Item只有一个!"),
    ORDER_ITEM_SOME_NOT_EXISTS(108_00_0201, "有不存在的商品!"),

    // ========== 订单退货 ==========
    ORDER_RETURN_NO_RETURN_APPLY(108_00_0400, "未退货申请"),
    ORDER_RETURN_NOT_EXISTENT(108_00_0401, "退货订单不存在"),
    ORDER_RETURN_REFUND_FAILED(108_00_0402, "退款失败"),

    // ========== 购物车 ==========
    CARD_ITEM_NOT_FOUND(108_01_3000, "购物车项不存在"),
    CARD_ITEM_SKU_NOT_FOUND(108_01_3001, "商品不存在"),
    CARD_ITEM_SKU_QUANTITY_NOT_ENOUGH(108_01_3002, "商品库存不足"),

    // ========== 工具类服务 ==========
    DICT_SERVER_INVOKING_FAIL(108_02_4000, "字典服务调用失败!"),
    ORDER_LOGISTICS_INVOKING_FAIL(108_02_4001, "订单物流调用失败!"),
    ;

    private final Integer code;
    private final String msg;

    @Override
    public int getErrorCode() {
        return this.code;
    }

    @Override
    public String getErrorMsg() {
        return this.msg;
    }

}
