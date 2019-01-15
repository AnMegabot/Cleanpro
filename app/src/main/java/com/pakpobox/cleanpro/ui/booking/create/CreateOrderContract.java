package com.pakpobox.cleanpro.ui.booking.create;

import com.pakpobox.cleanpro.bean.CreateOrderRequest;
import com.pakpobox.cleanpro.bean.Order;
import com.pakpobox.cleanpro.bean.Result;
import com.pakpobox.cleanpro.ui.mvp.view.IView;

/**
 * User:Sean.Wei
 * Date:2018/7/25
 * Time:17:03
 */

public interface CreateOrderContract {
    interface ICreateOrderPresenter {
        /**
         * 校验支付密码
         */
        void checkPayPsw(String paymentPSW);

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
         * 校验支付密码成功
         */
        void checkPayPswSuccess();

        /**
         * 获取成功
         * @param data 钱包
         */
        void createSuccess(Order data);
    }
}
