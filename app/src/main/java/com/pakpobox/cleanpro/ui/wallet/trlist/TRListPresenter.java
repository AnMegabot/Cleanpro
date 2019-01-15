package com.pakpobox.cleanpro.ui.wallet.trlist;

import android.app.Activity;

import com.pakpobox.cleanpro.bean.TradingRecort;
import com.pakpobox.cleanpro.net.callback.PageListNetCallback;
import com.pakpobox.cleanpro.ui.mvp.model.IAccountModel;
import com.pakpobox.cleanpro.ui.mvp.model.IModel;
import com.pakpobox.cleanpro.ui.mvp.model.impl.AccountModel;
import com.pakpobox.cleanpro.ui.mvp.presenter.BasePresenter;

/**
 * User:Sean.Wei
 * Date:2018/6/27
 * Time:12:08
 */

public class TRListPresenter extends BasePresenter<TRListContract.ITRListView> implements TRListContract.ITRListPresenter {
    private IAccountModel mModel;

    private Activity activity;

    public TRListPresenter(Activity activity) {
        this.activity = activity;
        mModel = new AccountModel();
        addModel((IModel) mModel);
    }

    @Override
    public void getRechargeDetailList() {
        mModel.getRechargeDetailList(getView().getPage(), 20, new PageListNetCallback<TradingRecort>(activity, this){});
    }

}
