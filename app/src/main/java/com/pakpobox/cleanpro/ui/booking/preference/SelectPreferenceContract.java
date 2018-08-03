package com.pakpobox.cleanpro.ui.booking.preference;

import com.pakpobox.cleanpro.bean.PageListDataBean;
import com.pakpobox.cleanpro.bean.location.Site;
import com.pakpobox.cleanpro.bean.price.Price;
import com.pakpobox.cleanpro.net.callback.NetCallback;
import com.pakpobox.cleanpro.ui.mvp.view.IView;

import java.util.List;

/**
 * User:Sean.Wei
 * Date:2018/7/25
 * Time:17:03
 */

public interface SelectPreferenceContract {
    interface ISelectPreferencePresenter {
        /**
         * 获取价格配置
         */
        void getPrice();
    }

    interface ISelectPreferenceView extends IView {
        /**
         * 获取机器码
         * @return
         */
        String getMachineNo();

        /**
         * 获取成功
         */
        void getSuccess(List<Price> data);
    }

    interface ISelectPreferenceModel {

        /**
         * 获取门店列表
         * @param machineNo 机器码
         * @param callback 回调
         */
        void getPrice(String machineNo, NetCallback<List<Price>> callback);
    }
}
