package com.xjtu.dao;

import com.xjtu.pojo.Cart;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CartMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Cart record);

    int insertSelective(Cart record);

    Cart selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Cart record);

    int updateByPrimaryKey(Cart record);

    Integer selectQuantitySumByUserId(Integer userId);

    List<Cart> selectListByUserId(Integer userId);

    List<Cart> selectedListByUserId(Integer userId);

    Cart selectByUserIdAndProductId(@Param("userId") Integer userId, @Param("productId") Integer productId);

    int reverseAllSelect(@Param("userId") Integer userId, @Param("checked") Boolean checked);

    int reverseSelect(@Param("userId") Integer userId, @Param("productId") Integer productId, @Param("checked") Boolean checked);

    int deleteByUserIdAndProductId(@Param("userId") Integer userId, @Param("productId") Integer productId);

    int deleteSelectedListByUserId(Integer userId);
}