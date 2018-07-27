package com.pakpobox.cleanpro.ui.wallet.trlist;

import com.pakpobox.cleanpro.bean.Order;
import com.pakpobox.cleanpro.bean.PageListDataBean;
import com.pakpobox.cleanpro.bean.TradingRecort;
import com.pakpobox.cleanpro.bean.Wallet;
import com.pakpobox.cleanpro.net.callback.NetCallback;
import com.pakpobox.cleanpro.ui.mvp.view.IListDataView;

import java.util.List;

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
        /**
         * 获取成功
         * @param datas 订单列表
         */
        void getSuccess(PageListDataBean<TradingRecort> datas);
    }

    interface ITRListModel {

        /**
         * 获取钱包流水
         * @param page 页数
         * @param maxCount 每页条数
         * @param callback 回调
         */
        void getRechargeDetailList(int page, int maxCount, NetCallback<PageListDataBean<TradingRecort>> callback);
    }
}
