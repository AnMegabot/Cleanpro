package com.pakpobox.cleanpro.model.net.callback;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.base.MyApplication;
import com.pakpobox.cleanpro.bean.BaseBean;
import com.pakpobox.cleanpro.model.net.NetConfig;
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
    protected Type genericityType;

    public BaseNetCallback(Activity activity, BasePresenter mPresenter) {
        this.activity = activity;
        if (null != mPresenter)
            this.view = mPresenter.getView();

        Type genericSuperclass = getClass().getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType) {
            this.genericityType = ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];
        } else {
            this.genericityType = Object.class;
        }
    }

    @Override
    public void onStart() {
        showLoading();
    }

    @Override
    public void onNext(byte[] data) {
        String responseStr = new String(data);
        if (!TextUtils.isEmpty(responseStr)) {
            boolean returnJson = false;
            if (genericityType instanceof Class) {
                switch (((Class) genericityType).getSimpleName()) {
                    case "Object":
                    case "String":
                        returnJson = true;
                        break;
                    default:
                        break;
                }
            }

            if (returnJson) {
                onResolve((T) responseStr);
            } else {
                T t = (new Gson()).fromJson(responseStr, genericityType);
                onResolve(t);
            }
        } else {
            onResponseError(NetConfig.REQUEST_ERROR);
        }
    }

    @Override
    public void onError(Throwable throwable) {
        hideLoading();
        dealException(MyApplication.getContext(), throwable);
    }

    @Override
    public void onResponseError(int errorCode) {
        hideLoading();
        onException(errorCode, MyApplication.getContext());
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
                        default:
                            ToastUtils.showToast(context, context.getString(R.string.app_service_error) + errorCode);
                            break;
                    }
                }
            });
        }
    }

    public abstract void onResolve(T t);
}
