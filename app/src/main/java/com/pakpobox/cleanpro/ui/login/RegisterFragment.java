package com.pakpobox.cleanpro.ui.login;


import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.base.BaseFragment;
import com.pakpobox.cleanpro.utils.KeyBoardHelper;
import com.pakpobox.cleanpro.utils.StatusBarUtil;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 注册
 */
public class RegisterFragment extends BaseFragment {

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

    public static RegisterFragment newInstance() {
        Bundle args = new Bundle();
        RegisterFragment fragment = new RegisterFragment();
        fragment.setArguments(args);
        return fragment;
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
    }

    @OnClick({R.id.register_country_code_btn, R.id.register_verifycation_btn, R.id.register_next_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_country_code_btn:
                break;
            case R.id.register_verifycation_btn:
                break;
            case R.id.register_next_btn:
                start(SetPSWFragment.newInstance());
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        keyBoardHelper.removeKeyboardListener();
    }
}
