package com.pakpobox.cleanpro.ui.mvp.model;

import com.pakpobox.cleanpro.application.AppSetting;
import com.pakpobox.cleanpro.common.UrlConstainer;
import com.pakpobox.cleanpro.net.HttpManager;
import com.pakpobox.cleanpro.net.callback.BaseNetCallback;

import java.util.HashMap;

/**
 * User:Sean.Wei
 * Date:2018/7/20
 * Time:10:04
 */

public class BaseModel implements IModel{
    @Override
    public HttpManager getHttpRequest() {
        return AppSetting.getInstance().getHttpManager();
    }

    @Override
    public void postRequest(String url, String postStr, BaseNetCallback callback) {
        postRequest(url, null, postStr, callback);
    }

    @Override
    public void postRequest(String url, HashMap<String, String> headerValues, String postStr, BaseNetCallback callback) {
        getHttpRequest().asyncPostStringByHttp(url, headerValues, postStr, callback);

        if (null != callback && null!=callback.getPresenter())
            callback.getPresenter().addDisposable(url);
    }

    @Override
    public void getRequest(String url, BaseNetCallback callback) {
        getRequest(url, null, callback);
    }

    @Override
    public void getRequest(String url, HashMap<String, String> headerValues, BaseNetCallback callback) {
        getHttpRequest().asyncGetDataByHttp(url, headerValues, callback);

        if (null != callback && null!=callback.getPresenter())
            callback.getPresenter().addDisposable(url);
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
        if (null!=AppSetting.getUserInfo())
            headerMap.put("userToken", AppSetting.getUserInfo().getToken());

        return headerMap;
    }
}
