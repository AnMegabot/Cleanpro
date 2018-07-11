package com.pakpobox.cleanpro.bean;

import java.util.List;

/**
 * 响应数据实体类
 * User:Sean.Wei
 * Date:2018/3/12
 * Time:12:02
 */

public class ResponseDataBean<T> {
    private int StatusCode = -999;
    private String Message;
    private T Result;
    private List<T> ResultList;

    public int getStatusCode() {
        return StatusCode;
    }

    public void setStatusCode(int statusCode) {
        StatusCode = statusCode;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public T getResult() {
        return Result;
    }

    public void setResult(T result) {
        Result = result;
    }

    public List<T> getResultList() {
        return ResultList;
    }

    public void setResultList(List<T> resultList) {
        ResultList = resultList;
    }
}
