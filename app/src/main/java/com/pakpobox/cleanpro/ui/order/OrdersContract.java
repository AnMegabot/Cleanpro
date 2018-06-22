package com.pakpobox.cleanpro.ui.order;

import com.pakpobox.cleanpro.bean.Order;
import com.pakpobox.cleanpro.ui.mvp.view.IListDataView;

/**
 * User:Sean.Wei
 * Date:2018/6/21
 * Time:17:03
 */

public interface OrdersContract {
    interface IOrdersPresenter {
        void getOrdersList();
    }

    interface IOrdersView extends IListDataView<Order> {

    }
}
