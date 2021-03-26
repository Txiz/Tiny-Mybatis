package com.xzx;

import com.xzx.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 作者: xzx
 * 创建时间: 2021-03-26-20-17
 **/
public class MyExecutorImpl  implements MyExecutor {

    private MyConfiguration xmlConfiguration = new MyConfiguration();

    @Override
    public <T> T query(String sql, Object parameter) {
        Connection connection = getConnection();
        ResultSet set = null;
        PreparedStatement pre = null;
        try {
            pre = connection.prepareStatement(sql);
            //设置参数
            pre.setString(1, parameter.toString());
            set = pre.executeQuery();
            User u = new User();
            //遍历结果集
            while (set.next()) {
                u.setId(set.getString(1));
                u.setUsername(set.getString(2));
                u.setPassword(set.getString(3));
            }
            return (T) u;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (set != null) {
                    set.close();
                }
                if (pre != null) {
                    pre.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return null;
    }

    private Connection getConnection() {
        try {
            return xmlConfiguration.build("config.xml");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
