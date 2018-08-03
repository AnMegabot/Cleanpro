package com.pakpobox.cleanpro.ui.booking.create;

import com.google.gson.Gson;
import com.pakpobox.cleanpro.bean.CreateOrderRequest;
import com.pakpobox.cleanpro.bean.Order;
import com.pakpobox.cleanpro.bean.Result;
import com.pakpobox.cleanpro.bean.Wallet;
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

public class CreateOrderModel extends BaseModel implements CreateOrderContract.ICreateOrderModel {
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
    public void createOrder(CreateOrderRequest body, NetCallback<Order> callback) {
        String requestStr = new Gson().toJson(body);
        postRequest(getApiUrl(UrlConstainer.CTEATE_ORDER), getBaseHttpHeader(), requestStr, callback);
    }

}

