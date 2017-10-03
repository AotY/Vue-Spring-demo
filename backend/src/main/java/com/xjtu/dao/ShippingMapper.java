package com.xjtu.dao;

import com.xjtu.pojo.Shipping;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShippingMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Shipping record);

    int insertSelective(Shipping record);

    Shipping selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Shipping record);

    int updateByPrimaryKey(Shipping record);

    int deleteByUserIdAndShippingId(@Param("userId") Integer userId, @Param("id") Integer id);

    Shipping selectByUserIdAndShippingId(@Param("userId") Integer userId, @Param("id") Integer id);

    List<Shipping> selectList(Integer userId);

    int setDefaultByUserIdAndId(@Param("userId") Integer userId, @Param("id") Integer id);

    int setUnDefaultByUserIdAndId(@Param("userId") Integer userId, @Param("id") Integer id);
}