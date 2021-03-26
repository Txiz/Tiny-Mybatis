package com.xzx;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 作者: xzx
 * 创建时间: 2021-03-26-20-19
 **/
public class MyMapperProxy implements InvocationHandler {
    private MySqlSession mySqlSession;

    private MyConfiguration myConfiguration;

    public MyMapperProxy(MyConfiguration myConfiguration, MySqlSession mySqlSession) {
        this.myConfiguration = myConfiguration;
        this.mySqlSession = mySqlSession;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        MyMapperBean readMapper = myConfiguration.readMapper("UserMapper.xml");
        //是否是xml文件对应的接口
        if (!method.getDeclaringClass().getName().equals(readMapper.getInterfaceName())) {
            return null;
        }
        List<MyMethod> list = readMapper.getMyMethodList();
        if (null != list || 0 != list.size()) {
            for (MyMethod myMethod : list) {
                //id是否和接口方法名一样
                if (method.getName().equals(myMethod.getMethodName())) {
                    return mySqlSession.selectOne(myMethod.getSql(), String.valueOf(args[0]));
                }
            }
        }
        return null;
    }
}
