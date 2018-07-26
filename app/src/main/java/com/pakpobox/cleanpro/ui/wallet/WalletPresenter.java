package com.pakpobox.cleanpro.ui.wallet;

import android.app.Activity;
import android.text.TextUtils;

import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.application.MyApplication;
import com.pakpobox.cleanpro.bean.UserBean;
import com.pakpobox.cleanpro.bean.Wallet;
import com.pakpobox.cleanpro.net.callback.NetCallback;
import com.pakpobox.cleanpro.ui.mvp.presenter.BasePresenter;

/**
 * User:Sean.Wei
 * Date:2018/7/25
 * Time:17:34
 */

public class WalletPresenter extends BasePresenter<WalletContract.IWalletView> implements WalletContract.IWalletPresenter{
    private String token, password;
    private WalletContract.IWalletModel mModel;
    private WalletContract.IWalletView mWalletView;

    private Activity activity;

    public WalletPresenter(Activity activity) {
        this.activity = activity;
        mModel = new WalletModel();
    }

    @Override
    public void getWallet() {
        NetCallback<Wallet> callback = new NetCallback<Wallet>(activity, this) {
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
