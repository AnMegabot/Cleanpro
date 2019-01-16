package com.pakpobox.cleanpro.ui.mvp.model.impl;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.pakpobox.cleanpro.bean.Register;
import com.pakpobox.cleanpro.common.UrlConstainer;
import com.pakpobox.cleanpro.net.callback.INetCallback;
import com.pakpobox.cleanpro.ui.mvp.model.IAccountModel;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * User:Sean.Wei
 * Date:2019/1/10
 * Time:17:35
 */

public class AccountModel extends BaseModel implements IAccountModel {
    @Override
    public void register(Register register, INetCallback callback) {
        String requestStr = new Gson().toJson(register);
        postRequest(getApiUrl(UrlConstainer.REGISTER), requestStr, callback);
    }

    @Override
    public void login(String username, String password, INetCallback callback) {
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
    public void changePSW(String token, String newPassword, INetCallback callback) {
        JSONObject requestObj = new JSONObject();
        try {
            requestObj.put("token", token);
            requestObj.put("password", newPassword);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        postRequest(getApiUrl(UrlConstainer.CHANGE_PASSWORD), requestObj.toString(), callback);
    }

    @Override
    public void getRegisterVerifyCode(String phoneNumber, String countryCode, INetCallback callback) {
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
    public void getForgetPSWVerifyCode(String phoneNumber, String countryCode, INetCallback callback) {
        JSONObject requestObj = new JSONObject();
        try {
            requestObj.put("phone", phoneNumber);
            if (!TextUtils.isEmpty(countryCode))
                requestObj.put("countryCode", countryCode);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        postRequest(getApiUrl(UrlConstainer.GET_VERIFYCODE_REPSW), requestObj.toString(), callback);
    }

    @Override
    public void checkRegisterVerifyCode(String phoneNumber, String verifyCode, INetCallback callback) {
        JSONObject requestObj = new JSONObject();
        try {
            requestObj.put("phone", phoneNumber);
            requestObj.put("code", verifyCode);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        postRequest(getApiUrl(UrlConstainer.CHECK_VERIFYCODE), requestObj.toString(), callback);
    }

    @Override
    public void checkForgetPSWVerifyCode(String phoneNumber, String verifyCode, INetCallback callback) {
        JSONObject requestObj = new JSONObject();
        try {
            requestObj.put("phone", phoneNumber);
            requestObj.put("code", verifyCode);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        postRequest(getApiUrl(UrlConstainer.CHECK_VERIFYCODE_REPSW), requestObj.toString(), callback);
    }

    @Override
    public void checkPayPsw(String payPassword, INetCallback callback) {
        JSONObject requestObj = new JSONObject();
        try {
            requestObj.put("payPassword", payPassword);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        postRequest(getApiUrl(UrlConstainer.CHECK_PAY_PSW), getBaseHttpHeader(), requestObj.toString(), callback);
    }

    @Override
    public void resetPayPsw(String oldPayPassword, String newPayPassword, INetCallback callback) {
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
    public void changePayPSW(String token, String newPaymentPassword, INetCallback callback) {
        JSONObject requestObj = new JSONObject();
        try {
            requestObj.put("token", token);
            requestObj.put("payPassword", newPaymentPassword);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        postRequest(getApiUrl(UrlConstainer.CHANGE_PAY_PASSWORD), requestObj.toString(), callback);
    }

    @Override
    public void getWallet(INetCallback callback) {
        getRequest(getApiUrl(UrlConstainer.WALLET), getBaseHttpHeader(), callback);
    }

    @Override
    public void getRechargeDetailList(int page, int maxCount, INetCallback callback) {
        getRequest(getApiUrl(UrlConstainer.WALLET_QUERY, page, maxCount), getBaseHttpHeader(),callback);
    }
}
