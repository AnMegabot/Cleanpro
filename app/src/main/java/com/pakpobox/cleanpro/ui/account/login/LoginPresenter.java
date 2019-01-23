package com.pakpobox.cleanpro.ui.account.login;

import android.app.Activity;
import android.text.TextUtils;

import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.application.AppSetting;
import com.pakpobox.cleanpro.application.MyApplication;
import com.pakpobox.cleanpro.bean.UserBean;
import com.pakpobox.cleanpro.net.callback.BaseNetCallback;
import com.pakpobox.cleanpro.ui.mvp.model.IAccountModel;
import com.pakpobox.cleanpro.ui.mvp.model.IModel;
import com.pakpobox.cleanpro.ui.mvp.model.impl.AccountModel;
import com.pakpobox.cleanpro.ui.mvp.presenter.BasePresenter;

import org.greenrobot.eventbus.EventBus;

/**
 * User:Sean.Wei
 * Date:2018/7/19
 * Time:15:59
 */

public class LoginPresenter extends BasePresenter<LoginContract.ILoginView> implements LoginContract.ILoginPresenter{
    private String userName, password;
    private IAccountModel mModel;
    private LoginContract.ILoginView mLoginView;

    private Activity activity;

    public LoginPresenter(Activity activity) {
        this.activity = activity;
        mModel = new AccountModel();
        addModel((IModel) mModel);
    }

    @Override
    public void login() {
        if (verifyAccount()) {
            BaseNetCallback<UserBean> callback = new BaseNetCallback<UserBean>(activity, this) {
                @Override
                protected void onSuccess(UserBean data) {
                    if (null != data) {
                        AppSetting.saveUserInfo(data);
                        AppSetting.saveIsLogin(true);
                        AppSetting.saveLastPhoneNumb(userName);
                        EventBus.getDefault().post(data);
                        mLoginView.loginSuccess();
                    }else
                        mLoginView.showFail(MyApplication.getContext().getString(R.string.login_signIn_fail_warn));
                }
            };
            mModel.login(userName, password, callback);
        }
    }

    @Override
    public boolean verifyAccount() {
        mLoginView = getView();
        userName = mLoginView.getUserName();
        password = mLoginView.getPassWord();
        if (TextUtils.isEmpty(userName)) {
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            return false;
        }
        return true;
    }

}
