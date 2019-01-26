package com.pakpobox.cleanpro.ui.setting.feedback;

import com.pakpobox.cleanpro.bean.Feedback;
import com.pakpobox.cleanpro.bean.FeedbackReq;
import com.pakpobox.cleanpro.bean.UserBean;
import com.pakpobox.cleanpro.ui.mvp.view.IView;

/**
 * User:Sean.Wei
 * Date:2018/6/21
 * Time:17:03
 */

public interface FeedbackContract {
    interface IFeedbackPresenter {
        /**
         * 反馈
         */
        void createFeedback(FeedbackReq feedbackReq);
    }

    interface IFeedbackView extends IView {
        void feedbackSuccess(Feedback data);
    }
}
