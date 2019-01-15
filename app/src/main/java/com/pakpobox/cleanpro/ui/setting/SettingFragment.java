package com.pakpobox.cleanpro.ui.setting;


import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.application.AppSetting;
import com.pakpobox.cleanpro.base.BaseFragment;
import com.pakpobox.cleanpro.bean.UserBean;
import com.pakpobox.cleanpro.ui.setting.forgetpsw.ForgetPaymentPswFragment;
import com.pakpobox.cleanpro.ui.setting.setpsw.SetPaymentPswFragment;
import com.pakpobox.cleanpro.utils.StatusBarUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 设置
 */
public class SettingFragment extends BaseFragment {

    @BindView(R.id.toolbar_title_tv)
    TextView mTitleTv;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.setting_logout_btn)
    Button mLogoutBtn;

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

    @OnClick({R.id.setting_modify_pay_psw_btn, R.id.setting_forget_pay_psw_btn, R.id.setting_logout_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.setting_modify_pay_psw_btn:
                start(SetPaymentPswFragment.newInstance(1, null, null, null));
                break;
            case R.id.setting_forget_pay_psw_btn:
                start(ForgetPaymentPswFragment.newInstance());
                break;
            case R.id.setting_logout_btn:
                AppSetting.saveIsLogin(false);
                AppSetting.saveUserInfo(null);
                EventBus.getDefault().post(new UserBean());
                pop();
                break;
        }
    }
}