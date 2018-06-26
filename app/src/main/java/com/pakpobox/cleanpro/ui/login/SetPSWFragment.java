package com.pakpobox.cleanpro.ui.login;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.base.BaseFragment;
import com.pakpobox.cleanpro.ui.widget.PswInputView;
import com.pakpobox.cleanpro.utils.StatusBarUtil;
import com.tuo.customview.VerificationCodeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 设置密码
 */
public class SetPSWFragment extends BaseFragment {

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

    public static SetPSWFragment newInstance() {
        Bundle args = new Bundle();
        SetPSWFragment fragment = new SetPSWFragment();
        fragment.setArguments(args);
        return fragment;
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
    }

}