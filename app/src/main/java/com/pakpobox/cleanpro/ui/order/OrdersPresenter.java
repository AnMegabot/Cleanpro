package com.pakpobox.cleanpro.ui.order;

import com.pakpobox.cleanpro.bean.Order;
import com.pakpobox.cleanpro.ui.mvp.presenter.BasePresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * User:Sean.Wei
 * Date:2018/6/21
 * Time:17:08
 */

public class OrdersPresenter extends BasePresenter<OrdersContract.IOrdersView> implements OrdersContract.IOrdersPresenter {
    private OrdersContract.IOrdersView ordersView;

    OrdersPresenter() {
    }

    @Override
    public void getOrdersList() {
        ordersView = getView();
        ordersView.showLoading("");
        List<Order> orders = new ArrayList<>();
        for (int i=0; i<10; i++) {
            orders.add(new Order(UUID.randomUUID().toString(), i % 2 == 0 ? "Laundry" : "Dryer", i % 3 == 0 ? "PAID" : "UNPAID", "Order No.D123456789", "5.00"));
        }
        if (ordersView.getPage() == 0) {
            ordersView.clearListData();
        }
        if (ordersView.getPage() > 10) {
            ordersView.showNoMore();
        } else {
            ordersView.autoLoadMore();
        }
        ordersView.setData(orders);

        if (ordersView.getData().size() == 0)
            ordersView.showEmpty();
        else
            ordersView.showContent();

        ordersView.hideLoading();
    }

}
