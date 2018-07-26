package com.pakpobox.cleanpro.ui.logon.changepsw;

import com.pakpobox.cleanpro.bean.Result;
import com.pakpobox.cleanpro.bean.UserBean;
import com.pakpobox.cleanpro.net.callback.NetCallback;
import com.pakpobox.cleanpro.ui.mvp.view.IView;

/**
 * User:Sean.Wei
 * Date:2018/7/25
 * Time:17:03
 */

public interface ChangePSWContract {
    interface IChangePSWPresenter {
        /**
         * 重置密码
         */
        void changePSW();

        /**
         * 验证密码
         * @return
         */
        boolean verifyPassword();
    }

    interface IChangePSWView extends IView {

        /**
         * 获取密码
         * @return
         */
        String getPassWord();

        /**
         * 获取token
         * @return
         */
        String getToken();

        /**
         * 重置成功
         */
        void changeSuccess();
    }

    interface IChangePSWModel {

        /**
         * 重置密码
         * @param token token
         * @param newPassword 新登录密码
         * @param callback 回调
         */
        void changePSW(String token, String newPassword, NetCallback<UserBean> callback);
    }
}
