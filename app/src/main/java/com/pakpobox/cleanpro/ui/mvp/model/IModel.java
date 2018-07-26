package com.pakpobox.cleanpro.ui.mvp.model;

import com.pakpobox.cleanpro.net.HttpManager;
import com.pakpobox.cleanpro.net.callback.BaseNetCallback;

import java.util.HashMap;

/**
 * User:Sean.Wei
 * Date:2018/6/21
 * Time:17:10
 */

public interface IModel {
    /**
     * 使用HttpManager请求数据
     * @return
     */
    HttpManager getHttpRequest();

    /**
     * post请求
     * @param url
     * @param postStr
     * @param callback
     */
    void postRequest(String url, String postStr, BaseNetCallback callback);

    /**
     * post请求
     * @param url
     * @param headerValues
     * @param postStr
     * @param callback
     */
    void postRequest(String url, HashMap<String, String> headerValues, String postStr, BaseNetCallback callback);

    /**
     * Get请求
     * @param url
     * @param callback
     */
    void getRequest(String url, BaseNetCallback callback);

    /**
     * Get请求
     * @param url
     * @param headerValues
     * @param callback
     */
    void getRequest(String url, HashMap<String, String> headerValues, BaseNetCallback callback);
}
