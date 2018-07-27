package com.pakpobox.cleanpro.ui.wallet.trlist;

import com.pakpobox.cleanpro.bean.PageListDataBean;
import com.pakpobox.cleanpro.bean.TradingRecort;
import com.pakpobox.cleanpro.common.UrlConstainer;
import com.pakpobox.cleanpro.net.callback.NetCallback;
import com.pakpobox.cleanpro.ui.mvp.model.BaseModel;

/**
 * User:Sean.Wei
 * Date:2018/7/25
 * Time:17:36
 */

public class TRListModel extends BaseModel implements TRListContract.ITRListModel {
    @Override
    public void getRechargeDetailList(int page, int maxCount, NetCallback<PageListDataBean<TradingRecort>> callback) {
        getRequest(getApiUrl(UrlConstainer.WALLET_QUERY, page, maxCount), getBaseHttpHeader(),callback);
    }
}

