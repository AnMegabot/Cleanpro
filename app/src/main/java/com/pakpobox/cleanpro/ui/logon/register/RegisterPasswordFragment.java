package com.pakpobox.cleanpro.ui.logon.register;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;

import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.base.BaseFragment;
import com.pakpobox.cleanpro.bean.Register;
import com.pakpobox.cleanpro.utils.KeyBoardHelper;
import com.pakpobox.cleanpro.utils.StatusBarUtil;
import com.pakpobox.cleanpro.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 注册-7
 */
public class RegisterPasswordFragment extends BaseFragment {

    @BindView(R.id.app_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.register_password_et)
    EditText mPasswordEt;
    @BindView(R.id.register_password_confirm_et)
    EditText mPasswordConfEt;
    @BindView(R.id.register_content_llt)
    ScrollView mContentLlt;
    @BindView(R.id.register_password_next_btn)
    Button mNextBtn;

    private KeyBoardHelper keyBoardHelper;

    private Register mRegister;

    public static RegisterPasswordFragment newInstance(Register register) {
        Bundle args = new Bundle();
        args.putParcelable("register", register);
        RegisterPasswordFragment fragment = new RegisterPasswordFragment();
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
        mPasswordEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mNextBtn.setEnabled(verifyPassword());
            }
        });

        mPasswordConfEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mNextBtn.setEnabled(verifyPassword());
            }
        });
        mNextBtn.setEnabled(verifyPassword());
    }

    private boolean verifyPassword() {
        if (!TextUtils.isEmpty(mPasswordEt.getText().toString().trim()) && !TextUtils.isEmpty(mPasswordConfEt.getText().toString().trim()))
            return true;
        return false;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_register_password;
    }

    @OnClick(R.id.register_password_next_btn)
    public void onClick() {
        String password = mPasswordEt.getText().toString().trim();
        String passwordConfirm = mPasswordConfEt.getText().toString().trim();
        if (password.length() < 6) {
            ToastUtils.showToast(getContext(), R.string.register_set_psw_length_error);
            return;
        }
        if (!passwordConfirm.equals(password)) {
            ToastUtils.showToast(getContext(), R.string.register_set_psw_inconsistent_error);
            return;
        }

        if (null != mRegister) {
            mRegister.setPassword(mPasswordConfEt.getText().toString().trim());
        }
        start(RegisterInvitationFragment.newInstance(mRegister));
    }
}
