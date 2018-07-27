package com.pakpobox.cleanpro.ui.price;

import android.app.Activity;

import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.application.MyApplication;
import com.pakpobox.cleanpro.bean.Wallet;
import com.pakpobox.cleanpro.bean.price.Price;
import com.pakpobox.cleanpro.net.callback.NetCallback;
import com.pakpobox.cleanpro.ui.mvp.presenter.BasePresenter;

import java.util.List;

/**
 * User:Sean.Wei
 * Date:2018/7/25
 * Time:17:34
 */

public class PricePresenter extends BasePresenter<PriceContract.IPriceView> implements PriceContract.IPricePresenter{
    private PriceContract.IPriceModel mModel;
    private PriceContract.IPriceView mPriceView;

    private Activity activity;

    public PricePresenter(Activity activity) {
        this.activity = activity;
        mModel = new PriceModel();
    }

    @Override
    public void getPrices() {
        NetCallback<List<Price>> callback = new NetCallback<List<Price>>(activity, this) {
            @Override
            protected void onSuccess(List<Price> data) {
                mPriceView = getView();
                if (null != data)
                    mPriceView.getSuccess(data);
                else
                    mPriceView.showFail(MyApplication.getContext().getString(R.string.app_unknown_error));
            }
        };
        mModel.getPrices(callback);
    }

}
