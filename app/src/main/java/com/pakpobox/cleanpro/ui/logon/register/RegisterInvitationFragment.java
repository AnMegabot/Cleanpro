package com.pakpobox.cleanpro.ui.logon.register;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ScrollView;

import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.base.BasePresenterFragment;
import com.pakpobox.cleanpro.bean.Register;
import com.pakpobox.cleanpro.bean.UserBean;
import com.pakpobox.cleanpro.ui.logon.login.LoginFragment;
import com.pakpobox.cleanpro.ui.widget.PasswordView;
import com.pakpobox.cleanpro.utils.KeyBoardHelper;
import com.pakpobox.cleanpro.utils.StatusBarUtil;
import com.pakpobox.logger.Logger;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 注册-6
 */
public class RegisterInvitationFragment extends BasePresenterFragment<RegisterPresenter, RegisterContract.IRegisterView> implements RegisterContract.IRegisterView {


    @BindView(R.id.register_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.register_invitation_password)
    PasswordView mPasswordView;
    @BindView(R.id.register_content_llt)
    ScrollView mContentLlt;

    private KeyBoardHelper keyBoardHelper;

    private Register mRegister;

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

        mPasswordView.setPasswordListener(new PasswordView.PasswordListener() {
            @Override
            public void passwordChange(String changeText) {
                Logger.d("changeText = " + changeText);
            }

            @Override
            public void passwordComplete() {
                Logger.d("passwordComplete");
                String invitateCode = mPasswordView.getPassword();
                mPresenter.checkInviteCode(invitateCode);
            }

            @Override
            public void keyEnterPress(String password, boolean isComplete) {
                Logger.d("password = " + password + " isComplete = " + isComplete);
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_register_invitation;
    }

    @OnClick(R.id.register_invitation_skip_btn)
    public void onClick() {
        mRegister.setInviteCode("7ZHV6T");
        mPresenter.register(mRegister);
    }

    @Override
    public void checkInviteCodeSuccess(String result) {
        String invitateCode = mPasswordView.getPassword();
        mRegister.setInviteCode(invitateCode);
        mPresenter.register(mRegister);
    }

    @Override
    public void registerSuccess(UserBean userBean) {
        popTo(LoginFragment.class, false);
    }

    @Override
    protected RegisterPresenter createPresenter() {
        return new RegisterPresenter(getActivity());
    }
}