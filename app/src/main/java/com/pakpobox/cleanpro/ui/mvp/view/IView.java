package com.pakpobox.cleanpro.ui.mvp.view;

/**
 * User:Sean.Wei
 * Date:2018/6/20
 * Time:18:40
 */
public interface IView {


    /**
     * 显示进度条
     *
     */
    void showLoading(String msg);

    /**
     * 隐藏进度条
     */
    void hideLoading();

    /**
     * 失败
     * @param msg
     */
    void showFail(String msg);

    /**
     * 错误
     */
    void showError();

    /**
     * 没有数据
     */
    void showEmpty();//没有数据

    /**
     * 处理错误
     * @param errorCode 错误代码
     */
    void dealError(int errorCode);

}
