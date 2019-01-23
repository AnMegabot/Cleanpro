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
 * Date:2019/1/23
 * Time:17:47
 */

public class UpdatePostcodePresenter extends BasePresenter<PersonalInfoContract.IUpdatePostcodeInfoView> implements PersonalInfoContract.IUpdatePostcodePresenter  {
    private IAccountModel mAccountModel;

    private Activity activity;

    public UpdatePostcodePresenter(Activity activity) {
        this.activity = activity;
        mAccountModel = new AccountModel();
        addModel((IModel) mAccountModel);
    }

    @Override
    public void updatePostcode(String postcode) {
        JSONObject requestObj = new JSONObject();
        try {
            requestObj.put("postCode", postcode);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        BaseNetCallback<UserBean> callback = new BaseNetCallback<UserBean>(activity, this) {
            @Override
            protected void onSuccess(UserBean data) {
                getView().updatePostcodeSuccess(data);
            }
        };
        mAccountModel.updateProfile(requestObj.toString(), callback);
    }
}
