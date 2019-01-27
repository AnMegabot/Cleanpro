package com.pakpobox.cleanpro.ui.main;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.base.BaseFragmentActivity;
import com.pakpobox.cleanpro.ui.widget.FullScreenVideoView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Function:
 * Created by dubin on 2016/12/26.
 */

public class StartVideoActivity extends BaseFragmentActivity
        implements MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener, MediaPlayer.OnPreparedListener {


    @BindView(R.id.start_video)
    FullScreenVideoView mVideoView;
    @BindView(R.id.start_video_top_im)
    ImageView mTopIm;
    @BindView(R.id.start_video_center_im)
    TextView mCenterIm;
    private boolean mHasPaused;
    private int mVideoPosition;

    @Override
    protected void initViews() {
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

        mVideoView.setOnErrorListener(this);
        mVideoView.setOnPreparedListener(this);
        mVideoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.start_video);
//        mVideoView.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if (mVideoView.isPlaying())
//                    return;
//                mTopIm.setVisibility(View.GONE);
//                mCenterIm.setVisibility(View.GONE);
//                startActivity(new Intent(StartVideoActivity.this, MainActivity.class));
//                finish();
//            }
//        }, 8500);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_start_video;
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        mTopIm.setVisibility(View.GONE);
        mCenterIm.setVisibility(View.GONE);
        startActivity(new Intent(StartVideoActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
        startActivity(new Intent(StartVideoActivity.this, MainActivity.class));
        finish();
        return true;
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        if (mVideoView != null) {
            mVideoView.requestFocus();
            mVideoView.setOnCompletionListener(this);
            mVideoView.seekTo(0);
            mVideoView.start();
        }
        return;

    }


    @Override
    public void onResume() {
        super.onResume();
        if (mHasPaused) {
            if (mVideoView != null) {
                mVideoView.seekTo(mVideoPosition);
                mVideoView.resume();
            }
        }
        return;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mVideoView != null) {
            mVideoPosition = mVideoView.getCurrentPosition();
        }
        mHasPaused = true;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mVideoView != null) {
            mVideoView.stopPlayback();
        }
        return;
    }

    public void pause() {
        if ((mVideoView != null) && (mVideoView.canPause())) {
            mVideoView.setOnCompletionListener(null);
            mVideoView.pause();
        }
        return;

    }

    public void play() {
        if (mVideoView != null) {
            mVideoView.requestFocus();
            mVideoView.setOnCompletionListener(this);
            mVideoView.seekTo(0);
        } else {
            return;
        }
        mVideoView.start();
    }

    public void reLoadVideo() {
        if (mVideoView != null) {
            mVideoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.start_video);
        }
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

    @OnClick(R.id.start_video_skip_btn)
    public void onClick() {
        finish();
        startActivity(new Intent(StartVideoActivity.this, MainActivity.class));
    }
}
