package com.pakpobox.cleanpro.ui.wallet;

import com.pakpobox.cleanpro.bean.TradingRecort;
import com.pakpobox.cleanpro.ui.mvp.view.IListDataView;

/**
 * User:Sean.Wei
 * Date:2018/6/27
 * Time:12:09
 */

public interface TRListContract {
    interface ITRListPresenter {
        void getRechargeDetailList();
    }

    interface ITRListView extends IListDataView<TradingRecort> {

    }
}
