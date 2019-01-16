package com.pakpobox.cleanpro.ui.logon.register;


import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.base.BaseFragment;
import com.pakpobox.cleanpro.utils.StatusBarUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 注册-3
 */
public class RegisterBirthdayFragment extends BaseFragment {


    @BindView(R.id.register_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.register_birthday_timepicker)
    LinearLayout mTimepicker;

    public static RegisterBirthdayFragment newInstance() {
        Bundle args = new Bundle();
        RegisterBirthdayFragment fragment = new RegisterBirthdayFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initViews(View view) {
        StatusBarUtil.setHeight(getContext(), mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_register_birthday;
    }

    @OnClick(R.id.register_birthday_next_btn)
    public void onClick() {
        start(RegisterNameFragment.newInstance());
    }
}
