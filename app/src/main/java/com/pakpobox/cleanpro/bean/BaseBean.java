package com.pakpobox.cleanpro.bean;

import java.util.List;

/**
 * 服务器响应数据实体基类
 * User:Sean.Wei
 * Date:2018/7/12
 * Time:17:28
 */

public class BaseBean<T> {
    /**
     * 服务器返回的错误码
     */
    public int StatusCode;
    /**
     * 服务器返回的成功或失败的提示
     */
    public String Message;
    /**
     * 服务器返回的数据
     */
    public T Result;

    /**
     * 服务器返回的数据列表
     */
    public List<T> ResultList;

    public BaseBean(int statusCode, String message, T result, List<T> resultList) {
        StatusCode = statusCode;
        Message = message;
        Result = result;
        ResultList = resultList;
    }

    @Override
    public String toString() {
        return "BaseBean{" +
                "StatusCode=" + StatusCode +
                ", Message='" + Message + '\'' +
                ", Result=" + Result +
                ", ResultList=" + ResultList +
                '}';
    }
}
