package com.pakpobox.cleanpro.base;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pakpobox.cleanpro.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * User:Sean.Wei
 * Date:2018/3/23
 * Time:18:10
 */

public abstract class BaseListFragment<T> extends BaseFragment {

    @BindView(R.id.toolbar_title_tv)
    TextView mTitleTv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.base_list_recyclerview)
    protected RecyclerView mRecyclerview;
    @BindView(R.id.base_list_swiperefreshlayout)
    protected SwipeRefreshLayout mSwiperefreshlayout;

    private LayoutManager mManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_base_list, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        preLoadListData();
        if (isAutoLoad() && mRecyclerview.getAdapter().getItemCount() == 0) {
            mSwiperefreshlayout.setRefreshing(true);
            onRefreshing();
        }
    }

    private void initView() {
        if (isShowToolbar()) {
            toolbar.setVisibility(View.VISIBLE);
            mTitleTv.setText(getTitle());
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pop();
                }
            });
        } else {
            toolbar.setVisibility(View.GONE);
        }


        mRecyclerview.setAdapter(getRecyclerAdapter());
        mManager = getLayoutManager();
        mRecyclerview.setLayoutManager(mManager);
        mSwiperefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                BaseAdapter adapter = (BaseAdapter) mRecyclerview.getAdapter();
                adapter.clear();
                mSwiperefreshlayout.setRefreshing(true);//同时防止多次 requestMore
                onRefreshing();
            }
        });
        mSwiperefreshlayout.setColorSchemeColors(getContext().getResources().getColor(R.color.colorAccent), getContext().getResources().getColor(R.color.colorAccent), Color.BLUE);
        mRecyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (mManager instanceof LinearLayoutManager) {
                    if (!mSwiperefreshlayout.isRefreshing() && ((LinearLayoutManager) mManager).findLastVisibleItemPosition() == recyclerView.getAdapter().getItemCount() - 1) {
                        //加载更多
                    }
                } else {

                }
            }
        });
    }

    //预加载数据
    private void preLoadListData() {
        List<T> datas = getPreData();
        if (datas != null && datas.size() > 0) {
            BaseAdapter adapter = (BaseAdapter) mRecyclerview.getAdapter();
            adapter.clear();
            adapter.addItems(datas);
        }
    }

    //加载数据
    protected void loadListData(List<T> datas) {
        if (mSwiperefreshlayout.isRefreshing())
            mSwiperefreshlayout.setRefreshing(false);
        if (datas != null && datas.size() != 0) {
            BaseAdapter adapter = (BaseAdapter) mRecyclerview.getAdapter();
            adapter.clear();
            adapter.addItems(datas);
        }
        BaseAdapter adapter = (BaseAdapter) mRecyclerview.getAdapter();
        if (adapter.getItemCount() <= 0) {
            showToast(getEmptyMsg());
        }

    }

    protected void stopRefresh() {
        if (mSwiperefreshlayout.isRefreshing())
            mSwiperefreshlayout.setRefreshing(false);
    }

    protected abstract boolean isShowToolbar();

    protected abstract String getTitle();

    protected abstract BaseAdapter<T> getRecyclerAdapter();

    protected abstract void onRefreshing();

    protected List<T> getPreData() {
        return null;
    }

    protected LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getContext());
    }

    protected boolean isAutoLoad() {
        return true;
    }

    protected String getEmptyMsg() {
        return "";
//        return getString(R.string.app_no_order_toast);
    }

}
