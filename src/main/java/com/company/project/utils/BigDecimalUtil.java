package com.company.project.utils;

import java.math.BigDecimal;

/**
 * BigDecimal工具类
 * <p>
 * BigDecimal使用注意事项：
 * 1、new BigDecimal时，传入的0.1已经是浮点类型了，鉴于上面说的这个值只是近似值，在使用new BigDecimal时就把这个近似值完整的保留下来
 * 2、如果比较两个BigDecimal值的大小，采用其实现的compareTo方法；如果严格限制精度的比较，那么则可考虑使用equals方法。
 * 3、在使用BigDecimal进行（所有）运算时，一定要明确指定精度和舍入模式。
 * <p>
 * 1、在使用BigDecimal构造函数时，尽量传递字符串而非浮点类型；
 * 2、如果无法满足第一条，则可采用BigDecimal#valueOf方法来构造初始化值。
 * 3、虽然某些场景下推荐使用BigDecimal，它能够达到更好的精度，但性能相较于double和float，还是有一定的损失的，特别在处理庞大，复杂的运算时尤为明显。
 *
 * @author DanielQSL
 */
public class BigDecimalUtil {

    private BigDecimalUtil() {
    }

    public static BigDecimal add(double v1, double v2) {
        BigDecimal b1 = BigDecimal.valueOf(v1);
        BigDecimal b2 = BigDecimal.valueOf(v2);
        return b1.add(b2);
    }

    public static BigDecimal sub(double v1, double v2) {
        BigDecimal b1 = BigDecimal.valueOf(v1);
        BigDecimal b2 = BigDecimal.valueOf(v2);
        return b1.subtract(b2);
    }

    public static BigDecimal mul(double v1, double v2) {
        BigDecimal b1 = BigDecimal.valueOf(v1);
        BigDecimal b2 = BigDecimal.valueOf(v2);
        return b1.multiply(b2);
    }

    public static BigDecimal div(double v1, double v2) {
        BigDecimal b1 = BigDecimal.valueOf(v1);
        BigDecimal b2 = BigDecimal.valueOf(v2);
        // 四舍五入，保留2位小数
        // 必须设置精度
        return b1.divide(b2, 2, BigDecimal.ROUND_HALF_UP);
    }

    public static boolean equal(double v1, double v2) {
        BigDecimal b1 = BigDecimal.valueOf(v1);
        BigDecimal b2 = BigDecimal.valueOf(v2);
        return b1.compareTo(b2) == 0;
    }

    public static void main(String[] args) {
        BigDecimal a = BigDecimal.valueOf(35634535255456719.224352354356436);

        // 不使用任何科学计数法
        System.out.println(a.toPlainString());
        // 在必要的时候使用科学计数法
        System.out.println(a.toString());
        // 在必要的时候使用工程计数法
        System.out.println(a.toEngineeringString());
    }

}
