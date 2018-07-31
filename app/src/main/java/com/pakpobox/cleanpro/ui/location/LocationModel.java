package com.pakpobox.cleanpro.ui.location;

import com.pakpobox.cleanpro.bean.PageListDataBean;
import com.pakpobox.cleanpro.bean.Wallet;
import com.pakpobox.cleanpro.bean.location.Site;
import com.pakpobox.cleanpro.common.UrlConstainer;
import com.pakpobox.cleanpro.net.callback.NetCallback;
import com.pakpobox.cleanpro.ui.mvp.model.BaseModel;

/**
 * User:Sean.Wei
 * Date:2018/7/25
 * Time:17:36
 */

public class LocationModel extends BaseModel implements LocationContract.ILocationModel {
    @Override
    public void getLocations(int page, int maxCount, NetCallback<PageListDataBean<Site>> callback) {
        getRequest(getApiUrl(UrlConstainer.LOCATION, page, maxCount), getBaseHttpHeader(),callback);
    }
}

