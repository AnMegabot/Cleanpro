package com.pakpobox.cleanpro.ui.main;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.pakpobox.cleanpro.base.BaseFragmentActivity;

import java.util.concurrent.TimeUnit;

/**
 * 启动页面（闪屏）
 */
public class StartActivity extends BaseFragmentActivity{
    private static final int RC_CAMERA_AND_LOCATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_start);//Activity中不加装界面，splash使用window主题背景，加快启动效果

        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_STABLE //应用的主体内容占用系统状态栏的空间
//                    | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR //状态栏字体颜色反转（只有在6.0才有效）
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION //隐藏导航栏
                    | View.SYSTEM_UI_FLAG_FULLSCREEN //全屏（连同状态栏和导航栏以前隐藏掉）
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN; //布局全屏

            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT); //将状态栏设置成透明色
            getWindow().setNavigationBarColor(Color.TRANSPARENT); //将导航栏设置成透明色
        } else {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        new Thread(new Runnable() {
            @Override public void run() {
                try {
                    TimeUnit.MILLISECONDS.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                startActivity(new Intent(StartActivity.this, MainActivity.class));
                finish();
            }
        }).start();

    }

    //沉浸式体验：全屏显示视图，当下拉状态栏或者上拉导航栏时显示状态栏和导航栏，几秒后自动隐藏
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }
}
