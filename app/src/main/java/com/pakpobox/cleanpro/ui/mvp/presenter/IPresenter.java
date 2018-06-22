package com.pakpobox.cleanpro.ui.mvp.presenter;

import com.pakpobox.cleanpro.ui.mvp.view.IView;

/**
 * User:Sean.Wei
 * Date:2018/6/20
 * Time:18:42
 */

public interface IPresenter<V extends IView> {

    //绑定View
    void attachView(V view);

    //解除View绑定
    void detachView();

    //检查View是否存在
    void checkAttachView();

    V getView();

    //取消所有请求
    void removeAllDisposable();
}
