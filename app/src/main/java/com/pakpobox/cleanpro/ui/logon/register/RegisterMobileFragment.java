package com.pakpobox.cleanpro.ui.logon.register;


import android.os.Bundle;
import android.support.annotation.Nullable;

import com.pakpobox.cleanpro.bean.Register;

/**
 * 注册-6
 */
public class RegisterMobileFragment extends BaseVerifyFragment{

    private Register mRegister;

    public static RegisterMobileFragment newInstance(Register register) {
        Bundle args = new Bundle();
        args.putParcelable("register", register);
        RegisterMobileFragment fragment = new RegisterMobileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (null != bundle) {
            mRegister = bundle.getParcelable("register");
        }
    }

    @Override
    public void checkSuccess(String result) {
        if (null != mRegister) {
            mRegister.setCountryCode(countryCode);
            mRegister.setPhoneNumber(mMobileEt.getText().toString().trim());
            mRegister.setLoginName(mMobileEt.getText().toString().trim());
            mRegister.setRandomPassword(mVerifycationEt.getText().toString().trim());
        }
        start(RegisterPasswordFragment.newInstance(mRegister));
    }

    @Override
    protected VerifyMobilePresenter createPresenter() {
        return new VerifyMobilePresenter(getActivity(), 0);
    }
}
