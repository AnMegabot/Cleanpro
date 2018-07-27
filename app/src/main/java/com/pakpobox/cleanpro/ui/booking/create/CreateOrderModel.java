package com.pakpobox.cleanpro.ui.booking.create;

import com.google.gson.Gson;
import com.pakpobox.cleanpro.bean.CreateOrderRequest;
import com.pakpobox.cleanpro.bean.Order;
import com.pakpobox.cleanpro.bean.Wallet;
import com.pakpobox.cleanpro.common.UrlConstainer;
import com.pakpobox.cleanpro.net.callback.NetCallback;
import com.pakpobox.cleanpro.ui.mvp.model.BaseModel;

/**
 * User:Sean.Wei
 * Date:2018/7/25
 * Time:17:36
 */

public class CreateOrderModel extends BaseModel implements CreateOrderContract.ICreateOrderModel {
    @Override
    public void createOrder(CreateOrderRequest body, NetCallback<Order> callback) {
        String requestStr = new Gson().toJson(body);
        postRequest(getApiUrl(UrlConstainer.CTEATE_ORDER), getBaseHttpHeader(), requestStr, callback);
    }

}

