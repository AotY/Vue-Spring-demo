package com.xjtu.vo;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by LeonTao on 2017/9/29.
 */
public class CartVo {

    /**
     * 购物车列表
     */
    private List<CartProductVo> cartProductVoList;
    /**
     * 总价格
     */
    private BigDecimal cartTotalPrice;

    /**
     * 是否全选
     */
    private Boolean allChecked;
    private String imageHost;


    public CartVo() {
    }


    public List<CartProductVo> getCartProductVoList() {
        return cartProductVoList;
    }

    public void setCartProductVoList(List<CartProductVo> cartProductVoList) {
        this.cartProductVoList = cartProductVoList;
    }

    public BigDecimal getCartTotalPrice() {
        return cartTotalPrice;
    }

    public void setCartTotalPrice(BigDecimal cartTotalPrice) {
        this.cartTotalPrice = cartTotalPrice;
    }

    public Boolean getAllChecked() {
        return allChecked;
    }

    public void setAllChecked(Boolean allChecked) {
        this.allChecked = allChecked;
    }

    public String getImageHost() {
        return imageHost;
    }

    public void setImageHost(String imageHost) {
        this.imageHost = imageHost;
    }
}
