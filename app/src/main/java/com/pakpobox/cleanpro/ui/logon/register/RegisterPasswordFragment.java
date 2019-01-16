package com.pakpobox.cleanpro.ui.logon.register;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ScrollView;

import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.base.BaseFragment;
import com.pakpobox.cleanpro.utils.KeyBoardHelper;
import com.pakpobox.cleanpro.utils.StatusBarUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 注册-7
 */
public class RegisterPasswordFragment extends BaseFragment {


    @BindView(R.id.register_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.register_password_et)
    EditText mPasswordEt;
    @BindView(R.id.register_password_confirm_et)
    EditText mPasswordConfEt;
    @BindView(R.id.register_content_llt)
    ScrollView mContentLlt;

    private KeyBoardHelper keyBoardHelper;

    public static RegisterPasswordFragment newInstance() {
        Bundle args = new Bundle();
        RegisterPasswordFragment fragment = new RegisterPasswordFragment();
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
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_register_password;
    }

    @OnClick(R.id.register_password_next_btn)
    public void onClick() {
        start(RegisterBirthdayFragment.newInstance());
    }
}