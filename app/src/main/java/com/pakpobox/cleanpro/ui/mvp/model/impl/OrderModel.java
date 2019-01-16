package com.pakpobox.cleanpro.ui.mvp.model.impl;

import com.google.gson.Gson;
import com.pakpobox.cleanpro.bean.CreateOrderRequest;
import com.pakpobox.cleanpro.common.UrlConstainer;
import com.pakpobox.cleanpro.net.callback.INetCallback;
import com.pakpobox.cleanpro.ui.mvp.model.IOrderModel;

/**
 * User:Sean.Wei
 * Date:2018/7/25
 * Time:17:36
 */

public class OrderModel extends BaseModel implements IOrderModel {

    @Override
    public void createOrder(CreateOrderRequest body, INetCallback callback) {
        String requestStr = new Gson().toJson(body);
        postRequest(getApiUrl(UrlConstainer.CTEATE_ORDER), getBaseHttpHeader(), requestStr, callback);
    }

    @Override
    public void getOrdersList(int page, int maxCount, INetCallback callback) {
        getRequest(getApiUrl(UrlConstainer.ORDER_LIST, page, maxCount), getBaseHttpHeader(),callback);
    }
}

