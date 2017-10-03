package com.xjtu.service;

import com.xjtu.common.ServerResponse;
import com.xjtu.pojo.Shipping;
import com.xjtu.vo.ShippingVo;

import java.util.List;

/**
 * Created by LeonTao on 2017/10/1.
 */
public interface IShippingService {
    public ServerResponse delete(Integer userId, Integer id);

    public ServerResponse add(Integer userId, Shipping shipping);

    public ServerResponse edit(Integer userId, Shipping shipping);

    public ServerResponse<List<ShippingVo>> list(Integer userId);

    public ServerResponse setDefault(Integer userId, Integer id);

    public ServerResponse<ShippingVo> selectShipping(Integer shippingId);


}
