package com.xjtu.service;

import com.xjtu.common.ServerResponse;

import java.util.Map;

/**
 * Created by LeonTao on 2017/10/2.
 */
public interface IOrderService {
    public ServerResponse createOrder(Integer userId, Integer shippingId);

    public ServerResponse pay(Integer userId, Long orderNo, String uploadPath);

    public ServerResponse alipayCallback(Map<String, String> params);

    public ServerResponse getOrderList(Integer userId, int pageNum, int pageSize);

    public ServerResponse getOrderDetail(Integer userId, Long orderNo);

    public ServerResponse cancelOrder(Integer userId, Long orderNo);
}
