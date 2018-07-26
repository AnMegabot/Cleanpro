package com.pakpobox.cleanpro.net.callback;

import android.app.Activity;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.pakpobox.cleanpro.bean.BaseBean;
import com.pakpobox.cleanpro.net.NetConfig;
import com.pakpobox.cleanpro.ui.mvp.presenter.BasePresenter;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * User:Sean.Wei
 * Date:2018/7/20
 * Time:16:46
 */

public abstract class NetCallback<T> extends BaseNetCallback<T> {
    protected Type genericityType;

    public NetCallback(Activity activity, BasePresenter mPresenter) {
        super(activity, mPresenter);

        Type genericSuperclass = getClass().getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType) {
            this.genericityType = ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];
        } else {
            this.genericityType = Object.class;
        }
    }

    @Override
    public void onNext(byte[] data) {
        final String responseStr = new String(data);
        if (!TextUtils.isEmpty(responseStr)) {
            BaseBean baseBean = new Gson().fromJson(responseStr, new TypeToken<BaseBean>() {}.getType());
            if (null == baseBean || 0 == baseBean.getStatusCode()) {
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
                    if (null != view && null != activity && !activity.isFinishing()) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                onSuccess((T) responseStr);
                            }
                        });
                    }
                } else {
                    try {
                        final T t = (new Gson()).fromJson(responseStr, genericityType);
                        if (null != view && null != activity && !activity.isFinishing()) {
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    onSuccess(t);
                                }
                            });
                        }
                    } catch (JsonSyntaxException e) {
                        e.printStackTrace();
                        onError(e);
                    }
                }
            } else {
                onResponseError(baseBean.getStatusCode(), baseBean.getErrorMessage());
            }
        } else {
            onResponseError(NetConfig.REQUEST_ERROR, null);
        }
    }

    /**
     * 请求数据成功
     * @param data 数据实体
     */
    protected abstract void onSuccess(T data);

//    /**
//     * 请求数据失败
//     * @param errorCode 错误码
//     * @param errorMsg 错误信息
//     */
//    protected abstract void onFail(int errorCode, String errorMsg);
}
