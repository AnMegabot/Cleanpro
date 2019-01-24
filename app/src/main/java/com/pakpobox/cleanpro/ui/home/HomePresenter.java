package com.pakpobox.cleanpro.ui.home;

import android.app.Activity;

import com.pakpobox.cleanpro.application.AppSetting;
import com.pakpobox.cleanpro.bean.UserBean;
import com.pakpobox.cleanpro.bean.location.Site;
import com.pakpobox.cleanpro.net.callback.BaseNetCallback;
import com.pakpobox.cleanpro.net.callback.PageListNetCallback;
import com.pakpobox.cleanpro.ui.mvp.model.IAccountModel;
import com.pakpobox.cleanpro.ui.mvp.model.ICommonModel;
import com.pakpobox.cleanpro.ui.mvp.model.IModel;
import com.pakpobox.cleanpro.ui.mvp.model.impl.AccountModel;
import com.pakpobox.cleanpro.ui.mvp.model.impl.CommonModel;
import com.pakpobox.cleanpro.ui.mvp.presenter.BasePresenter;

import org.greenrobot.eventbus.EventBus;

/**
 * User:Sean.Wei
 * Date:2018/6/21
 * Time:17:08
 */

public class HomePresenter extends BasePresenter<HomeContract.IHomeView> implements HomeContract.IHomePresenter {
    private ICommonModel mModel;
    private IAccountModel mAccountModel;
    private Activity activity;

    public HomePresenter(Activity activity) {
        this.activity = activity;
        mModel = new CommonModel();
        addModel((IModel) mModel);
        mAccountModel = new AccountModel();
        addModel((IModel) mAccountModel);
    }

    @Override
    public void getPromosList() {
        BaseNetCallback<String> callback = new BaseNetCallback<String>(activity, this){
            @Override
            protected void onSuccess(String data) {
                super.onSuccess(data);
            }
        };
        mModel.getLocations(getView().getPage(), 20, new PageListNetCallback<Site>(activity, this){});
//        mModel.getLocations(getView().getPage(), 20, callback);
    }

    @Override
    public void getUserInfo() {
        if (!AppSetting.isLogin())
            return;

        BaseNetCallback<UserBean> callback = new BaseNetCallback<UserBean>(activity, this){
            @Override
            protected void onSuccess(UserBean data) {
                super.onSuccess(data);
                if (null != data) {
                    AppSetting.saveUserInfo(data);
                    AppSetting.saveLoginToken(data.getToken());
                    EventBus.getDefault().post(data);
                    getView().getUserInfoSuccess(data);
                }
            }
        };
        mAccountModel.getUserInfo(callback);
    }
}
