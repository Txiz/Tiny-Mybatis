package com.xzx;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 读取与解析配置信息，并返回处理后的环境
 * 作者: xzx
 * 创建时间: 2021-03-26-19-51
 **/
public class MyConfiguration {
    private static final ClassLoader loader = ClassLoader.getSystemClassLoader();

    public Connection build(String resource) {
        try {
            InputStream stream = loader.getResourceAsStream(resource);
            SAXReader reader = new SAXReader();
            Document document = reader.read(stream);
            Element root = document.getRootElement();
            return evalDataSource(root);
        } catch (Exception e) {
            throw new RuntimeException("XML文件：" + resource + " 解析异常");
        }
    }

    private Connection evalDataSource(Element node) throws ClassNotFoundException {
        if (!node.getName().equals("database")) {
            throw new RuntimeException("root should be <database>");
        }
        String driverClassName = null;
        String url = null;
        String username = null;
        String password = null;
        //获取属性节点
        for (Object item : node.elements("property")) {
            Element i = (Element) item;
            String value = getValue(i);
            String name = i.attributeValue("name");
            if (name == null || value == null) {
                throw new RuntimeException("[database]: <property> should contain name and value");
            }
            //赋值
            switch (name) {
                case "url":
                    url = value;
                    break;
                case "username":
                    username = value;
                    break;
                case "password":
                    password = value;
                    break;
                case "driverClassName":
                    driverClassName = value;
                    break;
                default:
                    throw new RuntimeException("[database]: <property> unknown name");
            }
        }
        Class.forName(driverClassName);
        Connection connection = null;
        try {
            //建立数据库链接
            assert url != null;
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    //获取property属性的值,如果有value值,则读取 没有设置value,则读取内容
    private String getValue(Element node) {
        return node.hasContent() ? node.getText() : node.attributeValue("value");
    }


    @SuppressWarnings("rawtypes")
    public MyMapperBean readMapper(String path) {
        MyMapperBean mapper = new MyMapperBean();
        try {
            InputStream stream = loader.getResourceAsStream(path);
            SAXReader reader = new SAXReader();
            Document document = reader.read(stream);
            Element root = document.getRootElement();
            mapper.setInterfaceName(root.attributeValue("nameSpace").trim()); //把mapper节点的nameSpace值存为接口名
            List<MyMethod> list = new ArrayList<MyMethod>(); //用来存储方法的List
            for (Iterator rootIter = root.elementIterator(); rootIter.hasNext(); ) {//遍历根节点下所有子节点
                MyMethod fun = new MyMethod();    //用来存储一条方法的信息
                Element e = (Element) rootIter.next();
                String sqlType = e.getName().trim();
                String methodName = e.attributeValue("id").trim();
                String sql = e.getText().trim();
                String resultType = e.attributeValue("resultType").trim();
                fun.setSqlType(sqlType);
                fun.setMethodName(methodName);
                Object newInstance = null;
                try {
                    newInstance = Class.forName(resultType).newInstance();
                } catch (InstantiationException | IllegalAccessException | ClassNotFoundException exception) {
                    exception.printStackTrace();
                }
                fun.setResultType(newInstance);
                fun.setSql(sql);
                list.add(fun);
            }
            mapper.setMyMethodList(list);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return mapper;
    }
}
