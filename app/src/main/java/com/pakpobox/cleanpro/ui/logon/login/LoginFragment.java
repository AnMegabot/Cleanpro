package com.pakpobox.cleanpro.ui.logon.login;


import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;

import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.base.BasePresenterFragment;
import com.pakpobox.cleanpro.application.AppSetting;
import com.pakpobox.cleanpro.ui.logon.register.ForgetPSWFragment;
import com.pakpobox.cleanpro.ui.logon.register.RegisterStartFragment;
import com.pakpobox.cleanpro.utils.InputUtils;
import com.pakpobox.cleanpro.utils.KeyBoardHelper;
import com.pakpobox.cleanpro.utils.StatusBarUtil;
import com.pakpobox.cleanpro.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 登录
 */
public class LoginFragment extends BasePresenterFragment<LoginPresenter, LoginContract.ILoginView> implements LoginContract.ILoginView{

    @BindView(R.id.login_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.login_mobile_et)
    EditText mMobileEt;
    @BindView(R.id.login_password_et)
    EditText mPasswordEt;
    @BindView(R.id.login_sign_in_btn)
    Button mSignInBtn;
    @BindView(R.id.login_scrollview)
    ScrollView mScrollview;

    private KeyBoardHelper keyBoardHelper;

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
    public String getUserName() {
        return mMobileEt.getText().toString().trim();
    }

    @Override
    public String getPassWord() {
        return mPasswordEt.getText().toString().trim();
    }

    @Override
    public void loginSuccess() {
        ToastUtils.showToast(getContext(), R.string.login_signIn_success_warn);
        getActivity().finish();
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(getActivity());
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

        keyBoardHelper = new KeyBoardHelper(getActivity());
        keyBoardHelper.setKeyboardListener(mScrollview, null);

        InputUtils.setEditFilter(mMobileEt, new InputFilter.LengthFilter(11));
        //填充上次登录账号
        String lastPhoneNumb = AppSetting.getInstance().getLastPhoneNumb();
        if (!TextUtils.isEmpty(lastPhoneNumb)) {
            mMobileEt.setText(lastPhoneNumb);
            mMobileEt.setSelection(mMobileEt.getText().toString().trim().length());
        }

        //监听输入框内容变化
        mMobileEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mSignInBtn.setEnabled(mPresenter.verifyAccount());
            }
        });

        mPasswordEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mSignInBtn.setEnabled(mPresenter.verifyAccount());
            }
        });

        mSignInBtn.setEnabled(mPresenter.verifyAccount());
    }

    @OnClick({R.id.login_register_btn, R.id.login_sign_in_btn, R.id.login_forget_password_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_register_btn:
                start(RegisterStartFragment.newInstance());
                break;
            case R.id.login_sign_in_btn:
                mPresenter.login();
                break;
            case R.id.login_forget_password_btn:
                start(ForgetPSWFragment.newInstance());
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        keyBoardHelper.removeKeyboardListener();
    }
}
