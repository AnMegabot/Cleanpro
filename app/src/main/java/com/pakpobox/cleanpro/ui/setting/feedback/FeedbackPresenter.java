package com.pakpobox.cleanpro.ui.setting.feedback;

import android.app.Activity;

import com.pakpobox.cleanpro.bean.Feedback;
import com.pakpobox.cleanpro.bean.FeedbackReq;
import com.pakpobox.cleanpro.net.callback.BaseNetCallback;
import com.pakpobox.cleanpro.ui.mvp.model.ICommonModel;
import com.pakpobox.cleanpro.ui.mvp.model.IModel;
import com.pakpobox.cleanpro.ui.mvp.model.impl.CommonModel;
import com.pakpobox.cleanpro.ui.mvp.presenter.BasePresenter;

/**
 * User:Sean.Wei
 * Date:2018/6/21
 * Time:17:08
 */

public class FeedbackPresenter extends BasePresenter<FeedbackContract.IFeedbackView> implements FeedbackContract.IFeedbackPresenter {
    private ICommonModel mModel;
    private Activity activity;

    public FeedbackPresenter(Activity activity) {
        this.activity = activity;
        mModel = new CommonModel();
        addModel((IModel) mModel);
    }

    @Override
    public void createFeedback(FeedbackReq feedbackReq) {
        BaseNetCallback<Feedback> callback = new BaseNetCallback<Feedback>(activity, this){
            @Override
            protected void onSuccess(Feedback data) {
                super.onSuccess(data);
                getView().feedbackSuccess(data);
            }
        };

        mModel.createFeedback(feedbackReq, callback);
    }
}
