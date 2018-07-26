package com.pakpobox.cleanpro.ui.logon.login;

import com.pakpobox.cleanpro.bean.UserBean;
import com.pakpobox.cleanpro.common.UrlConstainer;
import com.pakpobox.cleanpro.net.callback.NetCallback;
import com.pakpobox.cleanpro.ui.mvp.model.BaseModel;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * User:Sean.Wei
 * Date:2018/7/24
 * Time:9:58
 */

public class LoginModel extends BaseModel implements LoginContract.ILoginModel {

    @Override
    public void login(String username, String password, NetCallback<UserBean> callback) {
        JSONObject requestObj = new JSONObject();
        try {
            requestObj.put("account", username);
            requestObj.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        postRequest(getApiUrl(UrlConstainer.LOGIN), requestObj.toString(), callback);
    }

    @Override
    public void saveUserInfo(UserBean userBean) {

    }
}
