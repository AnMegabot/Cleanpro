package com.pakpobox.cleanpro.ui.order;


import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.base.BaseListFragment;
import com.pakpobox.cleanpro.base.list.BaseListAdapter;
import com.pakpobox.cleanpro.bean.Order;
import com.pakpobox.cleanpro.ui.main.MainFragment;
import com.pakpobox.cleanpro.ui.order.detail.OrderDetailFragment;
import com.pakpobox.cleanpro.ui.order.unpaid.UnpaidScanFragment;
import com.pakpobox.cleanpro.ui.setting.SettingFragment;
import com.pakpobox.cleanpro.utils.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 订单列表
 */
public class OrdersFragment extends BaseListFragment<OrdersPresenter, OrdersContract.IOrdersView, Order> implements OrdersContract.IOrdersView {
    @BindView(R.id.orders_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.orders_containerLayout)
    FrameLayout mContainerLayout;


    public static OrdersFragment newInstance() {
        Bundle args = new Bundle();
        OrdersFragment fragment = new OrdersFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initViews(View view) {
        StatusBarUtil.setHeight(getContext(), mToolbar);
        initListView(mContainerLayout);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_orders;
    }


    @Override
    protected OrdersPresenter createPresenter() {
        return new OrdersPresenter();
    }

    @Override
    protected void loadDatas() {
        mPresenter.getOrdersList();
    }

    @Override
    protected boolean isCanLoadMore() {
        return true;
    }

    @Override
    protected BaseListAdapter getListAdapter() {
        return new OrderListAdapter(new OnOrderItemClickListener() {
            @Override
            public void onItemClick(Order order) {
                if (getParentFragment() instanceof MainFragment) {
                    if (order.getPaymentStatus().equals("PAID"))
                        ((MainFragment) getParentFragment()).start(OrderDetailFragment.newInstance());
                    else
                        ((MainFragment) getParentFragment()).start(UnpaidScanFragment.newInstance());
                }
            }
        });
    }

    @Override
    protected View initHeaderView() {
        return null;
    }

}
