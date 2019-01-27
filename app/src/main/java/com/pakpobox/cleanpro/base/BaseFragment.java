package com.pakpobox.cleanpro.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pakpobox.cleanpro.ui.widget.dialog.LoadingDialog;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Fragment基类
 * User:Sean.Wei
 * Date:2018/3/8
 * Time:11:21
 */

public abstract class BaseFragment extends SupportFragment {
    private View clickingView;//如果用户尚未登录，则保存需要登录才能下一步的被点击的视图，登录成功后自动调用该视图的点击事件

    private boolean hasPop = false;//防止多次快速点击出现重复触发回退

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isRegisterEvent())
            EventBus.getDefault().register(this);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = null;
        int layoutId = getLayoutId();
        if (layoutId != 0) {
            view = inflater.inflate(getLayoutId(), container, false);
            ButterKnife.bind(this, view);
            initViews(view);
        }
        return view;
    }

    protected abstract void initViews(View view);

    protected abstract int getLayoutId();

    @Override
    public void onDestroy() {
        if (isRegisterEvent())
            EventBus.getDefault().unregister(this);

        hideLoadingDialog();
        super.onDestroy();
    }

    /**
     * 是否注册消息分发
     * @return boolean
     */
    protected boolean isRegisterEvent(){
        return false;
    }

    /**
     * 显示带消息的进度框
     *
     * @param title 提示
     */
    protected void showLoadingDialog(String title) {
        LoadingDialog.showLoading(getContext(), title);
    }

    /**
     * 显示进度框
     */
    protected void showLoadingDialog() {
        LoadingDialog.showLoading(getContext());
    }

    /**
     * 隐藏进度框
     */
    protected void hideLoadingDialog() {
        LoadingDialog.stopLoading();
    }

    protected void setClickingView(View view) {
        clickingView = view;
    }

    protected void clickClickingView() {
        if (null != clickingView) {
            clickingView.callOnClick();
            clickingView = null;
        }
    }

    @Override
    public void pop() {
        if (!hasPop) {
            super.pop();
            hasPop = true;
        }
    }
}
