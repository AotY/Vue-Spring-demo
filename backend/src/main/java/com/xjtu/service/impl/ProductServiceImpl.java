package com.xjtu.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.xjtu.common.Const;
import com.xjtu.common.ResponseCode;
import com.xjtu.common.ServerResponse;
import com.xjtu.dao.CategoryMapper;
import com.xjtu.dao.ProductMapper;
import com.xjtu.pojo.Category;
import com.xjtu.pojo.Product;
import com.xjtu.service.ICategoryService;
import com.xjtu.util.DateTimeUtil;
import com.xjtu.util.PropertiesUtil;
import com.xjtu.vo.ProductDetailVo;
import com.xjtu.vo.ProductListVo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by LeonTao on 2017/9/23.
 * 现在先实现最简单的返回商品列表功能
 */
@Service("iProductService")
public class ProductServiceImpl implements com.xjtu.service.IProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ICategoryService iCategoryService;

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 获取商品详情
     * @param productId
     * @return
     */
    @Override
    public ServerResponse<ProductDetailVo> getProductDetail(Integer productId) {
        if (productId == null){
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Product product = productMapper.selectByPrimaryKey(productId);
        if (product == null){
            return ServerResponse.createByError(Const.PRODUCTDELETED);
        }
        if (product.getStatus() != Const.ProductStatusEnum.ONSLAE.getStatus()){
            return ServerResponse.createByError(Const.PRODUCTNOTONSALE);
        }

        ProductDetailVo productDetailVo = assembleProductDetailVo(product);

        return ServerResponse.createBySuccess(productDetailVo);
    }

    /**
     * 组装ProductDetailVo对象
     * @param product
     * @return
     */
    private ProductDetailVo assembleProductDetailVo(Product product) {
        ProductDetailVo productDetailVo = new ProductDetailVo();
        productDetailVo.setId(product.getId());
        productDetailVo.setSubtitle(product.getSubtitle());
        productDetailVo.setPrice(product.getPrice());
        productDetailVo.setMainImage(product.getMainImage());
        productDetailVo.setSubImages(Lists.newArrayList(product.getSubImages().split(",")));
        productDetailVo.setCategoryId(product.getCategoryId());
        productDetailVo.setDetail(product.getDetail());
        productDetailVo.setName(product.getName());
        productDetailVo.setRate(product.getRate());
        productDetailVo.setStatus(product.getStatus());
        productDetailVo.setStock(product.getStock());

        productDetailVo.setImageHost(PropertiesUtil.getProperty(Const.FTPSERVERHTTPPREFIX,""));

        //TODO 设置商品父类别
        productDetailVo.setParentCategoryId(new Integer(0));

        productDetailVo.setCreateTime(DateTimeUtil.dateToStr(product.getCreateTime()));
        productDetailVo.setUpdateTime(DateTimeUtil.dateToStr(product.getUpdateTime()));
        return productDetailVo;
    }


    /**
     * 获取商品列表
     * @return
     */
    @Override
    public ServerResponse<PageInfo> getProductList(int pageNum, int pageSize) {
        /*
         * startPage--start
         * 填充自己的sql查询逻辑
         * pageHelper-收尾
         */
        PageHelper.startPage(pageNum, pageSize);

        List<Product> productList = productMapper.selectList();

        List<ProductListVo> productListVos = Lists.newArrayList();
        for (Product product: productList) {
            ProductListVo productListVo = assembleProductListVo(product);
            productListVos.add(productListVo);
        }

//        PageInfo pageInfo = new PageInfo();
        PageInfo pageInfo = new PageInfo(productList); //这个传递对象，pageInfo会计算page, size等属性
        pageInfo.setList(productListVos);

        return ServerResponse.createBySuccess(pageInfo);
    }

    /**
     * 根据categoryId和keyword查找类别和产品
     * @param categoryId
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @param orderBy
     * @return
     */
    @Override
    public ServerResponse getProductByCategoryKeyword(Integer categoryId, String keyword, int pageNum, int pageSize, String orderBy) {
        if (categoryId == null && StringUtils.isBlank(keyword)) {
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }

        List<Integer> categoryIdList = null;
        // 如果查找类别不为空
        if (categoryId != null) {
            Category category = categoryMapper.selectByPrimaryKey(categoryId);
            // 如果没有当前类别，同时没有提供关键词查找，这个时候返回一个空的结果集,不报错
            if (category == null && StringUtils.isBlank(keyword)) {
                PageHelper.startPage(pageNum, pageSize);
                List<ProductListVo> productListVos = Lists.newArrayList();
                PageInfo pageInfo = new PageInfo(productListVos);
                return ServerResponse.createBySuccess(productListVos);
            }
            // 如果当前类别不为空，则查找子类别，获得满足查找条件的categoryId
            categoryIdList = iCategoryService.selectCategoryAndChildrenById(categoryId).getData();
        }

        // 如果搜索关键词不为空，拼接查找条件
        if (StringUtils.isNoneBlank(keyword)) {
            keyword = new StringBuilder().append("%").append(keyword).append("%").toString();
        }

        PageHelper.startPage(pageNum, pageSize);

        // 排序处理
        if (StringUtils.isNoneBlank(orderBy)) {
            if (Const.ProductListOrderBy.PRICE_ASC_DESC.contains(orderBy)) {
                String[] fieldAndOrder = orderBy.split("_");
                PageHelper.orderBy(fieldAndOrder[0] + " " + fieldAndOrder[1]);
            }
        }


        categoryIdList = CollectionUtils.isEmpty(categoryIdList) ? null : categoryIdList;
        keyword = StringUtils.isBlank(keyword) ? null : keyword;
        List<Product> products = productMapper.selectByKeywordAndCategoryIdList(categoryIdList, keyword);


        List<ProductListVo> productListVoList = Lists.newArrayList();
        for (Product product: products) {
            ProductListVo productListVo = assembleProductListVo(product);
            productListVoList.add(productListVo);
        }
        PageInfo pageInfo = new PageInfo(products);
        pageInfo.setList(productListVoList);

        return ServerResponse.createBySuccess(pageInfo);
    }

    /**
     * 组装ProductListVo对象
     * @param product
     * @return
     */
    private ProductListVo assembleProductListVo(Product product) {
        ProductListVo productListVo = new ProductListVo();
        productListVo.setId(product.getId());
        productListVo.setName(product.getName());
        productListVo.setCategoryId(product.getCategoryId());
        productListVo.setImageHost(PropertiesUtil.getProperty(Const.FTPSERVERHTTPPREFIX,""));
        productListVo.setMainImage(product.getMainImage());
        productListVo.setPrice(product.getPrice());
        productListVo.setSubtitle(product.getSubtitle());
        productListVo.setRate(product.getRate());
        productListVo.setStatus(product.getStatus());
        return productListVo;
    }

}

