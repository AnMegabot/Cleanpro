package com.pakpobox.cleanpro.net.callback;

import android.app.Activity;

import com.pakpobox.cleanpro.bean.PageListDataBean;
import com.pakpobox.cleanpro.ui.mvp.presenter.IPresenter;
import com.pakpobox.cleanpro.ui.mvp.view.IListDataView;

import java.lang.reflect.Type;
import java.util.List;

/**
 * User:Sean.Wei
 * Date:2018/7/27
 * Time:10:31
 */

public abstract class PageListNetCallback<T> extends BaseNetCallback<PageListDataBean<T>>{

    protected IListDataView<T> mListDataView;

    public PageListNetCallback(Activity activity, IPresenter mPresenter) {
        super(activity, mPresenter);
        if (null != mPresenter) {
            if (view instanceof IListDataView)
                this.mListDataView = (IListDataView)view;
        }
    }

    @Override
    protected void onSuccess(PageListDataBean<T> data) {
        if (null == mListDataView)
            return;

        if (data.getPage() == 0) {
            mListDataView.clearListData();
        }

        List<T> datas = data.getResultList();
        mListDataView.setData(datas);

        if (mListDataView.getData().size() > 0) {
            if (data.getPage() >= data.getTotalPage()-1) {
                mListDataView.showNoMore();
            } else {
                mListDataView.autoLoadMore();
            }
        }

        if (mListDataView.getData().size() == 0)
            mListDataView.showEmpty();
        else
            mListDataView.showContent();
    }

    @Override
    public Type getRawType() {
        return PageListDataBean.class;
    }
}
