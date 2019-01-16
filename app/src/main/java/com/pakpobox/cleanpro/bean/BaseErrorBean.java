package com.pakpobox.cleanpro.bean;

/**
 * 服务器响应数据实体基类
 * User:Sean.Wei
 * Date:2018/7/12
 * Time:17:28
 */

public class BaseErrorBean {
    private int statusCode; //服务器返回的错误码
    private String errorMessage; //服务器返回的成功或失败的提示

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
