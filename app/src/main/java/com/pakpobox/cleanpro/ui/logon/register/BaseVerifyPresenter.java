package com.pakpobox.cleanpro.ui.logon.register;

import android.app.Activity;
import android.text.TextUtils;

import com.pakpobox.cleanpro.net.callback.NetCallback;
import com.pakpobox.cleanpro.ui.mvp.presenter.BasePresenter;

/**
 * User:Sean.Wei
 * Date:2018/7/25
 * Time:11:57
 */

public class BaseVerifyPresenter extends BasePresenter<BaseVerifyContract.IVerifyView> implements BaseVerifyContract.IVerifyPresenter {
    private String phoneNumber, verifyCode;
    private BaseVerifyContract.IVerifyView mRegisterView;
    private BaseVerifyContract.IVerifyModel mModel;

    private Activity activity;

    public BaseVerifyPresenter(Activity activity, BaseVerifyContract.IVerifyModel model) {
        this.activity = activity;
        this.mModel = model;
    }

    @Override
    public void getVerifyCode() {
        if (verifyAccount()) {
            NetCallback<String> callback = new NetCallback<String>(activity, this) {
                @Override
                protected void onSuccess(String data) {
                    mRegisterView.getSuccess(data);
                }
            };
            mModel.getVerifyCode(phoneNumber, callback);
        }
    }

    @Override
    public void checkVerifyCode() {
        if (verifyCode() && verifyAccount()) {
            NetCallback<String> callback = new NetCallback<String>(activity, this) {
                @Override
                protected void onSuccess(String data) {
                        mRegisterView.checkSuccess(data);
                }
            };
            mModel.checkVerifyCode(phoneNumber, verifyCode, callback);
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
