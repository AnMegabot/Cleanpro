package com.pakpobox.cleanpro.ui.logon.register;


import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.base.BaseFragment;
import com.pakpobox.cleanpro.utils.StatusBarUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 注册-1
 */
public class RegisterStartFragment extends BaseFragment {

    @BindView(R.id.register_toolbar)
    Toolbar mToolbar;

    public static RegisterStartFragment newInstance() {
        Bundle args = new Bundle();
        RegisterStartFragment fragment = new RegisterStartFragment();
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
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_register_start;
    }

    @OnClick(R.id.register_start_next_btn)
    public void onClick() {
        start(RegisterNameFragment.newInstance());
    }
}
