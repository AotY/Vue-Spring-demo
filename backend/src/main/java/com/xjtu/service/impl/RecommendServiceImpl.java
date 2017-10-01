package com.xjtu.service.impl;

import com.xjtu.common.Const;
import com.xjtu.common.ServerResponse;
import com.xjtu.dao.CarouselMapper;
import com.xjtu.dao.KeywordMapper;
import com.xjtu.pojo.Carousel;
import com.xjtu.service.IRecommendService;
import com.xjtu.util.PropertiesUtil;
import com.xjtu.vo.CarouselVo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by LeonTao on 2017/10/1.
 */
@Service("iRecommendService")
public class RecommendServiceImpl implements IRecommendService {

    @Autowired
    private CarouselMapper carouselMapper;

    @Autowired
    private KeywordMapper keywordMapper;


    /**
     * 获取推荐大图
     * @return
     */
    @Override
    public ServerResponse getCarousels() {
        // 默认返回数
        List<Carousel> carouselList = carouselMapper.selectList();
        CarouselVo carouselVo = new CarouselVo();
        carouselVo.setCarousels(carouselList);
        carouselVo.setImageHost(PropertiesUtil.getProperty(Const.FTP_SERVER_HTTP_PREFIX));
        return ServerResponse.createBySuccess(carouselVo);
    }

    /**
     * 获取查询建议词
     * @return
     */
    @Override
    public ServerResponse getKeywords() {
        Integer limit = Const.KEYWORDS_LIMIT;
        List<String> keywords = keywordMapper.selectByLimit(limit);

        return ServerResponse.createBySuccess(keywords);
    }
}
