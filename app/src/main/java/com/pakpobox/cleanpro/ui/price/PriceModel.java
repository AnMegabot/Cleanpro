package com.pakpobox.cleanpro.ui.price;

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

public class PriceModel extends BaseModel implements PriceContract.IPriceModel {
    @Override
    public void getPrices(NetCallback<List<Price>> callback) {
        getRequest(getApiUrl(UrlConstainer.PRICE), callback);
    }
}

