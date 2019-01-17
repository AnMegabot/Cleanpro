package com.pakpobox.cleanpro.ui.logon.register;

import android.app.Activity;

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

public class VerifyMobilePresenter extends BasePresenter<RegisterContract.IVerifyMobileView> implements RegisterContract.IVerifyMobilePresenter {
    private IAccountModel mModel;
    private int flag = 0;//0为注册，1为忘记密码

    private Activity activity;

    public VerifyMobilePresenter(Activity activity, int flag) {
        this.activity = activity;
        this.flag = flag;
        mModel = new AccountModel();
        addModel((IModel) mModel);
    }

    @Override
    public void getVerifyCode(String phoneNumber, String countryCode) {
        BaseNetCallback<String> callback = new BaseNetCallback<String>(activity, this) {
            @Override
            protected void onSuccess(String data) {
                getView().getSuccess(data);
            }
        };
        switch (flag) {
            case 0:
                mModel.getRegisterVerifyCode(phoneNumber, countryCode, callback);
                break;

            case 1:
                mModel.getForgetPSWVerifyCode(phoneNumber, countryCode, callback);
                break;
        }

    }

    @Override
    public void checkVerifyCode(String phoneNumber, String verifyCode) {
        BaseNetCallback<String> callback = new BaseNetCallback<String>(activity, this) {
            @Override
            protected void onSuccess(String data) {
                    getView().checkSuccess(data);
            }
        };
        switch (flag) {
            case 0:
                mModel.checkRegisterVerifyCode(phoneNumber, verifyCode, callback);
                break;

            case 1:
                mModel.checkForgetPSWVerifyCode(phoneNumber, verifyCode, callback);
                break;
        }
    }
}
