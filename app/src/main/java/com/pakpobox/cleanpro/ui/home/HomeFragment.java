package com.pakpobox.cleanpro.ui.home;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.base.BaseFragment;
import com.pakpobox.cleanpro.model.AppSetting;
import com.pakpobox.cleanpro.ui.booking.SelectPreferenceFragment;
import com.pakpobox.cleanpro.ui.login.LoginActivity;
import com.pakpobox.cleanpro.ui.main.MainFragment;
import com.pakpobox.cleanpro.ui.scanner.QRCodeScanActivity;
import com.pakpobox.cleanpro.utils.StatusBarUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 首页
 */
public class HomeFragment extends BaseFragment {
    public static final int LAUNDRY_SCAN_REQUEST_CODE = 0x0011;
    public static final int DRYER_SCAN_REQUEST_CODE = 0x0012;
    public static final int LOGIN_REQUEST_CODE = 0x0013;

    @BindView(R.id.home_toolbar)
    Toolbar mToolbar;

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
                    getActivity().startActivityForResult(new Intent(getContext(), LoginActivity.class), LOGIN_REQUEST_CODE);
                    setClickingView(view);
                } else {
                    startActivityForResult(new Intent(getContext(), QRCodeScanActivity.class), LAUNDRY_SCAN_REQUEST_CODE);
                }
                break;
            case R.id.home_dryer_btn:
                startActivityForResult(new Intent(getContext(), QRCodeScanActivity.class), DRYER_SCAN_REQUEST_CODE);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK)
            return;

        switch (requestCode) {
            case LAUNDRY_SCAN_REQUEST_CODE:
                if (getParentFragment() instanceof MainFragment) {
                    ((MainFragment) getParentFragment()).start(SelectPreferenceFragment.newInstance(LAUNDRY_SCAN_REQUEST_CODE));
                }
                break;
            case DRYER_SCAN_REQUEST_CODE:
                if (getParentFragment() instanceof MainFragment) {
                    ((MainFragment) getParentFragment()).start(SelectPreferenceFragment.newInstance(DRYER_SCAN_REQUEST_CODE));
                }
                break;
            case LOGIN_REQUEST_CODE:
                clickClickingView();
                break;
        }
    }
}
