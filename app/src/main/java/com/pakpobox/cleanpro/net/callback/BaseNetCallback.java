package com.pakpobox.cleanpro.net.callback;

import android.app.Activity;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.bean.BaseErrorBean;
import com.pakpobox.cleanpro.ui.mvp.presenter.IPresenter;
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

public abstract class BaseNetCallback<T> implements INetCallback, ParameterizedType {
    protected Activity activity;
    protected IView view;
    private IPresenter mPresenter;

    public BaseNetCallback(Activity activity, IPresenter presenter) {
        this.activity = activity;
        this.mPresenter = presenter;
        if (null != mPresenter)
            this.view = mPresenter.getView();
    }

    public IPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void onNext(byte[] data) {
        final String responseStr = new String(data);
        if (!TextUtils.isEmpty(responseStr)) {
            //先判断返回的数据是否是异常状态信息
            try {
                BaseErrorBean baseBean = new Gson().fromJson(responseStr, new TypeToken<BaseErrorBean>() {}.getType());
                if (null != baseBean && 0 != baseBean.getStatusCode()) {
                    onStatusError(baseBean.getStatusCode(), baseBean.getErrorMessage());
                    return;
                }
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
            }

            boolean returnJson = false;
            Type gsonType = getRawType();
            if (gsonType instanceof Class) {
                switch (((Class) gsonType).getSimpleName()) {
                    case "Object":
                    case "String":
                        returnJson = true;
                        break;
                    default:
                        break;
                }
            }

            if (returnJson) {
                if (null != activity && !activity.isFinishing()) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            onSuccess((T) responseStr);
                        }
                    });
                } else {
                    onSuccess((T) responseStr);
                }
            } else {
                try {
                    final T t = (new Gson()).fromJson(responseStr, getGsonFormatType());
                    if (null != activity && !activity.isFinishing()) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                onSuccess(t);
                            }
                        });
                    } else {
                        onSuccess(t);
                    }
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                    onError(e);
                }
            }
        } else {
            ToastUtils.showToast(activity, R.string.app_response_empty);
        }
    }

    protected void onSuccess(T data) {
    }

    protected void onStatusError(int statusCode, String statusMsg) {
        showError(statusMsg);
    }

    @Override
    public void onStart() {
        showLoading();
    }

    @Override
    public void onError(Throwable throwable) {
        hideLoading();
        dealException(throwable);
    }

    @Override
    public void onResponseError(int errorCode, String errorMsg) {
        hideLoading();
        if (null != activity) {
            String msgStr = activity.getString(R.string.app_response_error) + errorCode;
            if (!TextUtils.isEmpty(errorMsg))
                msgStr = msgStr + "-" + errorMsg;
            showError(msgStr);
        }
    }

    @Override
    public void onComplete() {
        hideLoading();
    }

    //显示正在加载
    protected void showLoading() {
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
    protected void hideLoading() {
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
    private void dealException(Throwable t) {
        if (t instanceof ConnectException || t instanceof UnknownHostException) {
            //连接错误
            showError(R.string.app_connect_error);
        } else if (t instanceof InterruptedException) {
            //连接超时
            showError(R.string.app_connect_timeout);
        } else if (t instanceof JsonParseException
                || t instanceof JSONException
                || t instanceof ParseException) {
            //解析错误
            showError(R.string.app_parse_error);
        } else if (t instanceof SocketTimeoutException) {
            //请求超时
            showError(R.string.app_request_timeout);
        } else if (t instanceof UnknownError) {
            //未知错误
            showError(R.string.app_unknown_error);
        } else {
            //未知错误
            showError(R.string.app_system_error);
        }
    }

    protected void showError(final int errorMsgResId) {
        if (null != activity) {
            showError(activity.getString(errorMsgResId));
        }
    }

    protected void showError(final String errorMsg) {
        if (null != activity && !activity.isFinishing()) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ToastUtils.showToast(activity, errorMsg);
                    if (null != view)
                        view.showError();
                }
            });
        }
    }

    //====================================== 重写ParameterizedType内部方法，返回BaseBean类型 ==========================================

    @Override
    public Type[] getActualTypeArguments() {
        Class clz = this.getClass();
        //这里必须注意在外面使用new GsonResponsePasare<GsonResponsePasare.DataInfo>(){};实例化时必须带上{},否则获取到的superclass为Object
        Type superclass = clz.getGenericSuperclass(); //getGenericSuperclass()获得带有泛型的父类
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return parameterized.getActualTypeArguments();
    }

    @Override
    public Type getOwnerType() {
        return null;
    }

    @Override
    public Type getRawType() {
        return getGenericityType();
    }

    private Type getGenericityType() {
        //获取第1个泛型的类型
        Type genericSuperclass = getClass().getGenericSuperclass();
        Type genericityType;
        if (genericSuperclass instanceof ParameterizedType) {
            genericityType = ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];
        } else {
            genericityType = Object.class;
        }
        return genericityType;
    }

    private Type getGsonFormatType() {
        return getRawType() instanceof Class ? this : getRawType();
    }
}
