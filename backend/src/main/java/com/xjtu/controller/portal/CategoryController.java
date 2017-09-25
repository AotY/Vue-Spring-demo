package com.xjtu.controller.portal;

import com.xjtu.common.ServerResponse;
import com.xjtu.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by LeonTao on 2017/9/23.
 * user 商品 controller
 */
@Controller
@RequestMapping("/category/")
public class CategoryController {


    @Autowired
    private ICategoryService iCategoryService;

    /**
     * 获取子类别（一层）
     * @param categoryId
     * @return
     */
    @RequestMapping(value = "parallel.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse getChildrenParallelCategory(Integer categoryId) {
        return iCategoryService.getChildrenParallelCategory(categoryId);
    }
    
}
