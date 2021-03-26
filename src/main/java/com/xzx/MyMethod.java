package com.xzx;

/**
 * 作者: xzx
 * 创建时间: 2021-03-26-20-08
 **/
public class MyMethod {

    /**
     * sql类型
     */
    private String sqlType;
    /**
     * 方法名称
     */
    private String methodName;
    /**
     * sql语句
     */
    private String sql;
    /**
     * 返回类型
     */
    private Object resultType;
    /**
     * 参数类型
     */
    private String parameterType;

    public String getSqlType() {
        return sqlType;
    }

    public void setSqlType(String sqlType) {
        this.sqlType = sqlType;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public Object getResultType() {
        return resultType;
    }

    public void setResultType(Object resultType) {
        this.resultType = resultType;
    }

    public String getParameterType() {
        return parameterType;
    }

    public void setParameterType(String parameterType) {
        this.parameterType = parameterType;
    }
}
