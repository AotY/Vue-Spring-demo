package com.xjtu.util;

import com.xjtu.pojo.Shipping;
import com.xjtu.vo.ShippingVo;

/**
 * Created by LeonTao on 2017/10/2.
 */
public class AssembleUtil {

    public static ShippingVo assembleShippingVo(Shipping shipping) {
        ShippingVo shippingVo = new ShippingVo();
        shippingVo.setId(shipping.getId());
        shippingVo.setReceiverName(shipping.getReceiverName());
        shippingVo.setReceiverAddress(shipping.getReceiverAddress());
        shippingVo.setReceiverCity(shipping.getReceiverCity());
        shippingVo.setReceiverDistrict(shipping.getReceiverDistrict());
        shippingVo.setReceiverMobile(shipping.getReceiverMobile());
        shippingVo.setReceiverPhone(shipping.getReceiverPhone());
        shippingVo.setReceiverProvince(shipping.getReceiverProvince());
        shippingVo.setReceiverZip(shipping.getReceiverZip());
        shippingVo.setDefault(shipping.getDefault());
        return shippingVo;
    }

}
