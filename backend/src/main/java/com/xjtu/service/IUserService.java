package com.xjtu.service;

import com.xjtu.common.ServerResponse;
import com.xjtu.pojo.User;
import com.xjtu.vo.CategoryRecommendListVo;

import java.util.List;

/**
 * Created by LeonTao on 2017/9/23.
 * 类别（category）服务
 */
public interface IUserService {


    public ServerResponse<User> login(String username, String password);


}
