package com.pakpobox.cleanpro.ui.account.paypsw;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.application.AppSetting;
import com.pakpobox.cleanpro.application.MyApplication;
import com.pakpobox.cleanpro.base.BasePresenterFragment;
import com.pakpobox.cleanpro.bean.ChangePSWToken;
import com.pakpobox.cleanpro.bean.UserBean;
import com.pakpobox.cleanpro.ui.account.register.RegisterContract;
import com.pakpobox.cleanpro.ui.account.register.VerifyMobilePresenter;
import com.pakpobox.cleanpro.utils.InputUtils;
import com.pakpobox.cleanpro.utils.KeyBoardHelper;
import com.pakpobox.cleanpro.utils.StatusBarUtil;
import com.pakpobox.cleanpro.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 忘记支付密码
 */
public class ForgetPaymentPswFragment extends BasePresenterFragment<VerifyMobilePresenter, RegisterContract.IVerifyMobileView> implements RegisterContract.IVerifyMobileView {

    @BindView(R.id.app_toolbar_title_tv)
    TextView mTitleTv;
    @BindView(R.id.app_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.forget_payment_psw_mobile_tv)
    TextView mMobileTv;
    @BindView(R.id.app_verifycation_et)
    EditText mVerifycationEt;
    @BindView(R.id.app_verifycation_btn)
    Button mVerifycationBtn;
    @BindView(R.id.forget_payment_psw_next_btn)
    Button mNextBtn;
    @BindView(R.id.forget_payment_psw_content_llt)
    ScrollView mContentLlt;


    private KeyBoardHelper keyBoardHelper;

    private String countryCode = "86";

    private Handler mHandler;
    private int countdownTime = 60;
    private boolean hasGetSignCode = false;
    private Runnable countdownTask = new Runnable() {
        @Override
        public void run() {
            mVerifycationBtn.setText(getString(R.string.login_valitecode_time, countdownTime));
            --countdownTime;
            if (countdownTime >= 0) {
                mHandler.postDelayed(countdownTask, 1000);
            } else {
                hasGetSignCode = false;
                mVerifycationBtn.setEnabled(true);
                mVerifycationBtn.setText(getString(R.string.login_getCode));
            }
        }
    };

    public static ForgetPaymentPswFragment newInstance() {
        Bundle args = new Bundle();
        ForgetPaymentPswFragment fragment = new ForgetPaymentPswFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new Handler();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_forget_payment_psw;
    }

    @Override
    protected void initViews(View view) {
        StatusBarUtil.setHeight(getContext(), mToolbar);
        mTitleTv.setText(getString(R.string.wallet_payment_setting));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pop();
            }
        });

        keyBoardHelper = new KeyBoardHelper(getActivity());
        keyBoardHelper.setKeyboardListener(mContentLlt, null);

        //限制输入最大个数
        InputUtils.setEditFilter(mVerifycationEt, new InputFilter.LengthFilter(6));

        mVerifycationEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mNextBtn.setEnabled(verifyCode());
            }
        });

        mVerifycationBtn.setEnabled(!hasGetSignCode);
        mNextBtn.setEnabled(verifyCode());
    }

    @Override
    public void getSuccess(String result) {
        hasGetSignCode = true;
        countdownTime = 60;
        mVerifycationBtn.setEnabled(false);
        mHandler.post(countdownTask);
        ToastUtils.showToast(getContext(), R.string.login_get_validecode_success);
    }

    @Override
    public void checkSuccess(String result) {
        try {
            ChangePSWToken tokenBean = new Gson().fromJson(result, ChangePSWToken.class);
            start(SetPaymentPswFragment.newInstance(tokenBean.getToken(), null));
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            showFail(MyApplication.getContext().getString(R.string.app_unknown_error));
        }
    }

    @Override
    protected VerifyMobilePresenter createPresenter() {
        return new VerifyMobilePresenter(getActivity(), 1);
    }

    public boolean verifyCode() {
        if (TextUtils.isEmpty(mVerifycationEt.getText().toString().trim())) {
            return false;
        }
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        keyBoardHelper.removeKeyboardListener();

        if (null != mHandler)
            mHandler.removeCallbacks(countdownTask);
    }

    @OnClick({R.id.app_verifycation_btn, R.id.forget_payment_psw_next_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.app_verifycation_btn:
                if (null != AppSetting.getUserInfo())
                    mPresenter.getVerifyCode(AppSetting.getUserInfo().getPhoneNumber(), countryCode);
                break;
            case R.id.forget_payment_psw_next_btn:
                if (null != AppSetting.getUserInfo())
                    mPresenter.checkVerifyCode(AppSetting.getUserInfo().getPhoneNumber(), mVerifycationEt.getText().toString().trim());
                break;
        }
    }
}