package com.xjtu.service.impl;

import com.google.common.collect.Lists;
import com.xjtu.common.Const;
import com.xjtu.common.ResponseCode;
import com.xjtu.common.ServerResponse;
import com.xjtu.dao.ShippingMapper;
import com.xjtu.pojo.Shipping;
import com.xjtu.service.IShippingService;
import com.xjtu.util.AssembleUtil;
import com.xjtu.vo.ShippingVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by LeonTao on 2017/10/1.
 */
@Service("iShippingService")
public class ShippingServiceImpl implements IShippingService {

    @Autowired
    private ShippingMapper shippingMapper;

    /**
     * 删除
     *
     * @param userId
     * @param id
     * @return
     */
    @Override
    public ServerResponse delete(Integer userId, Integer id) {
        int affectedRows = 0;
        affectedRows = shippingMapper.deleteByUserIdAndShippingId(userId, id);
        if (affectedRows > 0) {
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError();
    }

    /**
     * 添加
     *
     * @param userId
     * @param shipping
     * @return
     */
    @Override
    public ServerResponse add(Integer userId, Shipping shipping) {
        if (shipping == null) {
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        // 设置userId
        shipping.setUserId(userId);
        shipping.setDefault(false);
        int affectedRows = shippingMapper.insertSelective(shipping);
        if (affectedRows > 0) {
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError();
    }

    /**
     * 更新
     * @param userId
     * @param shipping
     * @return
     */
    @Override
    public ServerResponse edit(Integer userId, Shipping shipping) {
        if (shipping == null) {
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        // 如果用户没有该地址信息
        Shipping oldShipping = shippingMapper.selectByUserIdAndShippingId(userId, shipping.getId());
        if (oldShipping == null) {
            return ServerResponse.createByError(Const.Shipping.No_Exists);
        }

        // 设置userId
        shipping.setUserId(userId);
        int affectedRows = shippingMapper.updateByPrimaryKeySelective(shipping);
        if (affectedRows > 0) {
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError();
    }

    /**
     * 获取地址列表
     * @param userId
     * @return
     */
    @Override
    public ServerResponse<List<ShippingVo>> list(Integer userId) {
        List<Shipping> shippingList = shippingMapper.selectList(userId);
        List<ShippingVo> shippingVoList = Lists.newArrayList();
        if (shippingList.size() > 0) {
            for (Shipping shipping: shippingList) {
                ShippingVo shippingVo = AssembleUtil.assembleShippingVo(shipping);
                shippingVoList.add(shippingVo);
            }
        }
        return ServerResponse.createBySuccess(shippingVoList);
    }

    /**
     * 设置默认地址
     * @param userId
     * @param id
     * @return
     */
    @Override
    public ServerResponse setDefault(Integer userId, Integer id) {
        if (id == null) {
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        // 如果用户没有该地址信息
        int affectedRows1 = 0;
        int affectedRows2 = 0;
        affectedRows1 = shippingMapper.setDefaultByUserIdAndId(userId, id);
        affectedRows2 = shippingMapper.setUnDefaultByUserIdAndId(userId, id);
        if (affectedRows1 > 0 && affectedRows2 > 0) {
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError();
    }

    @Override
    public ServerResponse<ShippingVo> selectShipping(Integer shippingId) {
        Shipping shipping = shippingMapper.selectByPrimaryKey(shippingId);
        if (shipping != null) {
            ShippingVo shippingVo = AssembleUtil.assembleShippingVo(shipping);
            return ServerResponse.createBySuccess(shippingVo);
        } else {
            return ServerResponse.createByError();
        }

    }

}
