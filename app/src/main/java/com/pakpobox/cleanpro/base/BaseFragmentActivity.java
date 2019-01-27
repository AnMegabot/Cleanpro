package com.pakpobox.cleanpro.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;

import com.pakpobox.cleanpro.ui.widget.dialog.LoadingDialog;
import com.pakpobox.cleanpro.utils.KeyBoardHelper;
import com.pakpobox.cleanpro.utils.language.LanguageUtil;
import com.pakpobox.cleanpro.utils.language.MyContextWrapper;

import org.greenrobot.eventbus.EventBus;

import java.util.Locale;

import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * FragmentActivity基类
 * User:Sean.Wei
 * Date:2018/2/28
 * Time:15:ic_launcher
 */

public abstract class BaseFragmentActivity extends SupportActivity {
    private View clickingView;//如果用户尚未登录，则保存需要登录才能下一步的被点击的视图，登录成功后自动调用该视图的点击事件
    private KeyBoardHelper keyBoardHelper;
    private boolean hasPop = false;//防止多次快速点击出现重复触发回退

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        keyBoardHelper = new KeyBoardHelper(this);
        if (isRegisterEvent())
            EventBus.getDefault().register(this);

        if (0 != getLayoutId()) {
            setContentView(getLayoutId());
        }
        ButterKnife.bind(this);
        initViews();
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //设置点击非EditText区域时隐藏软键盘
        keyBoardHelper.hideKeyboardByTouchOutsize(ev, true);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        // .. create or get your new Locale object here.
        Locale newLocale = LanguageUtil.getAppLocale(newBase);
        Context context = MyContextWrapper.wrap(newBase, newLocale);
        super.attachBaseContext(context);
    }

    @Override
    protected void onDestroy() {
        if (isRegisterEvent())
            EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    /**
     * 显示带消息的进度框
     *
     * @param title 提示
     */
    protected void showLoadingDialog(String title) {
        LoadingDialog.showLoading(this, title);
    }

    /**
     * 显示进度框
     */
    protected void showLoadingDialog() {
        LoadingDialog.showLoading(this);
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

    /**
     * 是否注册消息分发
     * @return boolean
     */
    protected boolean isRegisterEvent(){
        return false;
    }

    protected abstract void initViews();

    protected abstract int getLayoutId();
}
