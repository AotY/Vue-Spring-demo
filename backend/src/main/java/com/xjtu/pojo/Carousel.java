package com.xjtu.pojo;

import java.util.Date;

public class Carousel {

    private Integer id;

    private Integer productId;

    private String name;

    private String image;

    private Date createTime;

    private Date updateTime;

    public Carousel(Integer id, Integer productId, String name, String image, Date createTime, Date updateTime) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.image = image;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Carousel() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image == null ? null : image.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}