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
import com.pakpobox.cleanpro.base.BasePresenterFragment;
import com.pakpobox.cleanpro.bean.Register;
import com.pakpobox.cleanpro.bean.UserBean;
import com.pakpobox.cleanpro.ui.account.login.LoginFragment;
import com.pakpobox.cleanpro.utils.KeyBoardHelper;
import com.pakpobox.cleanpro.utils.StatusBarUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 注册-6
 */
public class RegisterInvitationFragment extends BasePresenterFragment<RegisterPresenter, RegisterContract.IRegisterView> implements RegisterContract.IRegisterView {


    @BindView(R.id.app_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.register_invitation_code)
    EditText mCodeEt;
    @BindView(R.id.register_content_llt)
    ScrollView mContentLlt;
    @BindView(R.id.register_invitate_complete_btn)
    Button mNextBtn;

    private KeyBoardHelper keyBoardHelper;

    private Register mRegister;
    private String invitateCode;

    public static RegisterInvitationFragment newInstance(Register register) {
        Bundle args = new Bundle();
        args.putParcelable("register", register);
        RegisterInvitationFragment fragment = new RegisterInvitationFragment();
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

        mCodeEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mNextBtn.setEnabled(verifyPostcode());
                String code = mCodeEt.getText().toString().trim();
                if (6==code.length() && !verifyPostcode()) {
                    mPresenter.checkInviteCode(code);
                }
            }
        });
        mNextBtn.setEnabled(verifyPostcode());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_register_invitation;
    }

    @Override
    public void checkInviteCodeSuccess(String result) {
        invitateCode = result;
        mNextBtn.setEnabled(verifyPostcode());
    }

    private boolean verifyPostcode() {
        String code = mCodeEt.getText().toString().trim();
        if (!TextUtils.isEmpty(code) && code.equals(invitateCode))
            return true;
        return false;
    }

    @Override
    public void registerSuccess(UserBean userBean) {
        popTo(LoginFragment.class, false);
    }

    @Override
    protected RegisterPresenter createPresenter() {
        return new RegisterPresenter(getActivity());
    }

    @OnClick({R.id.register_invitate_complete_btn, R.id.register_invitation_skip_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_invitate_complete_btn:
                mRegister.setInviteCode(invitateCode);
                mPresenter.register(mRegister);
                break;
            case R.id.register_invitation_skip_btn:
                mRegister.setInviteCode(null);
                mPresenter.register(mRegister);
                break;
        }
    }
}