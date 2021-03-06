package com.pakpobox.cleanpro.ui.account.register;


import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.application.MyApplication;
import com.pakpobox.cleanpro.bean.ChangePSWToken;
import com.pakpobox.cleanpro.ui.account.changepsw.ChangePSWFragment;

/**
 * 忘记登录密码
 */
public class ForgetPSWFragment extends BaseVerifyFragment {

    public static ForgetPSWFragment newInstance() {
        Bundle args = new Bundle();
        ForgetPSWFragment fragment = new ForgetPSWFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected VerifyMobilePresenter createPresenter() {
        return new VerifyMobilePresenter(getActivity(), 1);
    }

    @Override
    public void checkSuccess(String result) {
        try {
            ChangePSWToken tokenBean = new Gson().fromJson(result, ChangePSWToken.class);
            start(ChangePSWFragment.newInstance(tokenBean.getToken()));
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            showFail(MyApplication.getContext().getString(R.string.app_unknown_error));
        }
    }
}
