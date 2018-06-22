package com.pakpobox.cleanpro.ui.login;


import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.base.BaseFragment;
import com.pakpobox.cleanpro.utils.StatusBarUtil;
import com.pakpobox.cleanpro.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 登录
 */
public class LoginFragment extends BaseFragment {


    @BindView(R.id.login_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.login_mobile_et)
    EditText mMobileEt;
    @BindView(R.id.login_password_et)
    EditText mPasswordEt;
    @BindView(R.id.login_sign_in_btn)
    Button mSignInBtn;
    Unbinder unbinder;

    public static LoginFragment newInstance() {
        Bundle args = new Bundle();
        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    protected void initViews(View view) {
        StatusBarUtil.setHeight(getContext(), mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
    }

    @OnClick({R.id.login_register_btn, R.id.login_sign_in_btn, R.id.login_forget_password_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_register_btn:
                start(RegisterFragment.newInstance());
                break;
            case R.id.login_sign_in_btn:
                break;
            case R.id.login_forget_password_btn:
                start(ForgetPSWFragment.newInstance());
                break;
        }
    }
}
