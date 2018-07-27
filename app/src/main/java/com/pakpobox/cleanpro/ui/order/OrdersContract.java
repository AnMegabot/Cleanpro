package com.pakpobox.cleanpro.ui.order;

import com.pakpobox.cleanpro.bean.Order;
import com.pakpobox.cleanpro.bean.PageListDataBean;
import com.pakpobox.cleanpro.net.callback.NetCallback;
import com.pakpobox.cleanpro.ui.mvp.view.IListDataView;

import java.util.List;

/**
 * User:Sean.Wei
 * Date:2018/6/21
 * Time:17:03
 */

public interface OrdersContract {
    interface IOrdersPresenter {
        /**
         * 获取订单列表
         */
        void getOrdersList();
    }

    interface IOrdersView extends IListDataView<Order> {
        /**
         * 获取成功
         * @param datas 订单列表
         */
        void getSuccess(PageListDataBean<Order> datas);
    }

    interface IIOrdersModel {

        /**
         * 获取订单列表
         * @param page 页数
         * @param maxCount 每页条数
         * @param callback 回调
         */
        void getOrdersList(int page, int maxCount, NetCallback<PageListDataBean<Order>> callback);
    }
}
