package com.pakpobox.cleanpro.ui.order;

import com.pakpobox.cleanpro.bean.Order;
import com.pakpobox.cleanpro.bean.PageListDataBean;
import com.pakpobox.cleanpro.common.UrlConstainer;
import com.pakpobox.cleanpro.net.callback.NetCallback;
import com.pakpobox.cleanpro.ui.mvp.model.BaseModel;

/**
 * User:Sean.Wei
 * Date:2018/7/25
 * Time:17:36
 */

public class OrdersModel extends BaseModel implements OrdersContract.IIOrdersModel {
    @Override
    public void getOrdersList(int page, int maxCount, NetCallback<PageListDataBean<Order>> callback) {
        getRequest(getApiUrl(UrlConstainer.ORDER_LIST, page, maxCount), getBaseHttpHeader(),callback);
    }
}

