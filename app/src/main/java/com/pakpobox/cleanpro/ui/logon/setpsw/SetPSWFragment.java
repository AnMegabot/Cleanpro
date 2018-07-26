package com.pakpobox.cleanpro.ui.logon.setpsw;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.base.BasePresenterFragment;
import com.pakpobox.cleanpro.bean.Register;
import com.pakpobox.cleanpro.ui.logon.login.LoginFragment;
import com.pakpobox.cleanpro.utils.KeyBoardHelper;
import com.pakpobox.cleanpro.utils.StatusBarUtil;
import com.pakpobox.cleanpro.utils.ToastUtils;
import com.tuo.customview.VerificationCodeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 注册-设置密码
 */
public class SetPSWFragment extends BasePresenterFragment<SetPSWPresenter, SetPSWContract.ISetPSWView> implements SetPSWContract.ISetPSWView {

    @BindView(R.id.set_psw_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.set_psw_login_psw_et)
    EditText mLoginPswEt;
    @BindView(R.id.set_psw_enter_login_psw_et)
    EditText mEnterLoginPswEt;
    @BindView(R.id.set_psw_payment_psw_et)
    VerificationCodeView mPaymentPswEt;
    @BindView(R.id.set_psw_enter_payment_psw_et)
    VerificationCodeView mEnterPaymentPswEt;
    @BindView(R.id.set_psw_llt)
    LinearLayout mRootLayout;
    @BindView(R.id.set_psw_sign_up_btn)
    Button mSignUpBtn;

    private KeyBoardHelper keyBoardHelper;

    private String mPhoneNumber, mVerifyCode, mCountryCode;

    public static SetPSWFragment newInstance(String phoneNumber, String verifyCode, String countryCode) {
        Bundle args = new Bundle();
        args.putString("phoneNumber", phoneNumber);
        args.putString("verifyCode", verifyCode);
        args.putString("countryCode", countryCode);
        SetPSWFragment fragment = new SetPSWFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (null != bundle) {
            mPhoneNumber = bundle.getString("phoneNumber");
            mVerifyCode = bundle.getString("verifyCode");
            mCountryCode = bundle.getString("countryCode");
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_set_psw;
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

        keyBoardHelper = new KeyBoardHelper(getActivity());
        keyBoardHelper.setKeyboardListener(mRootLayout, null);

        //监听输入框内容变化
        mLoginPswEt.addTextChangedListener(new MyTextWatcher());
        mEnterLoginPswEt.addTextChangedListener(new MyTextWatcher());
        mPaymentPswEt.getEditText().addTextChangedListener(new MyTextWatcher());
        mEnterPaymentPswEt.getEditText().addTextChangedListener(new MyTextWatcher());

        mSignUpBtn.setEnabled(mPresenter.verifyPassword(false) && mPresenter.verifyPayPassword(false));
    }

    private class MyTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            mSignUpBtn.setEnabled(mPresenter.verifyPassword(false) && mPresenter.verifyPayPassword(false));
        }
    }

    @Override
    public String getPassword() {
        return mLoginPswEt.getText().toString().trim();
    }

    @Override
    public String getEnterPassword() {
        return mEnterLoginPswEt.getText().toString().trim();
    }

    @Override
    public String getPayPassword() {
        return mPaymentPswEt.getInputContent();
    }

    @Override
    public String getEnterPayPassword() {
        return mEnterPaymentPswEt.getInputContent();
    }

    @Override
    public Register getRegisterParam() {
        return new Register(mPhoneNumber, mPhoneNumber, mVerifyCode, getPassword(), getPayPassword(), mCountryCode);
    }

    @Override
    public void registerSuccess() {
        popTo(LoginFragment.class, false);
        ToastUtils.showToast(getContext(), R.string.login_signUp_success_warn);
    }

    @Override
    protected SetPSWPresenter createPresenter() {
        return new SetPSWPresenter(getActivity());
    }

    @OnClick(R.id.set_psw_sign_up_btn)
    public void onClick() {
        mPresenter.register();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        keyBoardHelper.removeKeyboardListener();
    }

}
