package com.pakpobox.cleanpro.ui.mvp.model;

import com.pakpobox.cleanpro.model.AppSetting;
import com.pakpobox.cleanpro.model.GlobalData;
import com.pakpobox.cleanpro.model.net.HttpManager;

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

    public String getApiUrl(String apiAction, Object... params) {
        String url;
        url = GlobalData.API_HOST + GlobalData.API_PATH + apiAction;

        return params!=null && params.length!=0 ? String.format(url, params) : url;
    }
}
