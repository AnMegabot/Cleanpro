package com.pakpobox.cleanpro.ui.logon.register;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.PopupMenu;
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
 * 注册-6
 */
public class RegisterMobileFragment extends BasePresenterFragment<RegisterPresenter, RegisterContract.IRegisterView> implements RegisterContract.IRegisterView{

    @BindView(R.id.register_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.register_mobile_et)
    EditText mMobileEt;
    @BindView(R.id.register_verifycation_et)
    EditText mVerifycationEt;
    @BindView(R.id.register_verifycation_btn)
    Button mVerifycationBtn;
    @BindView(R.id.register_mobile_next_btn)
    Button mNextBtn;
    @BindView(R.id.register_content_llt)
    ScrollView mContentLlt;
    @BindView(R.id.register_country_code_btn)
    CheckBox mCountryCodeBtn;

    private String countryCode = "86";

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
                if (!TextUtils.isEmpty(mMobileEt.getText().toString().trim()))
                    mVerifycationBtn.setEnabled(true);
                else
                    mVerifycationBtn.setEnabled(false);

                mVerifycationBtn.setText(getString(R.string.login_getCode));
            }
        }
    };

    public static RegisterMobileFragment newInstance() {
        Bundle args = new Bundle();
        RegisterMobileFragment fragment = new RegisterMobileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new Handler();
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
        start(RegisterPasswordFragment.newInstance());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_register_mobile;
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
                mVerifycationBtn.setEnabled(!hasGetSignCode && verifyAccount());
                mNextBtn.setEnabled(verifyAccount() && verifyCode());
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
                mNextBtn.setEnabled(verifyAccount() && verifyCode());
            }
        });

        mVerifycationBtn.setEnabled(!hasGetSignCode && verifyAccount());
        mNextBtn.setEnabled(verifyAccount() && verifyCode());
    }

    @OnClick({R.id.register_country_code_btn, R.id.register_verifycation_btn, R.id.register_mobile_next_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_country_code_btn:
                final PopupMenu popupMenu;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                    popupMenu = new PopupMenu(_mActivity, mCountryCodeBtn, GravityCompat.START);
                } else {
                    popupMenu = new PopupMenu(_mActivity, mCountryCodeBtn);
                }
                popupMenu.inflate(R.menu.country_code_pop);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        mCountryCodeBtn.setText(item.getTitle());
                        switch (item.getItemId()) {
                            case R.id.country_code_china:
                                countryCode = "86";
                                break;
                            case R.id.country_code_malaysia:
                                countryCode = "60";
                                break;
                        }
                        popupMenu.dismiss();
                        return true;
                    }
                });
                popupMenu.show();
                break;
            case R.id.register_verifycation_btn:
                mPresenter.getVerifyCode(mMobileEt.getText().toString().trim(), countryCode);
                break;
            case R.id.register_mobile_next_btn:
//                mPresenter.checkVerifyCode(mMobileEt.getText().toString().trim(), mVerifycationEt.getText().toString().trim());
                start(RegisterPasswordFragment.newInstance());
                break;
        }
    }

    public boolean verifyAccount() {
        if (TextUtils.isEmpty(mMobileEt.getText().toString().trim())) {
            return false;
        }
        return true;
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

    @Override
    protected RegisterPresenter createPresenter() {
        return new RegisterPresenter(getActivity(), 0);
    }
}
