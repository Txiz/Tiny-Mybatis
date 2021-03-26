package com.xzx;

import com.xzx.entity.User;
import com.xzx.mapper.UserMapper;

/**
 * 作者: xzx
 * 创建时间: 2021-03-26-20-25
 **/
public class App {
    public static void main(String[] args) {
        MySqlSession sqlSession = new MySqlSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user = userMapper.getById("1");
        System.out.println("user = " + user.toString());
    }
}
