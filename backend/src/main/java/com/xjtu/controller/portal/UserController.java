package com.xjtu.controller.portal;

import com.xjtu.common.Const;
import com.xjtu.common.ServerResponse;
import com.xjtu.pojo.User;
import com.xjtu.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by LeonTao on 2017/9/28.
 */
@Controller
@RequestMapping("/user/")
public class UserController {


    @Autowired
    private IUserService iUserService;

    /**
     * 用户登录
     *
     * @param username
     * @param password
     * @param session 保存用户session
     * @return
     */
    // @RequestParam(value = "username", required = true)
    // @RequestParam(value = "password", required = true)
    @RequestMapping(value = "login.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> login(String username, String password, HttpSession session) {
        ServerResponse<User> response = iUserService.login(username, password);
        if (response.isSuccess()) {
            session.setAttribute(Const.CURRENTUSER, response.getData());
//            User user = (User) session.getAttribute(Const.CURRENTUSER);
//            Integer id = user.getId();
        }
        return response;
    }

    @RequestMapping(value = "logout.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse logout(HttpSession session) {
        session.removeAttribute(Const.CURRENTUSER);
        return ServerResponse.createBySuccess();
    }

}
