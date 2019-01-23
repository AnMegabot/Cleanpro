package com.pakpobox.cleanpro.ui.account.register;


import android.os.Bundle;
import android.support.annotation.Nullable;
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
 * 注册-5
 */
public class RegisterPostcodeFragment extends BaseFragment {


    @BindView(R.id.app_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.register_postcode_et)
    EditText mPostcodeEt;
    @BindView(R.id.register_content_llt)
    ScrollView mContentLlt;
    @BindView(R.id.register_postcode_next_btn)
    Button mNextBtn;

    private KeyBoardHelper keyBoardHelper;

    private Register mRegister;

    public static RegisterPostcodeFragment newInstance(Register register) {
        Bundle args = new Bundle();
        args.putParcelable("register", register);
        RegisterPostcodeFragment fragment = new RegisterPostcodeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (null != bundle) {
            mRegister = bundle.getParcelable("register");
        }
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
        mPostcodeEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mNextBtn.setEnabled(verifyPostcode());
            }
        });
        mNextBtn.setEnabled(verifyPostcode());
    }

    private boolean verifyPostcode() {
        if (!TextUtils.isEmpty(mPostcodeEt.getText().toString().trim()))
            return true;
        return false;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_register_postcode;
    }

    @OnClick(R.id.register_postcode_next_btn)
    public void onClick() {
        if (null != mRegister)
            mRegister.setPostCode(mPostcodeEt.getText().toString().trim());
        start(RegisterMobileFragment.newInstance(mRegister));
    }

}
