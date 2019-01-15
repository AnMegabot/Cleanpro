package com.pakpobox.cleanpro.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.pakpobox.cleanpro.ui.mvp.presenter.BasePresenter;
import com.pakpobox.cleanpro.ui.mvp.view.IView;
import com.pakpobox.cleanpro.utils.ToastUtils;

/**
 * MVP模式Fragment基类
 */

public abstract class BasePresenterFragment<P extends BasePresenter<V>, V extends IView> extends BaseFragment implements IView{

    protected P mPresenter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
        //关联View
        attachView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //解除关联
        detachView();
    }

    @Override
    public void showLoading(String msg) {
        showLoadingDialog(msg);
    }

    @Override
    public void hideLoading() {
        hideLoadingDialog();
    }

    @Override
    public void showFail(String msg) {
        ToastUtils.showToast(getContext(), msg);
    }

    @Override
    public void showError() {

    }

    @Override
    public void showEmpty() {

    }

    @Override
    public void dealError(int errorCode) {
//        switch (errorCode) {
//            case NetConfig.LOGIN_INVALID:
//                AppSetting.saveIsLogin(false);
//                AppSetting.saveUserInfo(null);
//                EventBus.getDefault().post(new UserBean());
//                popTo(MainFragment.class, false);
//                getActivity().startActivity(new Intent(getContext(), LoginActivity.class));
//                break;
//        }
    }

    private void detachView() {
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter.removeAllDisposable();
            mPresenter = null;
        }
    }

    private void attachView() {
        if (mPresenter != null) {
            mPresenter.attachView((V) this);
        }
    }

    protected abstract P createPresenter();

}
