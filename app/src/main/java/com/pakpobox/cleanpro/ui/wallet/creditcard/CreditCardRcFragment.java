package com.pakpobox.cleanpro.ui.wallet.creditcard;


import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.base.BaseFragment;
import com.pakpobox.cleanpro.ui.widget.tabbar.TabBarItem;
import com.pakpobox.cleanpro.ui.widget.tabbar.TabBarLayout;
import com.pakpobox.cleanpro.utils.StatusBarUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 信用卡充值
 */
public class CreditCardRcFragment extends BaseFragment {

    @BindView(R.id.toolbar_title_tv)
    TextView mTitleTv;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.credit_card_rc_tabbar_layout)
    TabBarLayout mTabbarLayout;

    public static CreditCardRcFragment newInstance() {
        Bundle args = new Bundle();
        CreditCardRcFragment fragment = new CreditCardRcFragment();
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

        mTabbarLayout.setOnItemSelectedListener(new TabBarLayout.OnItemSelectedListener() {
            @Override
            public void onItemSelected(TabBarItem bottomBarItem, int previousPosition, int currentPosition) {

            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_credit_card_rc;
    }

    @OnClick(R.id.credit_card_rc_pay_btn)
    public void onClick() {
        
    }
}