package com.pakpobox.cleanpro.ui.price;


import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.base.BaseListFragment;
import com.pakpobox.cleanpro.base.list.BaseListAdapter;
import com.pakpobox.cleanpro.bean.price.Price;
import com.pakpobox.cleanpro.utils.StatusBarUtil;

import butterknife.BindView;

/**
 * 我的钱包
 */
public class PriceFragment extends BaseListFragment<PricePresenter, PriceContract.IPriceView, Price> implements PriceContract.IPriceView {
    @BindView(R.id.app_toolbar_title_tv)
    TextView mTitleTv;
    @BindView(R.id.app_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.price_containerLayout)
    FrameLayout mContainerLayout;


    public static PriceFragment newInstance() {
        Bundle args = new Bundle();
        PriceFragment fragment = new PriceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initViews(View view) {
        StatusBarUtil.setHeight(getContext(), mToolbar);
        mTitleTv.setText(getString(R.string.home_price));
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
        return R.layout.fragment_price;
    }

    @Override
    protected PricePresenter createPresenter() {
        return new PricePresenter(getActivity());
    }

    @Override
    protected void loadDatas() {
        mPresenter.getPrices();
    }

    @Override
    protected boolean isCanLoadMore() {
        return false;
    }

    @Override
    public boolean onBackPressedSupport() {
        return super.onBackPressedSupport();
    }

    @Override
    protected BaseListAdapter getListAdapter() {
        return new PriceAdapter(getContext(), new PriceAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Price order) {

            }
        });
    }

    @Override
    protected View initHeaderView() {
        return null;
    }

}