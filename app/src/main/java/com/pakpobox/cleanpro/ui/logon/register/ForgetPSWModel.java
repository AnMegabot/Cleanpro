package com.pakpobox.cleanpro.ui.logon.register;

import com.pakpobox.cleanpro.common.UrlConstainer;
import com.pakpobox.cleanpro.net.callback.NetCallback;
import com.pakpobox.cleanpro.ui.mvp.model.BaseModel;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * User:Sean.Wei
 * Date:2018/7/25
 * Time:15:09
 */

public class ForgetPSWModel extends BaseModel implements BaseVerifyContract.IVerifyModel {
    @Override
    public void getVerifyCode(String phoneNumber, NetCallback<String> callback) {
        JSONObject requestObj = new JSONObject();
        try {
            requestObj.put("phone", phoneNumber);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        postRequest(getApiUrl(UrlConstainer.GET_VERIFYCODE_REPSW), requestObj.toString(), callback);
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

        postRequest(getApiUrl(UrlConstainer.CHECK_VERIFYCODE_REPSW), requestObj.toString(), callback);
    }
}