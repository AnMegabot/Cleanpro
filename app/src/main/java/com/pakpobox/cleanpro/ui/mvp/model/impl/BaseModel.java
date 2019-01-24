package com.pakpobox.cleanpro.ui.mvp.model.impl;

import android.text.TextUtils;

import com.pakpobox.cleanpro.application.AppSetting;
import com.pakpobox.cleanpro.common.UrlConstainer;
import com.pakpobox.cleanpro.net.HttpManager;
import com.pakpobox.cleanpro.net.callback.INetCallback;
import com.pakpobox.cleanpro.ui.mvp.model.IModel;

import java.util.HashMap;

/**
 * User:Sean.Wei
 * Date:2018/7/20
 * Time:10:04
 */

public class BaseModel implements IModel {
    private HttpManager httpManager;

    public BaseModel() {
        httpManager = new HttpManager();
    }

    @Override
    public HttpManager getHttpRequest() {
        return httpManager;
    }

    @Override
    public void postRequest(String url, String postStr, INetCallback callback) {
        postRequest(url, null, postStr, callback);
    }

    @Override
    public void postRequest(String url, HashMap<String, String> headerValues, String postStr, INetCallback callback) {
        if (null == httpManager)
            return;
        httpManager.asyncPostStringByHttp(url, headerValues, postStr, callback);
    }

    @Override
    public void getRequest(String url, INetCallback callback) {
        getRequest(url, null, callback);
    }

    @Override
    public void getRequest(String url, HashMap<String, String> headerValues, INetCallback callback) {
        if (null == httpManager)
            return;
        httpManager.asyncGetDataByHttp(url, headerValues, callback);
    }

    public String getApiUrl(String apiAction, Object... params) {
        String url;
        url = UrlConstainer.BASE_URL + UrlConstainer.API_PATH + apiAction;

        return params!=null && params.length!=0 ? String.format(url, params) : url;
    }

    /**
     * 获取基础请求头
     * @return
     */
    public HashMap<String, String> getBaseHttpHeader(){

        HashMap<String, String> headerMap = new HashMap<>();
        if (!TextUtils.isEmpty(AppSetting.getLoginToken()))
            headerMap.put("userToken", AppSetting.getLoginToken());

        return headerMap;
    }
}
