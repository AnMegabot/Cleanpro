package com.pakpobox.cleanpro.ui.booking.preference;

import com.pakpobox.cleanpro.bean.price.Price;
import com.pakpobox.cleanpro.common.UrlConstainer;
import com.pakpobox.cleanpro.net.callback.NetCallback;
import com.pakpobox.cleanpro.ui.mvp.model.BaseModel;

import java.util.List;

/**
 * User:Sean.Wei
 * Date:2018/7/25
 * Time:17:36
 */

public class SelectPreferenceModel extends BaseModel implements SelectPreferenceContract.ISelectPreferenceModel {
    @Override
    public void getPrice(String machineNo, NetCallback<List<Price>> callback) {
        getRequest(getApiUrl(UrlConstainer.MACHINE_PRICE, machineNo), getBaseHttpHeader(),callback);
    }
}

