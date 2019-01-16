package com.pakpobox.cleanpro.ui.logon.register;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.ScrollView;

import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.base.BaseFragment;
import com.pakpobox.cleanpro.utils.KeyBoardHelper;
import com.pakpobox.cleanpro.utils.StatusBarUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 注册-5
 */
public class RegisterPostcodeFragment extends BaseFragment {


    @BindView(R.id.register_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.register_postcode_et)
    EditText mPostcodeEt;
    @BindView(R.id.register_content_llt)
    ScrollView mContentLlt;

    private KeyBoardHelper keyBoardHelper;

    public static RegisterPostcodeFragment newInstance() {
        Bundle args = new Bundle();
        RegisterPostcodeFragment fragment = new RegisterPostcodeFragment();
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
        return R.layout.fragment_register_postcode;
    }

    @OnClick(R.id.register_postcode_next_btn)
    public void onClick() {
        start(RegisterMobileFragment.newInstance());
    }

}
