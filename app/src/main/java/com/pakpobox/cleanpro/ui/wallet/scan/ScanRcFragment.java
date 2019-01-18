package com.pakpobox.cleanpro.ui.wallet.scan;


import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.base.BaseFragment;
import com.pakpobox.cleanpro.utils.StatusBarUtil;

import butterknife.BindView;

/**
 * 扫码充值
 */
public class ScanRcFragment extends BaseFragment {

    @BindView(R.id.app_toolbar_title_tv)
    TextView mTitleTv;
    @BindView(R.id.app_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.scan_rc_qr_code_im)
    ImageView mQrcodeIm;

    public static ScanRcFragment newInstance() {
        Bundle args = new Bundle();
        ScanRcFragment fragment = new ScanRcFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initViews(View view) {
        StatusBarUtil.setHeight(getContext(), mToolbar);
        mTitleTv.setText(getString(R.string.wallet_recharge));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pop();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_scan_rc;
    }

}