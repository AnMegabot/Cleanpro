package com.pakpobox.cleanpro.ui.mvp.model;

import com.pakpobox.cleanpro.net.callback.INetCallback;

/**
 * User:Sean.Wei
 * Date:2019/1/10
 * Time:17:57
 */

public interface ICommonModel {
    /**
     * 获取价格列表
     * @param callback 回调
     */
    void getPrices(INetCallback callback);

    /**
     * 获取指定机器价格列表
     * @param machineNo 机器码
     * @param callback 回调
     */
    void getPrice(String machineNo, INetCallback callback);

    /**
     * 获取门店列表
     * @param page 页码
     * @param maxCount 每页条数
     * @param callback 回调
     */
    void getLocations(int page, int maxCount, INetCallback callback);
}
