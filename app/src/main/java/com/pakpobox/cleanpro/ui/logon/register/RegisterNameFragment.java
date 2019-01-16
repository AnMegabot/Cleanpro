package com.pakpobox.cleanpro.ui.logon.register;


import android.os.Bundle;
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
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 注册-2
 */
public class RegisterNameFragment extends BaseFragment {


    @BindView(R.id.register_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.register_first_name_et)
    EditText mFirstNameEt;
    @BindView(R.id.register_last_name_et)
    EditText mLastNameEt;
    @BindView(R.id.register_content_llt)
    ScrollView mContentLlt;

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
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_register_name;
    }

    @OnClick(R.id.register_name_next_btn)
    public void onClick() {
        start(RegisterBirthdayFragment.newInstance());
    }
}