package com.xjtu.controller.portal;

import com.xjtu.common.ServerResponse;
import com.xjtu.service.IRecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by LeonTao on 2017/9/28.
 */
@Controller
@RequestMapping("/recommend/")
public class RecommendController {


    @Autowired
    private IRecommendService iRecommendService;

    /**
     *
     * @return
     */
    @RequestMapping(value = "carousel.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse getCarousels() {

        return iRecommendService.getCarousels();
    }

    /**
     *
     * @return
     */
    @RequestMapping(value = "keyword.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse getKeywords() {
        return iRecommendService.getKeywords();
    }

}
