package com.pakpobox.cleanpro.ui.setting.setpsw;

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
 * Time:15:09
 */

public class SetPaymentPswModel extends BaseModel implements SetPaymentPswContract.ISetPaymentPswModel {
    @Override
    public void checkPayPsw(String payPassword, NetCallback<Result> callback) {
        JSONObject requestObj = new JSONObject();
        try {
            requestObj.put("payPassword", payPassword);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        postRequest(getApiUrl(UrlConstainer.CHECK_PAY_PSW), getBaseHttpHeader(), requestObj.toString(), callback);
    }

    @Override
    public void resetPayPsw(String oldPayPassword, String newPayPassword, NetCallback<UserBean> callback) {
        JSONObject requestObj = new JSONObject();
        try {
            requestObj.put("payPassword", newPayPassword);
            requestObj.put("oldPayPassword", oldPayPassword);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        postRequest(getApiUrl(UrlConstainer.RESET_PAY_PSW), getBaseHttpHeader(), requestObj.toString(), callback);
    }

    @Override
    public void changePayPSW(String token, String newPaymentPassword, NetCallback<UserBean> callback) {
        JSONObject requestObj = new JSONObject();
        try {
            requestObj.put("token", token);
            requestObj.put("password", null);
            requestObj.put("payPassword", newPaymentPassword);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        postRequest(getApiUrl(UrlConstainer.CHANGE_PASSWORD), requestObj.toString(), callback);
    }

}
