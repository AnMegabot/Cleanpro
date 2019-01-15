package com.pakpobox.cleanpro.ui.logon.register;

import android.app.Activity;
import android.text.TextUtils;

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

public class BaseVerifyPresenter extends BasePresenter<BaseVerifyContract.IVerifyView> implements BaseVerifyContract.IVerifyPresenter {
    private String phoneNumber, verifyCode;
    private BaseVerifyContract.IVerifyView mRegisterView;
    private IAccountModel mModel;
    private int flag = 0;//0为注册，1为忘记密码

    private Activity activity;

    public BaseVerifyPresenter(Activity activity, int flag) {
        this.activity = activity;
        this.flag = flag;
        mModel = new AccountModel();
        addModel((IModel) mModel);
    }

    @Override
    public void getVerifyCode() {
        if (verifyAccount()) {
            BaseNetCallback<String> callback = new BaseNetCallback<String>(activity, this) {
                @Override
                protected void onSuccess(String data) {
                    mRegisterView.getSuccess(data);
                }
            };
            switch (flag) {
                case 0:
                    mModel.getRegisterVerifyCode(phoneNumber, mRegisterView.getCountryCode(), callback);
                    break;

                case 1:
                    mModel.getForgetPSWVerifyCode(phoneNumber, mRegisterView.getCountryCode(), callback);
                    break;
            }

        }
    }

    @Override
    public void checkVerifyCode() {
        if (verifyCode() && verifyAccount()) {
            BaseNetCallback<String> callback = new BaseNetCallback<String>(activity, this) {
                @Override
                protected void onSuccess(String data) {
                        mRegisterView.checkSuccess(data);
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

    @Override
    public boolean verifyAccount() {
        mRegisterView = getView();
        phoneNumber = mRegisterView.getUserName();
        if (TextUtils.isEmpty(phoneNumber)) {
            return false;
        }
        return true;
    }

    @Override
    public boolean verifyCode() {
        mRegisterView = getView();
        verifyCode = mRegisterView.getVerifyCode();
        if (TextUtils.isEmpty(verifyCode)) {
            return false;
        }
        return true;
    }
}
