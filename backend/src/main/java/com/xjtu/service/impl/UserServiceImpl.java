package com.xjtu.service.impl;

import com.xjtu.common.Const;
import com.xjtu.common.ResponseCode;
import com.xjtu.common.ServerResponse;
import com.xjtu.dao.UserMapper;
import com.xjtu.pojo.User;
import com.xjtu.service.IUserService;
import com.xjtu.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by LeonTao on 2017/9/28.
 */
@Service("iUserService")
public class UserServiceImpl implements IUserService {


    @Autowired
    private UserMapper userMapper;

    /**
     * 真正实现用户登录的地方
     * @param username
     * @param password
     * @return
     */
    @Override
    public ServerResponse<User> login(String username, String password) {
        // 校验
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }


        // 查询数据库
        int count = userMapper.selectByUsername(username);

        // 用户名不存在
        if (count == 0) {
            return ServerResponse.createByError(Const.USERNOTEXISTS);
        }

        // 密码
        String encodePassword = MD5Util.MD5EncodeUTF8(password);

        User user = userMapper.selectLogin(username, encodePassword);
        if (user == null) {
            return ServerResponse.createByError(Const.USERNAMEORPASSWORDERROR);
        }

        // 不要返回密码
        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccess(user);
    }
}
