package com.pakpobox.cleanpro.ui.my;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.base.BaseFragment;
import com.pakpobox.cleanpro.application.AppSetting;
import com.pakpobox.cleanpro.bean.UserBean;
import com.pakpobox.cleanpro.ui.logon.LoginActivity;
import com.pakpobox.cleanpro.ui.main.MainFragment;
import com.pakpobox.cleanpro.ui.setting.SettingFragment;
import com.pakpobox.cleanpro.ui.wallet.WalletFragment;
import com.pakpobox.cleanpro.utils.StatusBarUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我的
 */
public class MyFragment extends BaseFragment {

    @BindView(R.id.my_appbar)
    Toolbar mToolbar;
    @BindView(R.id.my_account_tv)
    TextView mAccountTv;

    public static MyFragment newInstance() {
        Bundle args = new Bundle();
        MyFragment fragment = new MyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected boolean isRegisterEvent() {
        return true;
    }

    @Override
    protected void initViews(View view) {
        StatusBarUtil.setHeight(getContext(), mToolbar);
        setAccountText(AppSetting.getUserInfo());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my;
    }

    @OnClick({R.id.my_portrait_im, R.id.my_wallet, R.id.my_settings})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.my_portrait_im:
                if (!AppSetting.isLogin()) {
                    getActivity().startActivity(new Intent(getContext(), LoginActivity.class));
                }
                break;
            case R.id.my_wallet:
                if (!AppSetting.isLogin()) {
                    getActivity().startActivity(new Intent(getContext(), LoginActivity.class));
                    setClickingView(view);
                } else {
                    if (getParentFragment() instanceof MainFragment) {
                        ((MainFragment) getParentFragment()).start(WalletFragment.newInstance());
                    }
                }
                break;
            case R.id.my_settings:
                if (getParentFragment() instanceof MainFragment) {
                    ((MainFragment) getParentFragment()).start(SettingFragment.newInstance());
                }
                break;
        }
    }

    public void setAccountText(UserBean data) {
        if (AppSetting.isLogin() && null!=data) {
            mAccountTv.setText(data.getLoginName());
            clickClickingView();
        } else {
            mAccountTv.setText(getString(R.string.my_no_login_tips));
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginEvent(UserBean event) {
        setAccountText(event);
    }
}
