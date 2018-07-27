package com.pakpobox.cleanpro.net.callback;

import android.app.Activity;

import com.pakpobox.cleanpro.ui.mvp.presenter.BasePresenter;
import com.pakpobox.cleanpro.ui.mvp.view.IListDataView;

import java.util.List;

/**
 * User:Sean.Wei
 * Date:2018/7/27
 * Time:10:31
 */

public class PageListNetCallback<T, V> extends NetCallback<T>{

    private IListDataView<V> mListDataView;
    public PageListNetCallback(Activity activity, BasePresenter mPresenter) {
        super(activity, mPresenter);
        this.mListDataView = (IListDataView<V>) view;
    }

    @Override
    protected void onSuccess(T data) {

        if (mListDataView.getPage() == 0) {
            mListDataView.clearListData();
        }
        if (mListDataView.getPage() > 0 && (null==data || ((List<V>)data).size()<=0)) {
            mListDataView.showNoMore();
        } else {
            mListDataView.autoLoadMore();
        }
        mListDataView.setData((List<V>)data);

        if (mListDataView.getData().size() == 0)
            mListDataView.showEmpty();
        else
            mListDataView.showContent();
    }

}
