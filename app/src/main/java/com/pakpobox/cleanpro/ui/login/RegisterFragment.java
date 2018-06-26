package com.pakpobox.cleanpro.ui.login;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.base.BaseFragment;
import com.pakpobox.cleanpro.utils.StatusBarUtil;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 注册
 */
public class RegisterFragment extends BaseFragment {

    @BindView(R.id.forget_psw_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.forget_psw_mobile_et)
    EditText mMobileEt;
    @BindView(R.id.forget_psw_verifycation_et)
    EditText mVerifycationEt;
    @BindView(R.id.forget_psw_verifycation_btn)
    Button mVerifycationBtn;
    @BindView(R.id.forget_psw_next_btn)
    Button mNextBtn;

    public static RegisterFragment newInstance() {
        Bundle args = new Bundle();
        RegisterFragment fragment = new RegisterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_register;
    }

    @Override
    protected void initViews(View view) {
        StatusBarUtil.setHeight(getContext(), mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pop();
            }
        });
    }

    @OnClick({R.id.forget_psw_country_code_btn, R.id.forget_psw_verifycation_btn, R.id.forget_psw_next_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.forget_psw_country_code_btn:
                break;
            case R.id.forget_psw_verifycation_btn:
                break;
            case R.id.forget_psw_next_btn:
                start(SetPSWFragment.newInstance());
                break;
        }
    }

}
