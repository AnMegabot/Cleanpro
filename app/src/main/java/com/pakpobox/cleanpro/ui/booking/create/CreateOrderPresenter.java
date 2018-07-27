package com.pakpobox.cleanpro.ui.booking.create;

import android.app.Activity;

import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.application.MyApplication;
import com.pakpobox.cleanpro.bean.Order;
import com.pakpobox.cleanpro.bean.Wallet;
import com.pakpobox.cleanpro.net.callback.NetCallback;
import com.pakpobox.cleanpro.ui.mvp.presenter.BasePresenter;

/**
 * User:Sean.Wei
 * Date:2018/7/25
 * Time:17:34
 */

public class CreateOrderPresenter extends BasePresenter<CreateOrderContract.ICreateOrderView> implements CreateOrderContract.ICreateOrderPresenter{
    private CreateOrderContract.ICreateOrderModel mModel;
    private CreateOrderContract.ICreateOrderView mCreateOrderView;

    private Activity activity;

    public CreateOrderPresenter(Activity activity) {
        this.activity = activity;
        mModel = new CreateOrderModel();
    }

    @Override
    public void createOrder() {
        mCreateOrderView = getView();
        NetCallback<Order> callback = new NetCallback<Order>(activity, this) {
            @Override
            protected void onSuccess(Order data) {
                if (null != data)
                    mCreateOrderView.getSuccess(data);
                else
                    mCreateOrderView.showFail(MyApplication.getContext().getString(R.string.app_unknown_error));
            }
        };
        mModel.createOrder(mCreateOrderView.getCreateOrderParam(), callback);
    }

}
