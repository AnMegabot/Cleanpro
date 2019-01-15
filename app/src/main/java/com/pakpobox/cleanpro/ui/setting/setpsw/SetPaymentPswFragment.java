package com.pakpobox.cleanpro.ui.setting.setpsw;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.base.BasePresenterFragment;
import com.pakpobox.cleanpro.ui.setting.SettingFragment;
import com.pakpobox.cleanpro.utils.StatusBarUtil;
import com.tuo.customview.VerificationCodeView;

import butterknife.BindView;

/**
 * 设置支付密码
 */
public class SetPaymentPswFragment extends BasePresenterFragment<SetPaymentPswPresenter, SetPaymentPswContract.ISetPaymentPswView> implements SetPaymentPswContract.ISetPaymentPswView {

    @BindView(R.id.toolbar_title_tv)
    TextView mTitleTv;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.set_payment_psw_tips_tv)
    TextView mTipsTv;
    @BindView(R.id.set_payment_psw_view)
    VerificationCodeView mPswView;

    private int mStep = 1;
    private String mOldPassword = null;
    private String mNewPassword1 = null;
    private String mToken = null;
    public static SetPaymentPswFragment newInstance(int step, String oldPassword, String newPassword1, String token) {
        Bundle args = new Bundle();
        args.putInt("step", step);
        args.putString("oldPassword", oldPassword);
        args.putString("newPassword1", newPassword1);
        args.putString("token", token);
        SetPaymentPswFragment fragment = new SetPaymentPswFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (null != bundle) {
            mStep = bundle.getInt("step");
            mOldPassword = bundle.getString("oldPassword");
            mNewPassword1 = bundle.getString("newPassword1");
            mToken = bundle.getString("token");
        }
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

        mPswView.setInputCompleteListener(new VerificationCodeView.InputCompleteListener() {
            @Override
            public void inputComplete() {
                if (mPswView.getInputContent().length() >= 6) {
                    switch (mStep) {
                        case 1:
                            mPresenter.checkPayPsw();
                            break;
                        case 2:
                            start(SetPaymentPswFragment.newInstance(3, getOldPassword(), getNewPassword1(), getToken()));
                            break;
                        case 3:
                            mPresenter.resetPayPsw();
                            break;
                    }
                }
            }

            @Override
            public void deleteContent() {

            }
        });

        switch (mStep) {
            case 1:
                mTipsTv.setText(getString(R.string.setting_enter_payment_psw));
                break;
            case 2:
                mTipsTv.setText(getString(R.string.setting_new_psw));
                break;
            case 3:
                mTipsTv.setText(getString(R.string.setting_comfirm_new_psw));
                break;
        }

        mPswView.getEditText().requestFocus();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_set_payment_psw;
    }

    @Override
    public String getOldPassword() {
        switch (mStep) {
            case 1:
                return mPswView.getInputContent().trim();
            case 2:
                return mOldPassword;
            case 3:
                return mOldPassword;
        }
        return null;
    }

    @Override
    public String getNewPassword1() {
        switch (mStep) {
            case 1:
                return mNewPassword1;
            case 2:
                return mPswView.getInputContent().trim();
            case 3:
                return mNewPassword1;
        }
        return null;
    }

    @Override
    public String getNewPassword2() {
        switch (mStep) {
            case 1:
                return null;
            case 2:
                return null;
            case 3:
                return mPswView.getInputContent().trim();
        }
        return null;
    }

    @Override
    public String getToken() {
        return mToken;
    }

    @Override
    public void checkPayPswSuccess() {
        start(SetPaymentPswFragment.newInstance(2, getOldPassword(), null, getToken()));
    }

    @Override
    public void resetPayPswSuccess(String result) {
        popTo(SettingFragment.class, false);
    }

    @Override
    protected SetPaymentPswPresenter createPresenter() {
        return new SetPaymentPswPresenter(getActivity());
    }
}
