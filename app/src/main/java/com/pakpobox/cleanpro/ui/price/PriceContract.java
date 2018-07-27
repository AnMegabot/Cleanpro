package com.pakpobox.cleanpro.ui.price;

import com.pakpobox.cleanpro.bean.price.Price;
import com.pakpobox.cleanpro.net.callback.NetCallback;
import com.pakpobox.cleanpro.ui.mvp.view.IListDataView;

import java.util.List;

/**
 * User:Sean.Wei
 * Date:2018/7/25
 * Time:17:03
 */

public interface PriceContract {
    interface IPricePresenter {
        /**
         * 获取价格列表
         */
        void getPrices();
    }

    interface IPriceView extends IListDataView<Price> {

        /**
         * 获取成功
         * @param datas 价格列表
         */
        void getSuccess(List<Price> datas);
    }

    interface IPriceModel {

        /**
         * 获取价格列表
         * @param callback 回调
         */
        void getPrices(NetCallback<List<Price>> callback);
    }
}
