package com.pakpobox.cleanpro.ui.booking.preference;

import android.app.Activity;

import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.application.MyApplication;
import com.pakpobox.cleanpro.bean.price.Price;
import com.pakpobox.cleanpro.net.callback.BaseNetCallback;
import com.pakpobox.cleanpro.ui.mvp.model.ICommonModel;
import com.pakpobox.cleanpro.ui.mvp.model.IModel;
import com.pakpobox.cleanpro.ui.mvp.model.impl.CommonModel;
import com.pakpobox.cleanpro.ui.mvp.presenter.BasePresenter;

import java.util.List;

/**
 * User:Sean.Wei
 * Date:2018/7/25
 * Time:17:34
 */

public class SelectPreferencePresenter extends BasePresenter<SelectPreferenceContract.ISelectPreferenceView> implements SelectPreferenceContract.ISelectPreferencePresenter{
    private ICommonModel mModel;
    private SelectPreferenceContract.ISelectPreferenceView mSelectPreferenceView;

    private Activity activity;

    public SelectPreferencePresenter(Activity activity) {
        this.activity = activity;
        mModel = new CommonModel();
        addModel((IModel) mModel);
    }

    @Override
    public void getPrice() {
        mSelectPreferenceView = getView();
        BaseNetCallback<List<Price>> callback = new BaseNetCallback<List<Price>>(activity, this){
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
