package com.pakpobox.cleanpro.ui.location;

import android.app.Activity;

import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.application.MyApplication;
import com.pakpobox.cleanpro.bean.PageListDataBean;
import com.pakpobox.cleanpro.bean.TradingRecort;
import com.pakpobox.cleanpro.bean.Wallet;
import com.pakpobox.cleanpro.bean.location.Site;
import com.pakpobox.cleanpro.net.callback.NetCallback;
import com.pakpobox.cleanpro.ui.mvp.presenter.BasePresenter;

/**
 * User:Sean.Wei
 * Date:2018/7/25
 * Time:17:34
 */

public class LocationPresenter extends BasePresenter<LocationContract.ILocationView> implements LocationContract.ILocationPresenter{
    private LocationContract.ILocationModel mModel;
    private LocationContract.ILocationView mLocationView;

    private Activity activity;

    public LocationPresenter(Activity activity) {
        this.activity = activity;
        mModel = new LocationModel();
    }

    @Override
    public void getLocations() {
        mLocationView = getView();
        NetCallback<PageListDataBean<Site>> callback = new NetCallback<PageListDataBean<Site>>(activity, this) {
            @Override
            protected void onSuccess(PageListDataBean<Site> data) {
                if (null != data)
                    mLocationView.getSuccess(data);
                else
                    mLocationView.showFail(MyApplication.getContext().getString(R.string.app_unknown_error));
            }
        };
        mModel.getLocations(mLocationView.getPage(), 20, callback);
    }

}
