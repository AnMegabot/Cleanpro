package com.pakpobox.cleanpro.ui.account.paypsw;

import android.app.Activity;

import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.application.MyApplication;
import com.pakpobox.cleanpro.bean.Result;
import com.pakpobox.cleanpro.bean.UserBean;
import com.pakpobox.cleanpro.net.callback.BaseNetCallback;
import com.pakpobox.cleanpro.ui.mvp.model.IAccountModel;
import com.pakpobox.cleanpro.ui.mvp.model.IModel;
import com.pakpobox.cleanpro.ui.mvp.model.impl.AccountModel;
import com.pakpobox.cleanpro.ui.mvp.presenter.BasePresenter;

/**
 * User:Sean.Wei
 * Date:2018/7/25
 * Time:11:57
 */

public class SetPaymentPswPresenter extends BasePresenter<PaymentPswContract.IPaymentPswView> implements PaymentPswContract.IPaymentPswPresenter {
    private IAccountModel mModel;

    private Activity activity;

    public SetPaymentPswPresenter(Activity activity) {
        this.activity = activity;
        this.mModel = new AccountModel();
        addModel((IModel) mModel);
    }

    @Override
    public void checkPayPsw(String oldPsw) {
        BaseNetCallback<Result> callback = new BaseNetCallback<Result>(activity, this) {
            @Override
            protected void onSuccess(Result data) {
                if (data.isResult())
                    getView().checkPayPswSuccess();
                else
                    getView().showFail(MyApplication.getContext().getString(R.string.setting_payment_psw_error));
            }
        };
        mModel.checkPayPsw(oldPsw, callback);
    }

    @Override
    public void changePayPsw(String token, String newPsw) {
        BaseNetCallback<UserBean> callback = new BaseNetCallback<UserBean>(activity, this) {
            @Override
            protected void onSuccess(UserBean data) {
                getView().changePayPswSuccess(data.getToken());
            }
        };
        mModel.changePayPSW(token, newPsw, callback);
    }

    @Override
    public void resetPayPsw(String oldPsw, String newPsw) {
        BaseNetCallback<UserBean> callback = new BaseNetCallback<UserBean>(activity, this) {
            @Override
            protected void onSuccess(UserBean data) {
                getView().resetPayPswSuccess(data.getToken());
            }
        };
        mModel.resetPayPsw(oldPsw, newPsw, callback);
    }

}
