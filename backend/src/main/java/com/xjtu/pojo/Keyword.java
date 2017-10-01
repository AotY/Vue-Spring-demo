package com.xjtu.pojo;

import java.util.Date;

public class Keyword {
    private Integer id;

    private String keyword;

    private Integer count;

    private Date createTime;

    private Date updateTime;

    public Keyword(Integer id, String keyword, Integer count, Date createTime, Date updateTime) {
        this.id = id;
        this.keyword = keyword;
        this.count = count;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Keyword() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword == null ? null : keyword.trim();
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
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