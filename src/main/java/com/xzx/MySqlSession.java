package com.xzx;

import java.lang.reflect.Proxy;

/**
 * 作者: xzx
 * 创建时间: 2021-03-26-20-15
 **/
public class MySqlSession {
    private MyExecutor executor = new MyExecutorImpl();
    private MyConfiguration myConfiguration = new MyConfiguration();

    public <T> T selectOne(String statement, Object parameter) {
        return executor.query(statement, parameter);
    }

    @SuppressWarnings("unchecked")
    public <T> T getMapper(Class<T> clas) {
        //动态代理调用
        return (T) Proxy.newProxyInstance(clas.getClassLoader(), new Class[]{clas},
                new MyMapperProxy(myConfiguration, this));
    }
}
