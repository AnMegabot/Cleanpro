package com.pakpobox.cleanpro.ui.setting.setpsw;

import android.app.Activity;
import android.text.TextUtils;

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

public class SetPaymentPswPresenter extends BasePresenter<SetPaymentPswContract.ISetPaymentPswView> implements SetPaymentPswContract.ISetPaymentPswPresenter {
    private SetPaymentPswContract.ISetPaymentPswView mSetPaymentPswView;
    private IAccountModel mModel;

    private Activity activity;

    public SetPaymentPswPresenter(Activity activity) {
        this.activity = activity;
        this.mModel = new AccountModel();
        addModel((IModel) mModel);
    }

    @Override
    public void checkPayPsw() {
        if (verifyPayPassword(1)) {
            BaseNetCallback<Result> callback = new BaseNetCallback<Result>(activity, this) {
                @Override
                protected void onSuccess(Result data) {
                    if (data.isResult())
                        mSetPaymentPswView.checkPayPswSuccess();
                    else
                        mSetPaymentPswView.showFail(MyApplication.getContext().getString(R.string.setting_payment_psw_error));
                }
            };
            mModel.checkPayPsw(mSetPaymentPswView.getOldPassword(), callback);
        }
    }

    @Override
    public void resetPayPsw() {
        if (verifyPayPassword(3)) {
            BaseNetCallback<UserBean> callback = new BaseNetCallback<UserBean>(activity, this) {
                @Override
                protected void onSuccess(UserBean data) {
                    mSetPaymentPswView.resetPayPswSuccess(data.getToken());
                }
            };
            if (TextUtils.isEmpty(mSetPaymentPswView.getToken()))
                mModel.resetPayPsw(mSetPaymentPswView.getOldPassword(), mSetPaymentPswView.getNewPassword2(), callback);
            else
                mModel.changePayPSW(mSetPaymentPswView.getToken(), mSetPaymentPswView.getNewPassword2(), callback);
        }
    }

    @Override
    public boolean verifyPayPassword(int step) {
        mSetPaymentPswView = getView();
        switch (step) {
            case 1:
                if (TextUtils.isEmpty(mSetPaymentPswView.getOldPassword())) {
                    return false;
                }
                break;
            case 2:
                if (TextUtils.isEmpty(mSetPaymentPswView.getNewPassword1())) {
                    return false;
                }
                break;
            case 3:
                if (TextUtils.isEmpty(mSetPaymentPswView.getToken()) && TextUtils.isEmpty(mSetPaymentPswView.getOldPassword())) {
                    return false;
                }
                if (TextUtils.isEmpty(mSetPaymentPswView.getNewPassword1()) || TextUtils.isEmpty(mSetPaymentPswView.getNewPassword2())) {
                    return false;
                }

                if (!mSetPaymentPswView.getNewPassword1().equals(mSetPaymentPswView.getNewPassword2())) {
                    mSetPaymentPswView.showFail(MyApplication.getContext().getString(R.string.login_pay_psw_match_warn));
                    return false;
                }
                break;
        }
        return true;
    }

}
