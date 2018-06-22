package com.pakpobox.cleanpro.base;

import android.os.Bundle;

import com.pakpobox.cleanpro.ui.mvp.presenter.BasePresenter;
import com.pakpobox.cleanpro.ui.mvp.view.IView;

/**
 * MVP模式Acitivity基类
 */

public abstract class BasePresenterActivity<P extends BasePresenter<V>, V extends IView> extends BaseFragmentActivity implements IView {

   protected P mPresenter;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        mPresenter = createPresenter();
        if (mPresenter != null) {
            mPresenter.attachView((V) this);
        }
    }

    protected abstract P createPresenter();

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter.removeAllDisposable();//取消所有请求
        }
        super.onDestroy();
    }
}
