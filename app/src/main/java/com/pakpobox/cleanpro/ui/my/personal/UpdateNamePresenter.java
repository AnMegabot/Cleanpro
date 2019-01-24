package com.pakpobox.cleanpro.ui.my.personal;

import android.app.Activity;

import com.pakpobox.cleanpro.bean.UserBean;
import com.pakpobox.cleanpro.net.callback.BaseNetCallback;
import com.pakpobox.cleanpro.ui.mvp.model.IAccountModel;
import com.pakpobox.cleanpro.ui.mvp.model.IModel;
import com.pakpobox.cleanpro.ui.mvp.model.impl.AccountModel;
import com.pakpobox.cleanpro.ui.mvp.presenter.BasePresenter;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * User:Sean.Wei
 * Date:2019/1/24
 * Time:18:19
 */

public class UpdateNamePresenter extends BasePresenter<PersonalInfoContract.IUpdateNameView> implements PersonalInfoContract.IUpdateNamePresenter  {
    private IAccountModel mAccountModel;

    private Activity activity;

    public UpdateNamePresenter(Activity activity) {
        this.activity = activity;
        mAccountModel = new AccountModel();
        addModel((IModel) mAccountModel);
    }

    @Override
    public void updateName(String firstName, String lastName) {
        JSONObject requestObj = new JSONObject();
        try {
            requestObj.put("firstName", firstName);
            requestObj.put("lastName", lastName);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        BaseNetCallback<UserBean> callback = new BaseNetCallback<UserBean>(activity, this) {
            @Override
            protected void onSuccess(UserBean data) {
                getView().updateNameSuccess(data);
            }
        };
        mAccountModel.updateProfile(requestObj.toString(), callback);
    }
}
