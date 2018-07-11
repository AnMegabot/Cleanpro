package com.pakpobox.cleanpro.ui.my;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.base.BaseFragment;
import com.pakpobox.cleanpro.model.AppSetting;
import com.pakpobox.cleanpro.ui.booking.SelectPreferenceFragment;
import com.pakpobox.cleanpro.ui.login.LoginActivity;
import com.pakpobox.cleanpro.ui.main.MainFragment;
import com.pakpobox.cleanpro.ui.scanner.QRCodeScanActivity;
import com.pakpobox.cleanpro.ui.setting.SettingFragment;
import com.pakpobox.cleanpro.ui.wallet.WalletFragment;
import com.pakpobox.cleanpro.utils.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

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
    protected void initViews(View view) {
        StatusBarUtil.setHeight(getContext(), mToolbar);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my;
    }

    @OnClick({R.id.my_portrait_im, R.id.my_wallet, R.id.my_settings})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.my_portrait_im:
                if (!AppSetting.getInstance().isHasLogin()) {
                    getActivity().startActivity(new Intent(getContext(), LoginActivity.class));
                }
                break;
            case R.id.my_wallet:
//                if (!AppSetting.getInstance().isHasLogin()) {
//                    getActivity().startActivity(new Intent(getContext(), LoginActivity.class));
//                    setClickingView(view);
//                } else {
                    if (getParentFragment() instanceof MainFragment) {
                        ((MainFragment) getParentFragment()).start(WalletFragment.newInstance());
                    }
//                }
                break;
            case R.id.my_settings:
                if (getParentFragment() instanceof MainFragment) {
                    ((MainFragment) getParentFragment()).start(SettingFragment.newInstance());
                }
                break;
        }
    }
}
