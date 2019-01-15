package com.pakpobox.cleanpro.ui.mvp.model;

import com.pakpobox.cleanpro.net.HttpManager;
import com.pakpobox.cleanpro.net.callback.INetCallback;

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
    void postRequest(String url, String postStr, INetCallback callback);

    /**
     * post请求
     * @param url
     * @param headerValues
     * @param postStr
     * @param callback
     */
    void postRequest(String url, HashMap<String, String> headerValues, String postStr, INetCallback callback);

    /**
     * Get请求
     * @param url
     * @param callback
     */
    void getRequest(String url, INetCallback callback);

    /**
     * Get请求
     * @param url
     * @param headerValues
     * @param callback
     */
    void getRequest(String url, HashMap<String, String> headerValues, INetCallback callback);
}
