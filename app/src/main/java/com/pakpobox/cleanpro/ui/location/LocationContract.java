package com.pakpobox.cleanpro.ui.location;

import com.pakpobox.cleanpro.bean.PageListDataBean;
import com.pakpobox.cleanpro.bean.TradingRecort;
import com.pakpobox.cleanpro.bean.Wallet;
import com.pakpobox.cleanpro.bean.location.Site;
import com.pakpobox.cleanpro.net.callback.NetCallback;
import com.pakpobox.cleanpro.ui.mvp.view.IView;

/**
 * User:Sean.Wei
 * Date:2018/7/25
 * Time:17:03
 */

public interface LocationContract {
    interface ILocationPresenter {
        /**
         * 获取钱包
         */
        void getLocations();
    }

    interface ILocationView extends IView {
        /**
         * 获取页码
         * @return
         */
        int getPage();

        /**
         * 获取成功
         */
        void getSuccess(PageListDataBean<Site> data);
    }

    interface ILocationModel {

        /**
         * 获取门店列表
         * @param page 页码
         * @param maxCount 每页条数
         * @param callback 回调
         */
        void getLocations(int page, int maxCount, NetCallback<PageListDataBean<Site>> callback);
    }
}
