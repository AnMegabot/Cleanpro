package com.pakpobox.cleanpro.ui.home;

import com.pakpobox.cleanpro.bean.UserBean;
import com.pakpobox.cleanpro.bean.location.Site;
import com.pakpobox.cleanpro.ui.mvp.view.IListDataView;

/**
 * User:Sean.Wei
 * Date:2018/6/21
 * Time:17:03
 */

public interface HomeContract {
    interface IHomePresenter {
        /**
         * 获取订单列表
         */
        void getPromosList();

        /**
         * 获取用户信息
         */
        void getUserInfo();
    }

    interface IHomeView extends IListDataView<Site> {
        void getUserInfoSuccess(UserBean userBean);
    }
}
