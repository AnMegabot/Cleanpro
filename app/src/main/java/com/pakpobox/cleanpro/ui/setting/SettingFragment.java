package com.pakpobox.cleanpro.ui.setting;


import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.application.AppSetting;
import com.pakpobox.cleanpro.base.BaseFragment;
import com.pakpobox.cleanpro.bean.UserBean;
import com.pakpobox.cleanpro.ui.account.paypsw.ForgetPaymentPswFragment;
import com.pakpobox.cleanpro.utils.StatusBarUtil;
import com.pakpobox.cleanpro.utils.SystemUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 设置
 */
public class SettingFragment extends BaseFragment {

    @BindView(R.id.app_toolbar_title_tv)
    TextView mTitleTv;
    @BindView(R.id.app_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.setting_logout_btn)
    Button mLogoutBtn;
    @BindView(R.id.setting_version_tv)
    TextView mVersionTv;

    public static SettingFragment newInstance() {
        Bundle args = new Bundle();
        SettingFragment fragment = new SettingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initViews(View view) {
        StatusBarUtil.setHeight(getContext(), mToolbar);
        mTitleTv.setText(getString(R.string.setting));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pop();
            }
        });
        mVersionTv.setText(SystemUtils.getVersionName(getContext()));
        if (AppSetting.isLogin()) {
            mLogoutBtn.setVisibility(View.VISIBLE);
        } else {
            mLogoutBtn.setVisibility(View.GONE);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_setting;
    }

    @OnClick(R.id.setting_logout_btn)
    public void onClick() {
        AppSetting.saveLoginToken(null);
        AppSetting.saveUserInfo(null);
        EventBus.getDefault().post(new UserBean());
        pop();
    }
}