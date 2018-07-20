package com.pakpobox.cleanpro.model.net.callback;

import android.app.Activity;
import android.content.Context;

import com.google.gson.JsonParseException;
import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.base.MyApplication;
import com.pakpobox.cleanpro.bean.BaseBean;
import com.pakpobox.cleanpro.model.net.NetConfig;
import com.pakpobox.cleanpro.ui.mvp.presenter.BasePresenter;
import com.pakpobox.cleanpro.ui.mvp.view.IView;
import com.pakpobox.cleanpro.utils.ToastUtils;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;

/**
 * User:Sean.Wei
 * Date:2018/7/20
 * Time:16:03
 */

public abstract class BaseNetCallback<T> implements INetCallback<BaseBean<T>> {
    protected Activity activity;
    protected IView view;

    public BaseNetCallback(Activity activity, BasePresenter mPresenter) {
        this.activity = activity;
        this.view = mPresenter.getView();
    }

    @Override
    public void onStart() {
        showLoading();
    }

    @Override
    public void onError(Throwable throwable) {
        hideLoading();
        dealException(MyApplication.getContext(), throwable);
    }

    @Override
    public void onComplete() {
        hideLoading();
    }

    //显示正在加载
    private void showLoading() {
        if (null != view && null != activity && !activity.isFinishing()) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    view.showLoading("");
                }
            });
        }
    }
    //隐藏正在加载
    private void hideLoading() {
        if (null != view && null != activity && !activity.isFinishing()) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    view.hideLoading();
                }
            });
        }
    }

    //处理异常错误
    void dealException(Context context, Throwable t) {

        if (t instanceof ConnectException || t instanceof UnknownHostException) {
            //连接错误
            onException(NetConfig.CONNECT_ERROR, context);
        } else if (t instanceof InterruptedException) {
            //连接超时
            onException(NetConfig.CONNECT_TIMEOUT, context);
        } else if (t instanceof JsonParseException
                || t instanceof JSONException
                || t instanceof ParseException) {
            //解析错误
            onException(NetConfig.PARSE_ERROR, context);
        } else if (t instanceof SocketTimeoutException) {
            //请求超时
            onException(NetConfig.REQUEST_TIMEOUT, context);
        } else if (t instanceof UnknownError) {
            //未知错误
            onException(NetConfig.UNKNOWN_ERROR, context);
        } else {
            //未知错误
            onException(NetConfig.UNKNOWN_ERROR, context);
        }
    }


    void onException(final int errorCode, final Context context) {
        if (null != activity && !activity.isFinishing()) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    switch (errorCode) {
                        case NetConfig.CONNECT_ERROR:
                            ToastUtils.showToast(context, R.string.app_connect_error);
                            break;
                        case NetConfig.CONNECT_TIMEOUT:
                            ToastUtils.showToast(context, R.string.app_connect_timeout);
                            break;
                        case NetConfig.PARSE_ERROR:
                            ToastUtils.showToast(context, R.string.app_parse_error);
                            break;
                        case NetConfig.REQUEST_TIMEOUT:
                            ToastUtils.showToast(context, R.string.app_request_timeout);
                            break;
                        case NetConfig.UNKNOWN_ERROR:
                            ToastUtils.showToast(context, R.string.app_unknown_error);
                            break;
                    }
                }
            });
        }
    }
}
