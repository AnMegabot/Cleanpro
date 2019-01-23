package com.pakpobox.cleanpro.ui.my.personal;

import android.app.Activity;

import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.application.MyApplication;
import com.pakpobox.cleanpro.bean.Order;
import com.pakpobox.cleanpro.bean.Result;
import com.pakpobox.cleanpro.bean.ResultBean;
import com.pakpobox.cleanpro.bean.UserBean;
import com.pakpobox.cleanpro.net.callback.BaseNetCallback;
import com.pakpobox.cleanpro.ui.mvp.model.IAccountModel;
import com.pakpobox.cleanpro.ui.mvp.model.IModel;
import com.pakpobox.cleanpro.ui.mvp.model.IOrderModel;
import com.pakpobox.cleanpro.ui.mvp.model.impl.AccountModel;
import com.pakpobox.cleanpro.ui.mvp.model.impl.OrderModel;
import com.pakpobox.cleanpro.ui.mvp.presenter.BasePresenter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

/**
 * User:Sean.Wei
 * Date:2018/7/25
 * Time:17:34
 */

public class PersonalInfoPresenter extends BasePresenter<PersonalInfoContract.IPersonalInfoView> implements PersonalInfoContract.IPersonalInfoPresenter {
    private IAccountModel mAccountModel;

    private Activity activity;

    public PersonalInfoPresenter(Activity activity) {
        this.activity = activity;
        mAccountModel = new AccountModel();
        addModel((IModel) mAccountModel);
    }

    @Override
    public void uploadHeadImage(String filePaht) {
        BaseNetCallback<ResultBean> callback = new BaseNetCallback<ResultBean>(activity, this) {
            @Override
            protected void onSuccess(ResultBean data) {
                getView().uploadHeadImageSuccess(data);
            }
        };
        mAccountModel.uploadHeadImage(filePaht, callback);
    }

    @Override
    public void updateGender(String gender) {
        JSONObject requestObj = new JSONObject();
        try {
            requestObj.put("gender", gender);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        BaseNetCallback<UserBean> callback = new BaseNetCallback<UserBean>(activity, this) {
            @Override
            protected void onSuccess(UserBean data) {
                getView().updateProfileSuccess(data);
            }
        };
        mAccountModel.updateProfile(requestObj.toString(), callback);
    }

    @Override
    public void updateBirthday(long birthday) {
        JSONObject requestObj = new JSONObject();
        try {
            requestObj.put("birthday", birthday);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        BaseNetCallback<UserBean> callback = new BaseNetCallback<UserBean>(activity, this) {
            @Override
            protected void onSuccess(UserBean data) {
                getView().updateProfileSuccess(data);
            }
        };
        mAccountModel.updateProfile(requestObj.toString(), callback);
    }
}
