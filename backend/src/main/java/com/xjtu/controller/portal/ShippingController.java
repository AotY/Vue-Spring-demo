package com.xjtu.controller.portal;

import com.xjtu.common.Const;
import com.xjtu.common.ResponseCode;
import com.xjtu.common.ServerResponse;
import com.xjtu.pojo.Shipping;
import com.xjtu.pojo.User;
import com.xjtu.service.ICartService;
import com.xjtu.service.IShippingService;
import com.xjtu.vo.CartVo;
import com.xjtu.vo.ShippingVo;
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
@RequestMapping("/shipping/")
public class ShippingController {

    @Autowired
    private IShippingService iShippingService;

    /**
     * 收货地址列表
     * @param session 用户session
     * @return
     */
    @RequestMapping(value = "list.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<List<ShippingVo>> count(HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iShippingService.list(user.getId());
    }

    /**
     * 添加地址
     * @param shipping
     * @param session
     * @return
     */
    @RequestMapping(value = "add.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse add(Shipping shipping, HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iShippingService.add(user.getId(), shipping);

    }

    /**
     * 编辑地址
     * @param shipping
     * @param session
     * @return
     */
    @RequestMapping(value = "edit.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse selectAll(Shipping shipping, HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iShippingService.edit(user.getId(), shipping);
    }

    /**
     * 删除地址
     */
    @RequestMapping(value = "delete.do", method = RequestMethod.DELETE)
    @ResponseBody
    public ServerResponse delete(Integer id, HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iShippingService.delete(user.getId(), id);
    }


    /**
     * 设置默认地址
     * @return
     */
    @RequestMapping(value = "set_default.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse setDefault(Integer id, HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iShippingService.setDefault(user.getId(), id);
    }



}
