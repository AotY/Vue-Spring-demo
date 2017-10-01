package com.xjtu.dao;

import com.xjtu.pojo.Keyword;

import java.util.List;

public interface KeywordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Keyword record);

    int insertSelective(Keyword record);

    Keyword selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Keyword record);

    int updateByPrimaryKey(Keyword record);

    List<String> selectByLimit(Integer count);
}