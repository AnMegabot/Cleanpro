package com.pakpobox.cleanpro.ui.mvp.model.impl;

import com.pakpobox.cleanpro.common.UrlConstainer;
import com.pakpobox.cleanpro.net.callback.INetCallback;
import com.pakpobox.cleanpro.ui.mvp.model.ICommonModel;

/**
 * User:Sean.Wei
 * Date:2019/1/10
 * Time:17:58
 */

public class CommonModel extends BaseModel implements ICommonModel {
    @Override
    public void getPrices(INetCallback callback) {
        getRequest(getApiUrl(UrlConstainer.PRICE), callback);
    }

    @Override
    public void getPrice(String machineNo, INetCallback callback) {
        getRequest(getApiUrl(UrlConstainer.MACHINE_PRICE, machineNo), getBaseHttpHeader(),callback);
    }

    @Override
    public void getLocations(int page, int maxCount, INetCallback callback) {
        getRequest(getApiUrl(UrlConstainer.LOCATION, page, maxCount), getBaseHttpHeader(),callback);
    }
}
