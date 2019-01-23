package com.pakpobox.cleanpro.ui.account.register;

import android.app.Activity;

import com.pakpobox.cleanpro.bean.Register;
import com.pakpobox.cleanpro.bean.UserBean;
import com.pakpobox.cleanpro.net.callback.BaseNetCallback;
import com.pakpobox.cleanpro.ui.mvp.model.IAccountModel;
import com.pakpobox.cleanpro.ui.mvp.model.IModel;
import com.pakpobox.cleanpro.ui.mvp.model.impl.AccountModel;
import com.pakpobox.cleanpro.ui.mvp.presenter.BasePresenter;

/**
 * User:Sean.Wei
 * Date:2019/1/17
 * Time:16:21
 */

public class RegisterPresenter extends BasePresenter<RegisterContract.IRegisterView> implements RegisterContract.IRegisterPresenter {
    private IAccountModel mModel;

    private Activity activity;

    public RegisterPresenter(Activity activity) {
        this.activity = activity;
        mModel = new AccountModel();
        addModel((IModel) mModel);
    }

    @Override
    public void checkInviteCode(String inviteCode) {
        BaseNetCallback<String> callback = new BaseNetCallback<String>(activity, this) {
            @Override
            protected void onSuccess(String data) {
                getView().checkInviteCodeSuccess(data);
            }
        };
        mModel.checkInviteCode(inviteCode, callback);
    }

    @Override
    public void register(Register register) {
        BaseNetCallback<UserBean> callback = new BaseNetCallback<UserBean>(activity, this) {
            @Override
            protected void onSuccess(UserBean data) {
                getView().registerSuccess(data);
            }
        };
        mModel.register(register, callback);
    }
}
