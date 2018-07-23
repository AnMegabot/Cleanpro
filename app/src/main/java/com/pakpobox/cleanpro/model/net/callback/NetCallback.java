package com.pakpobox.cleanpro.model.net.callback;

import android.app.Activity;

import com.pakpobox.cleanpro.bean.BaseBean;
import com.pakpobox.cleanpro.model.net.NetConfig;
import com.pakpobox.cleanpro.ui.mvp.presenter.BasePresenter;

import java.util.List;

/**
 * User:Sean.Wei
 * Date:2018/7/20
 * Time:16:46
 */

public abstract class NetCallback<T, V> extends BaseNetCallback<BaseBean<V>> {

    public NetCallback(Activity activity, BasePresenter mPresenter) {
        super(activity, mPresenter);
    }

    @Override
    public void onResolve(final BaseBean<V> data) {
        if (null != activity && !activity.isFinishing()) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //请求成功
                    if (data.StatusCode == NetConfig.REQUEST_SUCCESS) {
                        onSuccess(data.Result, data.ResultList);
                    } else {
                        //失败
                        onFail(data.StatusCode, data.Message);
                    }
                }
            });
        }
    }

    /**
     * 请求数据成功
     * @param data 数据实体
     * @param datas 数据实体列表
     */
    protected abstract void onSuccess(V data, List<V> datas);

    /**
     * 请求数据失败
     * @param errorCode 错误码
     * @param errorMsg 错误信息
     */
    protected abstract void onFail(int errorCode, String errorMsg);
}
