package com.pakpobox.cleanpro.ui.account.register;


import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;

import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.base.BaseFragment;
import com.pakpobox.cleanpro.bean.Register;
import com.pakpobox.cleanpro.utils.KeyBoardHelper;
import com.pakpobox.cleanpro.utils.StatusBarUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 注册-2
 */
public class RegisterNameFragment extends BaseFragment {

    @BindView(R.id.app_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.register_first_name_et)
    EditText mFirstNameEt;
    @BindView(R.id.register_last_name_et)
    EditText mLastNameEt;
    @BindView(R.id.register_content_llt)
    ScrollView mContentLlt;
    @BindView(R.id.register_name_next_btn)
    Button mNextBtn;

    private KeyBoardHelper keyBoardHelper;

    public static RegisterNameFragment newInstance() {
        Bundle args = new Bundle();
        RegisterNameFragment fragment = new RegisterNameFragment();
        fragment.setArguments(args);
        return fragment;
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

        //监听输入框内容变化
        mFirstNameEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mNextBtn.setEnabled(verifyName());
            }
        });

        mLastNameEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mNextBtn.setEnabled(verifyName());
            }
        });
        mNextBtn.setEnabled(verifyName());
    }

    private boolean verifyName() {
        if (!TextUtils.isEmpty(mFirstNameEt.getText().toString().trim()) && !TextUtils.isEmpty(mLastNameEt.getText().toString().trim()))
            return true;
        return false;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_register_name;
    }

    @OnClick(R.id.register_name_next_btn)
    public void onClick() {
        Register register = new Register();
        register.setFirstName(mFirstNameEt.getText().toString().trim());
        register.setLastName(mLastNameEt.getText().toString().trim());
        start(RegisterBirthdayFragment.newInstance(register));
    }
}