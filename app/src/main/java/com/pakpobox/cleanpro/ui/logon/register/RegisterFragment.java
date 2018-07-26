package com.pakpobox.cleanpro.ui.logon.register;


import android.os.Bundle;

import com.pakpobox.cleanpro.ui.logon.setpsw.SetPSWFragment;

/**
 * 注册
 */
public class RegisterFragment extends BaseVerifyFragment {

    public static RegisterFragment newInstance() {
        Bundle args = new Bundle();
        RegisterFragment fragment = new RegisterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected BaseVerifyPresenter createPresenter() {
        return new BaseVerifyPresenter(getActivity(), new RegisterModel());
    }

    @Override
    public void checkSuccess(String result) {
        start(SetPSWFragment.newInstance(getUserName(), getVerifyCode(), getCountryCode()));
    }
}
