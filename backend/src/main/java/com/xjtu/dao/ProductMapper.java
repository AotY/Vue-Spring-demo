package com.xjtu.dao;

import com.xjtu.pojo.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

    List<Product> selectList();

    List<Product> selectByKeywordAndCategoryIdList(@Param("categoryIdList") List<Integer> categoryIdList, @Param("keyword") String keyword);
}