package com.pakpobox.cleanpro.ui.wallet;

import android.app.Activity;

import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.application.MyApplication;
import com.pakpobox.cleanpro.bean.Wallet;
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

public class WalletPresenter extends BasePresenter<WalletContract.IWalletView> implements WalletContract.IWalletPresenter{
    private String token, password;
    private IAccountModel mModel;
    private WalletContract.IWalletView mWalletView;

    private Activity activity;

    public WalletPresenter(Activity activity) {
        this.activity = activity;
        mModel = new AccountModel();
        addModel((IModel) mModel);
    }

    @Override
    public void getWallet() {
        BaseNetCallback<Wallet> callback = new BaseNetCallback<Wallet>(activity, this) {
            @Override
            protected void onSuccess(Wallet data) {
                mWalletView = getView();
                if (null != data)
                    mWalletView.getSuccess(data);
                else
                    mWalletView.showFail(MyApplication.getContext().getString(R.string.app_unknown_error));
            }
        };
        mModel.getWallet(callback);
    }

}
