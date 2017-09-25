package com.xjtu.service;

import com.github.pagehelper.PageInfo;
import com.xjtu.common.ServerResponse;
import com.xjtu.vo.ProductDetailVo;

/**
 * Created by LeonTao on 2017/9/23.
 */
public interface IProductService {


    public ServerResponse<ProductDetailVo> getProductDetail(Integer productId);

    public ServerResponse<PageInfo> getProductList(int pageNum, int pageSize);

    public ServerResponse getProductByCategoryKeyword(Integer categoryId, String keyword, int pageNum, int pageSize, String orderBy);
}
