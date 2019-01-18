package com.pakpobox.cleanpro.ui.order.unpaid;


import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.base.BaseFragment;
import com.pakpobox.cleanpro.utils.StatusBarUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 未支付扫码
 */
public class UnpaidScanFragment extends BaseFragment {

    @BindView(R.id.app_toolbar_title_tv)
    TextView mTitleTv;
    @BindView(R.id.app_toolbar)
    Toolbar mToolbar;

    public static UnpaidScanFragment newInstance() {
        Bundle args = new Bundle();
        UnpaidScanFragment fragment = new UnpaidScanFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initViews(View view) {
        StatusBarUtil.setHeight(getContext(), mToolbar);
        mTitleTv.setText(getString(R.string.orders));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pop();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_unpaid_scan;
    }

    @OnClick(R.id.unpaid_scan_btn)
    public void onClick() {

    }

}
