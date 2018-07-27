package com.pakpobox.cleanpro.ui.booking.create;

import com.pakpobox.cleanpro.bean.CreateOrderRequest;
import com.pakpobox.cleanpro.bean.Order;
import com.pakpobox.cleanpro.bean.Register;
import com.pakpobox.cleanpro.bean.Wallet;
import com.pakpobox.cleanpro.net.callback.NetCallback;
import com.pakpobox.cleanpro.ui.mvp.view.IView;

/**
 * User:Sean.Wei
 * Date:2018/7/25
 * Time:17:03
 */

public interface CreateOrderContract {
    interface ICreateOrderPresenter {
        /**
         * 创建订单
         */
        void createOrder();
    }

    interface ICreateOrderView extends IView {

        /**
         * 获取下单参数实体类
         * @return
         */
        CreateOrderRequest getCreateOrderParam();

        /**
         * 获取成功
         * @param data 钱包
         */
        void getSuccess(Order data);
    }

    interface ICreateOrderModel {

        /**
         * 创建订单
         * @param body 下单数据
         * @param callback 回调
         */
        void createOrder(CreateOrderRequest body, NetCallback<Order> callback);
    }
}
