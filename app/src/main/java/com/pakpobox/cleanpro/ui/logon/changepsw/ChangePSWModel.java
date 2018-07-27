package com.pakpobox.cleanpro.ui.logon.changepsw;

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

public class ChangePSWModel extends BaseModel implements ChangePSWContract.IChangePSWModel {
    @Override
    public void changePSW(String token, String newPassword, NetCallback<UserBean> callback) {
        JSONObject requestObj = new JSONObject();
        try {
            requestObj.put("token", token);
            requestObj.put("password", newPassword);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        postRequest(getApiUrl(UrlConstainer.CHANGE_PASSWORD), requestObj.toString(), callback);
    }
}

