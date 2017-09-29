package com.xjtu.service;

import com.xjtu.common.ServerResponse;
import com.xjtu.pojo.Cart;
import com.xjtu.pojo.User;
import com.xjtu.vo.CartVo;

import java.util.List;

/**
 * Created by LeonTao on 2017/9/23.
 * 类别（category）服务
 */
public interface ICartService {


    public ServerResponse<Integer> count(Integer userId);

    public ServerResponse<CartVo> list(Integer userId);

    public ServerResponse add(Integer userId, Integer productId, Integer quantity, Boolean checked);

    public ServerResponse reverseAllSelect(Integer userId, Boolean checked);

    public ServerResponse reverseSelect(Integer userId, Integer productId, Boolean checked);

    public ServerResponse changeQuantity(Integer userId, Integer productId, Integer quantity);
}
