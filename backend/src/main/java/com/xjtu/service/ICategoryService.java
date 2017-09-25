package com.xjtu.service;

import com.github.pagehelper.PageInfo;
import com.xjtu.common.ServerResponse;
import com.xjtu.pojo.Category;
import com.xjtu.vo.CategoryRecommendListVo;
import com.xjtu.vo.CategoryVo;
import com.xjtu.vo.ProductDetailVo;

import java.util.List;

/**
 * Created by LeonTao on 2017/9/23.
 * 类别（category）服务
 */
public interface ICategoryService {


    public ServerResponse<List<CategoryRecommendListVo>> getFirstFloorCategory(Integer categoryId);

    public ServerResponse<List<Integer>> selectCategoryAndChildrenById(Integer categoryId);


}
