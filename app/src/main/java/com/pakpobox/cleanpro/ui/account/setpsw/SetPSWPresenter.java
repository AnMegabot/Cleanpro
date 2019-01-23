package com.pakpobox.cleanpro.ui.account.setpsw;

import android.app.Activity;
import android.text.TextUtils;

import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.application.MyApplication;
import com.pakpobox.cleanpro.bean.Register;
import com.pakpobox.cleanpro.bean.UserBean;
import com.pakpobox.cleanpro.net.callback.BaseNetCallback;
import com.pakpobox.cleanpro.ui.mvp.model.IAccountModel;
import com.pakpobox.cleanpro.ui.mvp.model.IModel;
import com.pakpobox.cleanpro.ui.mvp.model.impl.AccountModel;
import com.pakpobox.cleanpro.ui.mvp.presenter.BasePresenter;

/**
 * User:Sean.Wei
 * Date:2018/7/25
 * Time:17:34
 */

public class SetPSWPresenter extends BasePresenter<SetPSWContract.ISetPSWView> implements SetPSWContract.ISetPSWPresenter{
    private IAccountModel mModel;
    private SetPSWContract.ISetPSWView mSetPSWView;

    private Activity activity;

    public SetPSWPresenter(Activity activity) {
        this.activity = activity;
        mModel = new AccountModel();
        addModel((IModel) mModel);
    }

    @Override
    public void register() {
        if (verifyPassword(true)
                && verifyPayPassword(true)
                && verifyOther()){
            BaseNetCallback<UserBean> callback = new BaseNetCallback<UserBean>(activity, this) {
                @Override
                protected void onSuccess(UserBean data) {
                    if (null != data)
                        mSetPSWView.registerSuccess();
                    else
                        mSetPSWView.showFail(MyApplication.getContext().getString(R.string.login_signUp_fail_warn));
                }
            };
            mModel.register(mSetPSWView.getRegisterParam(), callback);
        }

    }

    @Override
    public boolean verifyPassword(boolean isFineVerify) {
        mSetPSWView = getView();
        String password = mSetPSWView.getPassword();
        String enterPassword = mSetPSWView.getEnterPassword();
        if (TextUtils.isEmpty(password)) {
            return false;
        }
        if (TextUtils.isEmpty(enterPassword)) {
            return false;
        }

        if (!isFineVerify)
            return true;

        if (!password.equals(enterPassword)) {
            mSetPSWView.showFail(MyApplication.getContext().getString(R.string.login_login_psw_match_warn));
            return false;
        }
        return true;
    }

    @Override
    public boolean verifyPayPassword(boolean isFineVerify) {
        mSetPSWView = getView();
        String password = mSetPSWView.getPayPassword();
        String enterPassword = mSetPSWView.getEnterPayPassword();
        if (TextUtils.isEmpty(password) || password.length()<6) {
            return false;
        }
        if (TextUtils.isEmpty(enterPassword) || enterPassword.length()<6) {
            return false;
        }

        if (!isFineVerify)
            return true;

        if (!password.equals(enterPassword)) {
            mSetPSWView.showFail(MyApplication.getContext().getString(R.string.login_pay_psw_match_warn));
            return false;
        }

        return true;
    }

    @Override
    public boolean verifyOther() {
        mSetPSWView = getView();
        Register register = mSetPSWView.getRegisterParam();
        if (TextUtils.isEmpty(register.getPhoneNumber()) || TextUtils.isEmpty(register.getRandomPassword()) || TextUtils.isEmpty(register.getCountryCode())) {
            mSetPSWView.showFail(MyApplication.getContext().getString(R.string.app_unknown_error));
            return false;
        }
        return true;
    }

}
