package com.xjtu.util;

import java.util.Random;

/**
 * Created by LeonTao on 2017/10/2.
 */
public class OrderUtil {

    /**
     * 订单号生成
     * @return
     */
    public static Long generateOrderNo() {
        return System.currentTimeMillis() + new Random().nextInt(100);
    }
}
