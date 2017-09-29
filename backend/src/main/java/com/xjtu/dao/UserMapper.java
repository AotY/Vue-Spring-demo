package com.xjtu.dao;

import com.xjtu.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int selectByUsername(String username);

    int selectByEmail(String email);

    User selectLogin(@Param("username") String username, @Param("password") String password);
}