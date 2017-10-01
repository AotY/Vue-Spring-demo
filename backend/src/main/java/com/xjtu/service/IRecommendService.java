package com.xjtu.service;

import com.xjtu.common.ServerResponse;

/**
 * Created by LeonTao on 2017/10/1.
 */
public interface IRecommendService {
    public ServerResponse getCarousels();
    public ServerResponse getKeywords();
}
