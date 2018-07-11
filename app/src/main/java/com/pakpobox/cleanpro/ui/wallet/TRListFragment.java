package com.pakpobox.cleanpro.ui.wallet;


import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.base.BaseListFragment;
import com.pakpobox.cleanpro.base.list.BaseListAdapter;
import com.pakpobox.cleanpro.bean.Order;
import com.pakpobox.cleanpro.bean.TradingRecort;
import com.pakpobox.cleanpro.ui.order.OnOrderItemClickListener;
import com.pakpobox.cleanpro.ui.order.OrderListAdapter;
import com.pakpobox.cleanpro.utils.StatusBarUtil;

import butterknife.BindView;

/**
 * 充值记录
 */
public class TRListFragment extends BaseListFragment<TRListPresenter, TRListContract.ITRListView, TradingRecort> implements TRListContract.ITRListView {
    @BindView(R.id.toolbar_title_tv)
    TextView mTitleTv;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.rc_details_containerLayout)
    FrameLayout mContainerLayout;


    public static TRListFragment newInstance() {
        Bundle args = new Bundle();
        TRListFragment fragment = new TRListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initViews(View view) {
        StatusBarUtil.setHeight(getContext(), mToolbar);
        mTitleTv.setText(getString(R.string.wallet_details));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pop();
            }
        });

        initListView(mContainerLayout);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tr_list;
    }


    @Override
    protected TRListPresenter createPresenter() {
        return new TRListPresenter();
    }

    @Override
    protected void loadDatas() {
        mPresenter.getRechargeDetailList();
    }

    @Override
    protected boolean isCanLoadMore() {
        return true;
    }

    @Override
    protected BaseListAdapter getListAdapter() {
        return new TRListAdapter(new TRListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(TradingRecort order) {

            }
        });
    }

    @Override
    protected View initHeaderView() {
        return null;
    }

}
