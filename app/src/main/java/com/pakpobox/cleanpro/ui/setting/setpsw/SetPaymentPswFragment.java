package com.pakpobox.cleanpro.ui.setting.setpsw;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.base.BaseFragment;
import com.pakpobox.cleanpro.ui.booking.BookSuccessFragment;
import com.pakpobox.cleanpro.ui.setting.SettingFragment;
import com.pakpobox.cleanpro.utils.StatusBarUtil;
import com.tuo.customview.VerificationCodeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 设置支付密码
 */
public class SetPaymentPswFragment extends BaseFragment {

    @BindView(R.id.toolbar_title_tv)
    TextView mTitleTv;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.set_payment_psw_tips_tv)
    TextView mTipsTv;
    @BindView(R.id.set_payment_psw_view)
    VerificationCodeView mPswView;

    private int mStep = 1;
    public static SetPaymentPswFragment newInstance(int step) {
        Bundle args = new Bundle();
        args.putInt("step", step);
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
                            start(SetPaymentPswFragment.newInstance(2));
                            break;
                        case 2:
                            start(SetPaymentPswFragment.newInstance(3));
                            break;
                        case 3:
                            popTo(SettingFragment.class, false);
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
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_set_payment_psw;
    }

}
