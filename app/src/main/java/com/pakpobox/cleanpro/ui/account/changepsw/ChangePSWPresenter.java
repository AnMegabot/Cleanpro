package com.pakpobox.cleanpro.ui.account.changepsw;

import android.app.Activity;
import android.text.TextUtils;

import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.application.MyApplication;
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

public class ChangePSWPresenter extends BasePresenter<ChangePSWContract.IChangePSWView> implements ChangePSWContract.IChangePSWPresenter{
    private String token, password;
    private IAccountModel mModel;
    private ChangePSWContract.IChangePSWView mChangePSWView;

    private Activity activity;

    public ChangePSWPresenter(Activity activity) {
        this.activity = activity;
        mModel = new AccountModel();
        addModel((IModel) mModel);
    }

    @Override
    public void changePSW() {
        if (verifyPassword()) {
            BaseNetCallback<UserBean> callback = new BaseNetCallback<UserBean>(activity, this) {
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
