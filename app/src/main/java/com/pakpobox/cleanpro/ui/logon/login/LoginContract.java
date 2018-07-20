package com.pakpobox.cleanpro.ui.logon.login;

import com.pakpobox.cleanpro.ui.mvp.view.IView;

/**
 * 登录页面接口
 * User:Sean.Wei
 * Date:2018/7/13
 * Time:18:09
 */

public interface LoginContract {
    interface ILoginPresenter {
        void login();
    }

    interface ILoginView extends IView{
    }

    interface ILoginModel {

    }
}
