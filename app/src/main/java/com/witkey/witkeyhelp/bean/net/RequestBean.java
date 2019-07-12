package com.witkey.witkeyhelp.bean.net;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/3/6.
 */

public class RequestBean implements Serializable {
    private String methodName;//方法名
    private String errorStr;//错误信息
    private String className;

    public String getErrorStr() {
        return errorStr;
    }

    public void setErrorStr(String errorStr) {
        this.errorStr = errorStr;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {

        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    @Override
    public String toString() {
        return "RequestBean{" +
                "methodName='" + methodName + '\'' +
                ", errorStr='" + errorStr + '\'' +
                ", className='" + className + '\'' +
                '}';
    }

    public RequestBean(String methodName, String errorStr, String className) {
        this.methodName = methodName;
        this.errorStr = errorStr;
        this.className = className;
    }

    public RequestBean() {
    }
}
