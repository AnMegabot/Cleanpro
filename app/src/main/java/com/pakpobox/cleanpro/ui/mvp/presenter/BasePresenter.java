package com.pakpobox.cleanpro.ui.mvp.presenter;

import com.pakpobox.cleanpro.net.HttpManager;
import com.pakpobox.cleanpro.ui.mvp.model.IModel;
import com.pakpobox.cleanpro.ui.mvp.view.IView;

import java.util.ArrayList;
import java.util.List;


public abstract class BasePresenter<V extends IView> implements IPresenter<V> {
    private V view;
    private List<IModel> modelList;

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
    public void addModel(IModel model) {
        if (null == modelList)
            modelList = new ArrayList<>();

        modelList.add(model);
    }

    @Override
    public void removeAllDisposable() {
        for (IModel model : modelList) {
            if (null != model) {
                HttpManager httpManager = model.getHttpRequest();
                if (null != httpManager)
                    httpManager.clearAllRequest();
            }
        }
        modelList.clear();
    }
}
