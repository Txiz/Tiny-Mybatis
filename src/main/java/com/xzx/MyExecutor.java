package com.xzx;

/**
 * 作者: xzx
 * 创建时间: 2021-03-26-20-16
 **/
public interface MyExecutor {
    <T> T query(String statement, Object parameter);
}
