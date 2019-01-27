package com.pakpobox.cleanpro.ui.order;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.application.AppSetting;
import com.pakpobox.cleanpro.base.BaseListFragment;
import com.pakpobox.cleanpro.base.list.BaseListAdapter;
import com.pakpobox.cleanpro.bean.Order;
import com.pakpobox.cleanpro.bean.UserBean;
import com.pakpobox.cleanpro.ui.account.LoginActivity;
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
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_orders_empty_view, mStatusLayout, false);
        mStatusLayout.setEmpty(view);
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
        if (!AppSetting.isLogin()) {
            View loginView = LayoutInflater.from(getContext()).inflate(R.layout.layout_not_login_view, mStatusLayout, false);
            loginView.findViewById(R.id.not_login_layout_login_btn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getActivity().startActivity(new Intent(getContext(), LoginActivity.class));
                }
            });
            mStatusLayout.setError(loginView);
            mStatusLayout.showError();
        } else {
            View errorView = LayoutInflater.from(getContext()).inflate(R.layout.status_error_layout, mStatusLayout, false);
            mStatusLayout.setError(errorView);
            mPresenter.getOrdersList();
        }
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginEvent(UserBean event) {
        refreshData();
    }
}
