package com.xzx;

import java.util.List;

/**
 * 作者: xzx
 * 创建时间: 2021-03-26-20-07
 **/
public class MyMapperBean {

    /**
     * 接口名称
     */
    private String interfaceName;

    /**
     * 接口下的所有方法
     */
    private List<MyMethod> myMethodList;

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public List<MyMethod> getMyMethodList() {
        return myMethodList;
    }

    public void setMyMethodList(List<MyMethod> myMethodList) {
        this.myMethodList = myMethodList;
    }
}
