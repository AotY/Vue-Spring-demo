package com.xjtu.vo;

import com.xjtu.pojo.Carousel;

import java.util.List;

/**
 * Created by LeonTao on 2017/10/1.
 */
public class CarouselVo {
    private String imageHost;

    private List<Carousel> carousels;

    public String getImageHost() {
        return imageHost;
    }

    public void setImageHost(String imageHost) {
        this.imageHost = imageHost;
    }

    public List<Carousel> getCarousels() {
        return carousels;
    }

    public void setCarousels(List<Carousel> carousels) {
        this.carousels = carousels;
    }
}
