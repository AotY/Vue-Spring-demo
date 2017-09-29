package com.xjtu.controller.portal;

import com.xjtu.common.Const;
import com.xjtu.common.ResponseCode;
import com.xjtu.common.ServerResponse;
import com.xjtu.pojo.Cart;
import com.xjtu.pojo.User;
import com.xjtu.service.ICartService;
import com.xjtu.service.IUserService;
import com.xjtu.vo.CartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by LeonTao on 2017/9/28.
 */
@Controller
@RequestMapping("/cart/")
public class CartController {


    @Autowired
    private ICartService iCartService;

    /**
     * 当前用户购物车数量
     * @param session 用户session
     * @return
     */
    @RequestMapping(value = "count.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<Integer> count(HttpSession session) {
        User user = (User)session.getAttribute(Const.CURRENTUSER);
        if (user == null) {
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.count(user.getId());
    }



    @RequestMapping(value = "list.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<CartVo> logout(HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENTUSER);
        if (user == null) {
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.list(user.getId());
    }

    @RequestMapping(value = "add.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse add(Integer productId, Integer quantity, Boolean checked, HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENTUSER);
        if (user == null) {
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.add(user.getId(), productId, quantity, checked);

    }

    @RequestMapping(value = "reverse_select_all.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse unSelectAll(Boolean checked, HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENTUSER);
        if (user == null) {
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.reverseAllSelect(user.getId(), checked);
    }

    @RequestMapping(value = "reverse_select.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse selectAll(Integer productId, Boolean checked, HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENTUSER);
        if (user == null) {
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.reverseSelect(user.getId(), productId, checked);
    }

    /**
     *
     * @param productId
     * @param session
     * @return
     */
    @RequestMapping(value = "change_quantity.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse changeQuantity(Integer productId, Integer quantity, HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENTUSER);
        if (user == null) {
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.changeQuantity(user.getId(), productId, quantity);
    }

}
