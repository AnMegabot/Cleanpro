package com.pakpobox.cleanpro.ui.my.personal;

import com.pakpobox.cleanpro.bean.CreateOrderRequest;
import com.pakpobox.cleanpro.bean.Order;
import com.pakpobox.cleanpro.bean.Result;
import com.pakpobox.cleanpro.bean.ResultBean;
import com.pakpobox.cleanpro.bean.UserBean;
import com.pakpobox.cleanpro.ui.mvp.view.IView;

import java.io.File;

/**
 * User:Sean.Wei
 * Date:2018/7/25
 * Time:17:03
 */

public interface PersonalInfoContract {
    interface IPersonalInfoPresenter {
        void uploadHeadImage(String filePaht);

        void updateGender(String gender);
        void updateBirthday(long birthday);
    }

    interface IUpdatePostcodePresenter {
        void updatePostcode(String postcode);
    }

    interface IPersonalInfoView extends IView {
        void uploadHeadImageSuccess(ResultBean result);

        void updateProfileSuccess(UserBean userBean);
    }

    interface IUpdatePostcodeInfoView extends IView {
        void updatePostcodeSuccess(UserBean userBean);
    }
}
