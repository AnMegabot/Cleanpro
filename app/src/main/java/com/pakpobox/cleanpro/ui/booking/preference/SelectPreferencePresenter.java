package com.pakpobox.cleanpro.ui.booking.preference;

import android.app.Activity;

import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.application.MyApplication;
import com.pakpobox.cleanpro.bean.PageListDataBean;
import com.pakpobox.cleanpro.bean.location.Site;
import com.pakpobox.cleanpro.bean.price.Price;
import com.pakpobox.cleanpro.net.callback.NetCallback;
import com.pakpobox.cleanpro.ui.mvp.presenter.BasePresenter;

import java.util.List;

/**
 * User:Sean.Wei
 * Date:2018/7/25
 * Time:17:34
 */

public class SelectPreferencePresenter extends BasePresenter<SelectPreferenceContract.ISelectPreferenceView> implements SelectPreferenceContract.ISelectPreferencePresenter{
    private SelectPreferenceContract.ISelectPreferenceModel mModel;
    private SelectPreferenceContract.ISelectPreferenceView mSelectPreferenceView;

    private Activity activity;

    public SelectPreferencePresenter(Activity activity) {
        this.activity = activity;
        mModel = new SelectPreferenceModel();
    }

    @Override
    public void getPrice() {
        mSelectPreferenceView = getView();
        NetCallback<List<Price>> callback = new NetCallback<List<Price>>(activity, this) {
            @Override
            protected void onSuccess(List<Price> data) {
                if (null != data)
                    mSelectPreferenceView.getSuccess(data);
                else
                    mSelectPreferenceView.showFail(MyApplication.getContext().getString(R.string.app_unknown_error));
            }
        };
        mModel.getPrice(mSelectPreferenceView.getMachineNo(), callback);
    }

}
