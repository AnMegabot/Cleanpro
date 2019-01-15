package com.pakpobox.cleanpro.ui.price;

import android.app.Activity;

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

public class PricePresenter extends BasePresenter<PriceContract.IPriceView> implements PriceContract.IPricePresenter{
    private ICommonModel mModel;

    private Activity activity;

    public PricePresenter(Activity activity) {
        this.activity = activity;
        mModel = new CommonModel();
        addModel((IModel) mModel);
    }

    @Override
    public void getPrices() {
        BaseNetCallback<List<Price>> callback = new BaseNetCallback<List<Price>>(activity, this){
            @Override
            protected void onSuccess(List<Price> data) {
                super.onSuccess(data);
                getView().clearListData();
                getView().setData(data);
                if (getView().getData().size() == 0)
                    getView().showEmpty();
                else
                    getView().showContent();
            }
        };
        mModel.getPrices(callback);
    }

}
