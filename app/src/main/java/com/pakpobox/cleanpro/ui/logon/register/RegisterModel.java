package com.pakpobox.cleanpro.ui.logon.register;

import android.text.TextUtils;

import com.pakpobox.cleanpro.common.UrlConstainer;
import com.pakpobox.cleanpro.net.callback.NetCallback;
import com.pakpobox.cleanpro.ui.mvp.model.BaseModel;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * User:Sean.Wei
 * Date:2018/7/25
 * Time:14:55
 */

public class RegisterModel extends BaseModel implements BaseVerifyContract.IVerifyModel {
    @Override
    public void getVerifyCode(String phoneNumber, String countryCode, NetCallback<String> callback) {
        JSONObject requestObj = new JSONObject();
        try {
            requestObj.put("phoneNumber", phoneNumber);
            if (!TextUtils.isEmpty(countryCode))
                requestObj.put("countryCode", countryCode);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        postRequest(getApiUrl(UrlConstainer.GET_VERIFYCODE), requestObj.toString(), callback);
    }

    @Override
    public void checkVerifyCode(String phoneNumber, String verifyCode, NetCallback<String> callback) {
        JSONObject requestObj = new JSONObject();
        try {
            requestObj.put("phone", phoneNumber);
            requestObj.put("code", verifyCode);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        postRequest(getApiUrl(UrlConstainer.CHECK_VERIFYCODE), requestObj.toString(), callback);
    }
}
