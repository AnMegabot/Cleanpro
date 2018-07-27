package com.pakpobox.cleanpro.net.callback;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.application.MyApplication;
import com.pakpobox.cleanpro.net.NetConfig;
import com.pakpobox.cleanpro.ui.mvp.presenter.BasePresenter;
import com.pakpobox.cleanpro.ui.mvp.view.IView;
import com.pakpobox.cleanpro.utils.ToastUtils;

import org.json.JSONException;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;

/**
 * User:Sean.Wei
 * Date:2018/7/20
 * Time:16:03
 */

public abstract class BaseNetCallback<T> implements INetCallback {
    protected Activity activity;
    protected IView view;
    private BasePresenter mPresenter;

    public BaseNetCallback(Activity activity, BasePresenter presenter) {
        this.activity = activity;
        this.mPresenter = presenter;
        if (null != mPresenter)
            this.view = mPresenter.getView();
    }

    public BasePresenter getPresenter() {
        return mPresenter;
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
    public void onResponseError(int errorCode, String errorMsg) {
        hideLoading();
        onException(MyApplication.getContext(), errorCode, errorMsg);
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
            onException(context, NetConfig.CONNECT_ERROR);
        } else if (t instanceof InterruptedException) {
            //连接超时
            onException(context, NetConfig.CONNECT_TIMEOUT);
        } else if (t instanceof JsonParseException
                || t instanceof JSONException
                || t instanceof ParseException) {
            //解析错误
            onException(context, NetConfig.PARSE_ERROR);
        } else if (t instanceof SocketTimeoutException) {
            //请求超时
            onException(context, NetConfig.REQUEST_TIMEOUT);
        } else if (t instanceof UnknownError) {
            //未知错误
            onException(context, NetConfig.UNKNOWN_ERROR);
        } else {
            //未知错误
            onException(context, NetConfig.UNKNOWN_ERROR);
        }
    }

    void onException(final Context context, final int errorCode) {
        onException(context,errorCode, null);
    }

    void onException(final Context context, final int errorCode, final String errorMsg) {
        if (null != activity && !activity.isFinishing()) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    view.showError();
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
                        case NetConfig.LOGIN_INVALID:
                            ToastUtils.showToast(context, R.string.app_login_invalid_error);
                            if (null != view)
                                view.dealError(NetConfig.LOGIN_INVALID);
                            break;
                        default:
                            ToastUtils.showToast(context, errorMsg);
                            break;
                    }
                }
            });
        }
    }
}
