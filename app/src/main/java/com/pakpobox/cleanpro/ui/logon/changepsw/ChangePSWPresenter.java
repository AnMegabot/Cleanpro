package com.pakpobox.cleanpro.ui.logon.changepsw;

import android.app.Activity;
import android.text.TextUtils;

import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.application.MyApplication;
import com.pakpobox.cleanpro.bean.Result;
import com.pakpobox.cleanpro.bean.UserBean;
import com.pakpobox.cleanpro.net.callback.NetCallback;
import com.pakpobox.cleanpro.ui.mvp.presenter.BasePresenter;

/**
 * User:Sean.Wei
 * Date:2018/7/25
 * Time:17:34
 */

public class ChangePSWPresenter extends BasePresenter<ChangePSWContract.IChangePSWView> implements ChangePSWContract.IChangePSWPresenter{
    private String token, password;
    private ChangePSWContract.IChangePSWModel mModel;
    private ChangePSWContract.IChangePSWView mChangePSWView;

    private Activity activity;

    public ChangePSWPresenter(Activity activity) {
        this.activity = activity;
        mModel = new ChangePSWModel();
    }

    @Override
    public void changePSW() {
        if (verifyPassword()) {
            NetCallback<UserBean> callback = new NetCallback<UserBean>(activity, this) {
                @Override
                protected void onSuccess(UserBean data) {
                    if (null != data)
                        mChangePSWView.changeSuccess();
                    else
                        mChangePSWView.showFail(MyApplication.getContext().getString(R.string.app_unknown_error));
                }
            };
            mModel.changePSW(token, password, callback);
        }
    }

    @Override
    public boolean verifyPassword() {
        mChangePSWView = getView();
        password = mChangePSWView.getPassWord();
        token = mChangePSWView.getToken();
        if (TextUtils.isEmpty(token)) {
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            return false;
        }
        return true;
    }
}
