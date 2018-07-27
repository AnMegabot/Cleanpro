package com.pakpobox.cleanpro.ui.order;

import android.app.Activity;

import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.application.MyApplication;
import com.pakpobox.cleanpro.bean.Order;
import com.pakpobox.cleanpro.bean.PageListDataBean;
import com.pakpobox.cleanpro.bean.price.Price;
import com.pakpobox.cleanpro.net.callback.NetCallback;
import com.pakpobox.cleanpro.ui.mvp.presenter.BasePresenter;
import com.pakpobox.cleanpro.ui.price.PriceContract;
import com.pakpobox.cleanpro.ui.price.PriceModel;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * User:Sean.Wei
 * Date:2018/6/21
 * Time:17:08
 */

public class OrdersPresenter extends BasePresenter<OrdersContract.IOrdersView> implements OrdersContract.IOrdersPresenter {
    private OrdersContract.IIOrdersModel mModel;
    private OrdersContract.IOrdersView mOrdersView;

    private Activity activity;

    public OrdersPresenter(Activity activity) {
        this.activity = activity;
        mModel = new OrdersModel();
    }

    @Override
    public void getOrdersList() {
        mOrdersView = getView();
        NetCallback<PageListDataBean<Order>> callback = new NetCallback<PageListDataBean<Order>>(activity, this) {
            @Override
            protected void onSuccess(PageListDataBean<Order> data) {
                if (null != data)
                    mOrdersView.getSuccess(data);
                else
                    mOrdersView.showFail(MyApplication.getContext().getString(R.string.app_unknown_error));
            }
        };
        mModel.getOrdersList(mOrdersView.getPage(), 20, callback);
    }

}
