package com.pakpobox.cleanpro.ui.order.detail;


import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.base.BaseFragment;
import com.pakpobox.cleanpro.utils.StatusBarUtil;

import butterknife.BindView;

/**
 * 订单详情
 */
public class OrderDetailFragment extends BaseFragment {

    @BindView(R.id.toolbar_title_tv)
    TextView mTitleTv;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    public static OrderDetailFragment newInstance() {
        Bundle args = new Bundle();
        OrderDetailFragment fragment = new OrderDetailFragment();
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
        return R.layout.fragment_order_detail;
    }
}