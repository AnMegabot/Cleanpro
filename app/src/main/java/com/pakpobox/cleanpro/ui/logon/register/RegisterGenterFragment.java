package com.pakpobox.cleanpro.ui.logon.register;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.base.BaseFragment;
import com.pakpobox.cleanpro.utils.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 注册-4
 */
public class RegisterGenterFragment extends BaseFragment {


    @BindView(R.id.register_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.register_gender_rg)
    RadioGroup mGenderRg;


    public static RegisterGenterFragment newInstance() {
        Bundle args = new Bundle();
        RegisterGenterFragment fragment = new RegisterGenterFragment();
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

        mGenderRg.check(R.id.register_gender_male_rb);
        mGenderRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.register_gender_male_rb:
                        break;
                    case R.id.register_gender_female_rb:
                        break;
                }
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_register_genter;
    }

    @OnClick(R.id.register_gender_next_btn)
    public void onClick() {
        start(RegisterPostcodeFragment.newInstance());
    }

}
