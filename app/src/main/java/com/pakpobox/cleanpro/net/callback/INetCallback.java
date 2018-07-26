package com.pakpobox.cleanpro.net.callback;

/**
 * 请求数据回调接口
 * User:Sean.Wei
 * Date:2018/7/20
 * Time:15:25
 */

public interface INetCallback {

    /**
     * 开始
     */
    void onStart();

    /**
     * 响应数据回调
     * @param data 数据
     */
    void onNext(byte[] data);

    /**
     * 错误
     * @param throwable 异常信息
     */
    void onError(Throwable throwable);

    /**
     * 响应错误
     * @param errorCode 响应错误码
     * @param errorMsg  错误信息
     */
    void onResponseError(int errorCode, String errorMsg);

    /**
     * 完成
     */
    void onComplete();

}
