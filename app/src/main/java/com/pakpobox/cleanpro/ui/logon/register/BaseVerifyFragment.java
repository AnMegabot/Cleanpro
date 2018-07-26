package com.pakpobox.cleanpro.ui.logon.register;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
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
import com.pakpobox.cleanpro.utils.InputUtils;
import com.pakpobox.cleanpro.utils.KeyBoardHelper;
import com.pakpobox.cleanpro.utils.StatusBarUtil;
import com.pakpobox.cleanpro.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * User:Sean.Wei
 * Date:2018/7/25
 * Time:14:32
 */

public abstract class BaseVerifyFragment extends BasePresenterFragment<BaseVerifyPresenter, BaseVerifyContract.IVerifyView> implements BaseVerifyContract.IVerifyView {

    @BindView(R.id.register_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.register_mobile_et)
    EditText mMobileEt;
    @BindView(R.id.register_verifycation_et)
    EditText mVerifycationEt;
    @BindView(R.id.register_verifycation_btn)
    Button mVerifycationBtn;
    @BindView(R.id.register_next_btn)
    Button mNextBtn;
    @BindView(R.id.register_content_llt)
    ScrollView mContentLlt;

    private KeyBoardHelper keyBoardHelper;

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
                if (!TextUtils.isEmpty(getUserName()))
                    mVerifycationBtn.setEnabled(true);
                else
                    mVerifycationBtn.setEnabled(false);

                mVerifycationBtn.setText(getString(R.string.login_getCode));
            }
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new Handler();
    }

    @Override
    public String getUserName() {
        return mMobileEt.getText().toString().trim();
    }

    @Override
    public String getVerifyCode() {
        return mVerifycationEt.getText().toString().trim();
    }

    @Override
    public String getCountryCode() {
        return "86";
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

        keyBoardHelper = new KeyBoardHelper(getActivity());
        keyBoardHelper.setKeyboardListener(mContentLlt, null);

        //限制输入最大个数
        InputUtils.setEditFilter(mMobileEt, new InputFilter.LengthFilter(11));
        InputUtils.setEditFilter(mVerifycationEt, new InputFilter.LengthFilter(6));

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
                mVerifycationBtn.setEnabled(!hasGetSignCode && mPresenter.verifyAccount());
                mNextBtn.setEnabled(mPresenter.verifyAccount() && mPresenter.verifyCode());
            }
        });

        mVerifycationEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mNextBtn.setEnabled(mPresenter.verifyAccount() && mPresenter.verifyCode());
            }
        });

        mVerifycationBtn.setEnabled(!hasGetSignCode && mPresenter.verifyAccount());
        mNextBtn.setEnabled(mPresenter.verifyAccount() && mPresenter.verifyCode());
    }

    @OnClick({R.id.register_country_code_btn, R.id.register_verifycation_btn, R.id.register_next_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_country_code_btn:

                break;
            case R.id.register_verifycation_btn:
                mPresenter.getVerifyCode();
                break;
            case R.id.register_next_btn:
                mPresenter.checkVerifyCode();
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        keyBoardHelper.removeKeyboardListener();

        if (null != mHandler)
            mHandler.removeCallbacks(countdownTask);
    }
}
