package com.pakpobox.cleanpro.ui.logon.setpsw;

import com.google.gson.Gson;
import com.pakpobox.cleanpro.bean.Register;
import com.pakpobox.cleanpro.bean.Result;
import com.pakpobox.cleanpro.bean.UserBean;
import com.pakpobox.cleanpro.common.UrlConstainer;
import com.pakpobox.cleanpro.net.callback.NetCallback;
import com.pakpobox.cleanpro.ui.mvp.model.BaseModel;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * User:Sean.Wei
 * Date:2018/7/25
 * Time:17:36
 */

public class SetPSWModel extends BaseModel implements SetPSWContract.ISetPSWModel {
    @Override
    public void register(Register register, NetCallback<UserBean> callback) {
        String requestStr = new Gson().toJson(register);
        postRequest(getApiUrl(UrlConstainer.REGISTER), requestStr, callback);
    }

}

