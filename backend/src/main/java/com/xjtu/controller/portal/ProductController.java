package com.xjtu.controller.portal;

import com.github.pagehelper.PageInfo;
import com.xjtu.common.ServerResponse;
import com.xjtu.service.IProductService;
import com.xjtu.service.impl.ProductServiceImpl;
import com.xjtu.vo.ProductDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by LeonTao on 2017/9/23.
 * user 商品 controller
 */
@Controller
@RequestMapping("/product/")
public class ProductController {


    @Autowired
    private IProductService iProductService;

    /**
     * 获取商品详情
     *
     * @param productId 商品id
     * @return
     */
    @RequestMapping(value = "detail.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<ProductDetailVo> detail(Integer productId) {
        return iProductService.getProductDetail(productId);
    }


    @RequestMapping(value = "list.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse getList(@RequestParam(value = "category", required = false) Integer categoryId,
                                  @RequestParam(value = "keyword", required = false) String keyword,
                                  @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                  @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                  @RequestParam(value = "orderBy", defaultValue = "") String orderBy)
    {
        return iProductService.getProductByCategoryKeyword(categoryId, keyword, pageNum, pageSize, orderBy);
    }


}
