package com.pakpobox.cleanpro.base;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.base.list.BaseListAdapter;
import com.pakpobox.cleanpro.ui.mvp.presenter.BasePresenter;
import com.pakpobox.cleanpro.ui.mvp.view.IListDataView;
import com.pakpobox.cleanpro.ui.mvp.view.IView;
import com.pakpobox.cleanpro.ui.widget.LMRecyclerView;
import com.pakpobox.cleanpro.ui.widget.StatusLayout;
import com.pakpobox.cleanpro.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * User:Sean.Wei
 * Date:2018/3/23
 * Time:18:10
 */

public abstract class BaseListFragment<P extends BasePresenter<V>, V extends IView, T> extends BasePresenterFragment<P, V> implements LMRecyclerView.OnFooterAutoLoadMoreListener, IListDataView<T> {

    protected StatusLayout mStatusLayout;
    protected SwipeRefreshLayout mRefreshLayout;
    protected LMRecyclerView mRecyclerView;
    protected BaseListAdapter mListAdapter;
    protected int page;
    protected int state = -1;
    protected boolean isAutoLoadMore = true;//是否开启自动加载
    protected List<T> mListData = new ArrayList<>();

    //当前页面状态
    public static class PAGE_STATE {
        public static final int STATE_REFRESH = 0; //刷新
        public static final int STATE_LOAD_MORE = 1;//加载更多
    }

    /**
     * 初始化列表视图
     * @param containerLayout 列表视图容器
     */
    protected void initListView(ViewGroup containerLayout) {
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_base_list, containerLayout, false);
        containerLayout.addView(contentView);

        mRefreshLayout = (SwipeRefreshLayout) contentView.findViewById(R.id.base_list_refreshLayout);
        mStatusLayout = (StatusLayout) contentView.findViewById(R.id.base_list_containerLayout);
        mRecyclerView = (LMRecyclerView) contentView.findViewById(R.id.base_list_recyclerView);

        mRefreshLayout.setOnRefreshListener(mOnRefreshListener);
        mRefreshLayout.setColorSchemeColors(getContext().getResources().getColor(R.color.colorAccent));
        mRecyclerView.setLayoutManager(getLayoutManager());
        mRecyclerView.setCanLoadMore(isCanLoadMore());
        mRecyclerView.addFooterAutoLoadMoreListener(this);
        mListAdapter = getListAdapter();
        if (mListAdapter != null) {
            mRecyclerView.addHeaderView(initHeaderView());
            mRecyclerView.setAdapter(mListAdapter);
            if (isStatusLayoutEnable())
                mStatusLayout.showLoding();
            loadDatas();
        }

        mStatusLayout.setOnErrorClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isStatusLayoutEnable())
                    mStatusLayout.showLoding();
                isAutoLoadMore = true;
                loadDatas();
            }
        });
    }

    /**
     * 刷新列表
     */
    public void refreshData() {
        state = PAGE_STATE.STATE_REFRESH;
        isAutoLoadMore = true;
        page = 0;
        loadDatas();
    }

    //刷新单条数据
    public void notifyItemDataChanged(int position, T data) {
        if (null != mListData && position <= mListData.size())
            mListData.set(position, data);
        mListAdapter.notifyItemDataChanged(position, mRecyclerView);
    }

    //移除单条数据
    public void notifyItemDataRemove(int position) {
        if (null != mListData && position < mListData.size())
            mListData.remove(position);
        mListAdapter.notifyItemDataRemove(position, mRecyclerView);
    }

    //下拉刷新监听
    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            refreshData();
        }
    };

    public StatusLayout getStatusLayout() {
        return mStatusLayout;
    }

    @Override
    public int getPage() {
        return page;
    }

    @Override
    public void setData(List<T> data) {
        mListData.addAll(data);
    }

    @Override
    public List<T> getData() {
        return mListData;
    }

    @Override
    public void showContent() {
        mStatusLayout.showContent();
        mListAdapter.notifyAllDatas(mListData, mRecyclerView);
    }

    @Override
    public void autoLoadMore() {
        mRecyclerView.showLoadMore();
        page++;
        isAutoLoadMore = true;
    }

    @Override
    public void clearListData() {
        mListData.clear();
    }

    @Override
    public void showNoMore() {
        mRecyclerView.showNoMoreData();
        isAutoLoadMore = false;
    }

    @Override
    public void showLoading(String msg) {
        if (state == PAGE_STATE.STATE_REFRESH)
            setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        setRefreshing(false);
    }

    @Override
    public void showFail(String msg) {
        ToastUtils.showToast(getActivity(), msg);
    }

    @Override
    public void showError() {
        isAutoLoadMore = false;
        //如果是加载更多出错，那么底部就显示点击重新加载;
        // 否则，就清空数据，显示没有数据
        if (state == PAGE_STATE.STATE_LOAD_MORE) {
            mRecyclerView.showLoadMoreError();
            mListAdapter.notifyAllDatas(mListData, mRecyclerView);
        } else {
            if (isStatusLayoutEnable())
                mStatusLayout.showError();
        }
    }

    @Override
    public void showEmpty() {
        if (isStatusLayoutEnable())
            mStatusLayout.showEmpty();
    }

    @Override
    public void loadMore() {
        if (!isAutoLoadMore) return;
        state = PAGE_STATE.STATE_LOAD_MORE;
        loadDatas();
    }

    @Override
    public void reLoadMore() {
        isAutoLoadMore = true;
        loadMore();
    }

    public boolean isStatusLayoutEnable() {
        return true;
    }

    protected void setRefreshing(final boolean isRefrshing) {
        mRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(isRefrshing);
            }
        }, 100);

    }

    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getActivity());
    }

    protected abstract void loadDatas();//加载数据
    protected abstract boolean isCanLoadMore();// 是否能够自动加载更多
    protected abstract BaseListAdapter getListAdapter();//获取列表适配器
    protected abstract View initHeaderView();//初始化头布局

}
