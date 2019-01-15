package com.pakpobox.cleanpro.ui.booking.create;

import android.app.Activity;

import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.application.MyApplication;
import com.pakpobox.cleanpro.bean.Order;
import com.pakpobox.cleanpro.bean.Result;
import com.pakpobox.cleanpro.net.callback.BaseNetCallback;
import com.pakpobox.cleanpro.ui.mvp.model.IAccountModel;
import com.pakpobox.cleanpro.ui.mvp.model.IModel;
import com.pakpobox.cleanpro.ui.mvp.model.IOrderModel;
import com.pakpobox.cleanpro.ui.mvp.model.impl.AccountModel;
import com.pakpobox.cleanpro.ui.mvp.model.impl.OrderModel;
import com.pakpobox.cleanpro.ui.mvp.presenter.BasePresenter;

/**
 * User:Sean.Wei
 * Date:2018/7/25
 * Time:17:34
 */

public class CreateOrderPresenter extends BasePresenter<CreateOrderContract.ICreateOrderView> implements CreateOrderContract.ICreateOrderPresenter{
    private IOrderModel mOrderModel;
    private IAccountModel mAccountModel;
    private CreateOrderContract.ICreateOrderView mCreateOrderView;

    private Activity activity;

    public CreateOrderPresenter(Activity activity) {
        this.activity = activity;
        mOrderModel = new OrderModel();
        addModel((IModel) mOrderModel);
        mAccountModel = new AccountModel();
        addModel((IModel) mAccountModel);
    }

    @Override
    public void checkPayPsw(String paymentPSW) {
        mCreateOrderView = getView();
        BaseNetCallback<Result> callback = new BaseNetCallback<Result>(activity, this) {
            @Override
            protected void onSuccess(Result data) {
                if (data.isResult())
                    mCreateOrderView.checkPayPswSuccess();
                else
                    mCreateOrderView.showFail(MyApplication.getContext().getString(R.string.setting_payment_psw_error));
            }
        };
        mAccountModel.checkPayPsw(paymentPSW, callback);
    }

    @Override
    public void createOrder() {
        mCreateOrderView = getView();
        BaseNetCallback<Order> callback = new BaseNetCallback<Order>(activity, this) {
            @Override
            protected void onSuccess(Order data) {
                if (null != data)
                    mCreateOrderView.createSuccess(data);
                else
                    mCreateOrderView.showFail(MyApplication.getContext().getString(R.string.app_response_empty));
            }
        };
        mOrderModel.createOrder(mCreateOrderView.getCreateOrderParam(), callback);
    }
}
