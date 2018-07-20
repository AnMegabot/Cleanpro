package com.pakpobox.cleanpro.model.net.callback;

/**
 * 请求数据回调接口
 * User:Sean.Wei
 * Date:2018/7/20
 * Time:15:25
 */

public interface INetCallback<T> {
    /**
     * 开始
     */
    void onStart();

    /**
     * 响应数据回调
     * @param data 数据
     */
    void onNext(T data);

    /**
     * 错误
     * @param throwable 异常信息
     */
    void onError(Throwable throwable);

    /**
     * 完成
     */
    void onComplete();
}
