package com.pakpobox.cleanpro.ui.order;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.application.AppSetting;
import com.pakpobox.cleanpro.base.list.BaseListFragment;
import com.pakpobox.cleanpro.base.list.BaseListAdapter;
import com.pakpobox.cleanpro.bean.Order;
import com.pakpobox.cleanpro.bean.PageListDataBean;
import com.pakpobox.cleanpro.bean.UserBean;
import com.pakpobox.cleanpro.ui.main.MainFragment;
import com.pakpobox.cleanpro.ui.order.detail.OrderDetailFragment;
import com.pakpobox.cleanpro.ui.order.unpaid.UnpaidScanFragment;
import com.pakpobox.cleanpro.utils.StatusBarUtil;
import com.pakpobox.logger.Logger;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

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
        initListView(mContainerLayout);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_orders;
    }

    @Override
    public void getSuccess(PageListDataBean<Order> datas) {
        if (getPage() == 0) {
            clearListData();
        }
        setData(datas.getResultList());

        if (getData().size() > 0) {
            if (datas.isOver()) {
                showNoMore();
            } else {
                autoLoadMore();
            }
        }

        if (getData().size() == 0)
            showEmpty();
        else
            showContent();
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
        return new OrderListAdapter(new OrderListAdapter.OnItemClickListener() {
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
