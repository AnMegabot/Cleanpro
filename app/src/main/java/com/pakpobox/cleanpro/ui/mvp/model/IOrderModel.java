package com.pakpobox.cleanpro.ui.mvp.model;

import com.pakpobox.cleanpro.bean.CreateOrderRequest;
import com.pakpobox.cleanpro.net.callback.INetCallback;

/**
 * User:Sean.Wei
 * Date:2019/1/10
 * Time:16:21
 */

public interface IOrderModel {

    /**
     * 创建订单
     * @param body 下单数据
     * @param callback 回调
     */
    void createOrder(CreateOrderRequest body, INetCallback callback);

    /**
     * 获取订单列表
     * @param page 页数
     * @param maxCount 每页条数
     * @param callback 回调
     */
    void getOrdersList(int page, int maxCount, INetCallback callback);
}
