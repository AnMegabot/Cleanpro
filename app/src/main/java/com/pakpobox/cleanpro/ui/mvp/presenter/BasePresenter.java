package com.pakpobox.cleanpro.ui.mvp.presenter;

import com.pakpobox.cleanpro.application.AppSetting;
import com.pakpobox.cleanpro.ui.mvp.view.IView;

import java.util.ArrayList;
import java.util.List;


public class BasePresenter<V extends IView> implements IPresenter<V> {
    private V view;

    private List<String> disposableUrlList;

    //绑定View
    @Override
    public void attachView(V view) {
        this.view = view;
    }

    //解除View绑定
    @Override
    public void detachView() {
        this.view = null;
    }

    //获取绑定的View
    @Override
    public V getView() {
        checkAttachView();
        return view;
    }

    //检查View是否存在
    @Override
    public void checkAttachView() {
        if (view == null)
            throw new RuntimeException("You have no binding this view");
    }

    @Override
    public void addDisposable(String disposableUrl) {
        if (null == disposableUrlList)
            disposableUrlList = new ArrayList<>();

        if (!disposableUrlList.contains(disposableUrl))
            disposableUrlList.add(disposableUrl);
    }

    @Override
    public void removeAllDisposable() {
        if (null != disposableUrlList) {
            for (String disposableUrl : disposableUrlList) {
                AppSetting.getInstance().getHttpManager().stopHttpRequest(disposableUrl);
            }
        }
    }
}
