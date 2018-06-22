package com.pakpobox.cleanpro.ui.home;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.base.BaseFragment;
import com.pakpobox.cleanpro.model.AppSetting;
import com.pakpobox.cleanpro.ui.login.LoginActivity;
import com.pakpobox.cleanpro.utils.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 首页
 */
public class HomeFragment extends BaseFragment {

    @BindView(R.id.home_toolbar)
    Toolbar mToolbar;
    Unbinder unbinder;

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initViews(View view) {
        StatusBarUtil.setHeight(getContext(), mToolbar);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @OnClick({R.id.home_laundry_btn, R.id.home_dryer_btn, R.id.home_service_btn, R.id.home_location_btn, R.id.home_price_btn, R.id.home_promotion_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home_laundry_btn:
                if (!AppSetting.getInstance().isHasLogin()) {
                    getActivity().startActivity(new Intent(getContext(), LoginActivity.class));
                }
                break;
            case R.id.home_dryer_btn:
                break;
            case R.id.home_service_btn:
                break;
            case R.id.home_location_btn:
                break;
            case R.id.home_price_btn:
                break;
            case R.id.home_promotion_btn:
                break;
        }
    }
}
