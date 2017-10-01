package com.xjtu.util;

import java.math.BigDecimal;

/**
 * Created by LeonTao on 2017/9/29.
 */
public class BigDecimalUtil {

    private BigDecimalUtil() {

    }


    public static BigDecimal add(double value1, double value2) {
        BigDecimal bigDecimal1 = new BigDecimal(Double.toString(value1));
        BigDecimal bigDecimal2 = new BigDecimal(Double.toString(value2));

        return bigDecimal1.add(bigDecimal2);
    }


    public static BigDecimal sub(double value1, double value2) {
        BigDecimal bigDecimal1 = new BigDecimal(Double.toString(value1));
        BigDecimal bigDecimal2 = new BigDecimal(Double.toString(value2));

        return bigDecimal1.subtract(bigDecimal2);
    }


    public static BigDecimal mul(double value1, double value2) {
        BigDecimal bigDecimal1 = new BigDecimal(Double.toString(value1));
        BigDecimal bigDecimal2 = new BigDecimal(Double.toString(value2));

        return bigDecimal1.multiply(bigDecimal2);
    }

    public static BigDecimal div(double value1, double value2) {
        BigDecimal bigDecimal1 = new BigDecimal(Double.toString(value1));
        BigDecimal bigDecimal2 = new BigDecimal(Double.toString(value2));

        // 四舍五入，保留2位小数点
        return bigDecimal1.divide(bigDecimal2, 2, BigDecimal.ROUND_HALF_UP);
    }


}











