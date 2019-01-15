package com.pakpobox.cleanpro.ui.order;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.base.BaseListFragment;
import com.pakpobox.cleanpro.base.list.BaseListAdapter;
import com.pakpobox.cleanpro.bean.Order;
import com.pakpobox.cleanpro.bean.PageListDataBean;
import com.pakpobox.cleanpro.ui.main.MainFragment;
import com.pakpobox.cleanpro.ui.order.detail.OrderDetailFragment;
import com.pakpobox.cleanpro.utils.StatusBarUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

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
    protected boolean isRegisterEvent() {
        return true;
    }

    @Override
    protected void initViews(View view) {
        StatusBarUtil.setHeight(getContext(), mToolbar);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initListView(mContainerLayout);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_orders;
    }

    @Override
    protected OrdersPresenter createPresenter() {
        return new OrdersPresenter(getActivity());
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
        return new OrderListAdapter(getContext(), new OrderListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Order order) {
                if (getParentFragment() instanceof MainFragment) {
                    ((MainFragment) getParentFragment()).start(OrderDetailFragment.newInstance(order));
                }
            }
        });
    }

//    @Override
//    public void showError() {
//        if (AppSetting.isLogin()) {
//            super.showError();
//        } else {
//
//        }
//    }

    @Override
    protected View initHeaderView() {
        return null;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginEvent(Order event) {
        refreshData();
    }
}
