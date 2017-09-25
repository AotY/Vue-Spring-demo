package com.xjtu.vo;


import java.util.List;

/**
 * Created by LeonTao on 2017/9/23.
 */

public class CategoryRecommendListVo {

    private Integer id;

    private Integer parentId;

    private String name;

    private List<CategoryVo> categoryVoList;

    public CategoryRecommendListVo() {
    }

    public CategoryRecommendListVo(Integer id, Integer parentId, String name, List<CategoryVo> categoryVoList) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
        this.categoryVoList = categoryVoList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CategoryVo> getCategoryVoList() {
        return categoryVoList;
    }

    public void setCategoryVoList(List<CategoryVo> categoryVoList) {
        this.categoryVoList = categoryVoList;
    }
}
