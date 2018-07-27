package com.pakpobox.cleanpro.ui.wallet.trlist;

import android.app.Activity;

import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.application.MyApplication;
import com.pakpobox.cleanpro.bean.PageListDataBean;
import com.pakpobox.cleanpro.bean.TradingRecort;
import com.pakpobox.cleanpro.net.callback.NetCallback;
import com.pakpobox.cleanpro.ui.mvp.presenter.BasePresenter;

/**
 * User:Sean.Wei
 * Date:2018/6/27
 * Time:12:08
 */

public class TRListPresenter extends BasePresenter<TRListContract.ITRListView> implements TRListContract.ITRListPresenter {
    private TRListContract.ITRListModel mModel;
    private TRListContract.ITRListView mTRListView;

    private Activity activity;

    public TRListPresenter(Activity activity) {
        this.activity = activity;
        mModel = new TRListModel();
    }

    @Override
    public void getRechargeDetailList() {
        mTRListView = getView();
        NetCallback<PageListDataBean<TradingRecort>> callback = new NetCallback<PageListDataBean<TradingRecort>>(activity, this) {
            @Override
            protected void onSuccess(PageListDataBean<TradingRecort> data) {
                if (null != data)
                    mTRListView.getSuccess(data);
                else
                    mTRListView.showFail(MyApplication.getContext().getString(R.string.app_unknown_error));
            }
        };
        mModel.getRechargeDetailList(mTRListView.getPage(), 20, callback);
    }

}
