package com.pakpobox.cleanpro.ui.order;

import android.app.Activity;

import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.application.MyApplication;
import com.pakpobox.cleanpro.bean.Order;
import com.pakpobox.cleanpro.bean.PageListDataBean;
import com.pakpobox.cleanpro.net.callback.BaseNetCallback;
import com.pakpobox.cleanpro.net.callback.PageListNetCallback;
import com.pakpobox.cleanpro.ui.mvp.model.IModel;
import com.pakpobox.cleanpro.ui.mvp.model.IOrderModel;
import com.pakpobox.cleanpro.ui.mvp.model.impl.OrderModel;
import com.pakpobox.cleanpro.ui.mvp.presenter.BasePresenter;

/**
 * User:Sean.Wei
 * Date:2018/6/21
 * Time:17:08
 */

public class OrdersPresenter extends BasePresenter<OrdersContract.IOrdersView> implements OrdersContract.IOrdersPresenter {
    private IOrderModel mModel;
    private OrdersContract.IOrdersView mOrdersView;

    private Activity activity;

    public OrdersPresenter(Activity activity) {
        this.activity = activity;
        mModel = new OrderModel();
        addModel((IModel) mModel);
    }

    @Override
    public void getOrdersList() {
        mOrdersView = getView();
        mModel.getOrdersList(mOrdersView.getPage(), 20, new PageListNetCallback<Order>(activity, this){});
    }

}
