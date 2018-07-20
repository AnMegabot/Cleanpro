package com.pakpobox.cleanpro.ui.setting.forgetpsw;


import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.base.BaseFragment;
import com.pakpobox.cleanpro.ui.setting.setpsw.SetPaymentPswFragment;
import com.pakpobox.cleanpro.utils.StatusBarUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 忘记支付密码
 */
public class ForgetPaymentPswFragment extends BaseFragment {

    @BindView(R.id.toolbar_title_tv)
    TextView mTitleTv;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.forget_payment_psw_mobile_et)
    EditText mMobileEt;
    @BindView(R.id.forget_payment_psw_verifycation_et)
    EditText mVerifycationEt;
    @BindView(R.id.forget_payment_psw_verifycation_btn)
    Button mVerifycationBtn;
    @BindView(R.id.forget_payment_psw_next_btn)
    Button mNextBtn;

    public static ForgetPaymentPswFragment newInstance() {
        Bundle args = new Bundle();
        ForgetPaymentPswFragment fragment = new ForgetPaymentPswFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_forget_payment_psw;
    }

    @Override
    protected void initViews(View view) {
        StatusBarUtil.setHeight(getContext(), mToolbar);
        mTitleTv.setText(getString(R.string.setting));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pop();
            }
        });
    }

    @OnClick({R.id.forget_payment_psw_country_code_btn, R.id.forget_payment_psw_verifycation_btn, R.id.forget_payment_psw_next_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.forget_payment_psw_country_code_btn:
                break;
            case R.id.forget_payment_psw_verifycation_btn:
                break;
            case R.id.forget_payment_psw_next_btn:
                start(SetPaymentPswFragment.newInstance(2));
                break;
        }
    }

}