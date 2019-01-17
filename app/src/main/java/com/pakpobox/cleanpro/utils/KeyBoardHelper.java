package com.pakpobox.cleanpro.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ScrollView;

/**
 * 软键盘操作助手
 */
public class KeyBoardHelper {
    private static final String TAG = "KeyBoardHelper";
    private Activity activity;
    private OnKeyBoardListener mOnKeyBoardListener;
    // 空白高度 = 屏幕高度 - 当前 Activity 的可见区域的高度
    private int blankHeight = 0;
    private View mResizeView;//跟随软键盘调整尺寸的视图

    private float downX = 0;
    private float downY = 0;
    private float moveX = 0;
    private float moveY = 0;

    public KeyBoardHelper(Activity activity) {
        this.activity = activity;
    }


    /**
     * 点击非EditText时隐藏软键盘
     * @param ev MotionEvent
     * @param isAllowSlide 是否允许滑动视图时不隐藏软键盘
     */
    public void hideKeyboardByTouchOutsize(MotionEvent ev, boolean isAllowSlide) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = ev.getX();//float DownX
                downY = ev.getY();//float DownY
                moveX = 0;
                moveY = 0;
                break;
            case MotionEvent.ACTION_MOVE:
                moveX += Math.abs(ev.getX() - downX);//X轴距离
                moveY += Math.abs(ev.getY() - downY);//y轴距离
                downX = ev.getX();
                downY = ev.getY();
                break;
            case MotionEvent.ACTION_UP:
                int touchSlop = ViewConfiguration.get(activity).getScaledTouchSlop();//系统所认为最小滑动距离
                //判断是否为点击事件
                if (!isAllowSlide || (moveX<touchSlop && moveY<touchSlop)) {
                    // 获得当前得到焦点的View，一般情况下就是EditText（特殊情况就是轨迹球或者实体案件会移动焦点）
                    View v = activity.getCurrentFocus();
                    if (v != null && isShouldHideInput(v, ev)) {
                        hideSoftInput(activity, v.getWindowToken());
                    }
                }
                break;

        }
    }

    /**
     * 设置软键盘弹出时需要保持显示的视图
     * @param resizeView 需要跟随软键盘调整尺寸的视图
     * @param onKeyBoardListener 接听器
     */
    public void setKeyboardListener(View resizeView, OnKeyBoardListener onKeyBoardListener) {
        this.mResizeView = resizeView;
        this.mOnKeyBoardListener = onKeyBoardListener;
        View content = activity.findViewById(android.R.id.content);
        // content.addOnLayoutChangeListener(listener); 这个方法有时会出现一些问题
        content.getViewTreeObserver().addOnGlobalLayoutListener(onGlobalLayoutListener);
    }

    /**
     * 销毁监听
     */
    public void removeKeyboardListener() {
        View content = activity.findViewById(android.R.id.content);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            content.getViewTreeObserver().removeOnGlobalLayoutListener(onGlobalLayoutListener);
        } else {
            content.getViewTreeObserver().removeGlobalOnLayoutListener(onGlobalLayoutListener);
        }
    }

    private ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {



        @Override
        public void onGlobalLayout() {
            //获取当前屏幕内容的高度
            int screenHeight = activity.getResources().getDisplayMetrics().heightPixels;
            //获取View可见区域的bottom
            Rect rect = new Rect();
            //DecorView即为activity的顶级view
            activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
            int newBlankheight = screenHeight - rect.bottom;
            if (newBlankheight != blankHeight) {
                //考虑到虚拟导航栏的情况（虚拟导航栏情况下：screenHeight = rect.bottom + 虚拟导航栏高度）
                //选取screenHeight*2/3进行判断
                if (screenHeight * 2 / 3 > rect.bottom) {
                    //软键盘已弹出
                    final int blankHeight = newBlankheight;
                    //上移处理
                    if (null != mResizeView) {
                        int[] buttonArray = new int[2];
                        mResizeView.getLocationOnScreen(buttonArray);
                        int viewHeight = mResizeView.getHeight();
                        int offset = blankHeight - (screenHeight - (buttonArray[1] + viewHeight));
                        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) mResizeView.getLayoutParams();
                        lp.bottomMargin = offset + lp.bottomMargin;
                        mResizeView.setLayoutParams(lp);

                        if (mResizeView instanceof ScrollView) {
                            mResizeView.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    View v = activity.getCurrentFocus();
                                    if (null != v)
                                        mResizeView.scrollTo(0, v.getTop() + v.getHeight());
                                }
                            },100);
                        }else if (mResizeView instanceof ViewGroup) {
                            int count = ((ViewGroup) mResizeView).getChildCount();
                            for (int i = 0; i < count; i++) {
                                final View view = ((ViewGroup) mResizeView).getChildAt(i);
                                if (view instanceof ScrollView) {
                                    view.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            View v = activity.getCurrentFocus();
                                            if (null != v)
                                                view.scrollTo(0, v.getTop() + v.getHeight());
                                        }
                                    },100);
                                }
                            }

                        }
                    }

                    if (mOnKeyBoardListener != null) {
                        mOnKeyBoardListener.onKeyBoardPop(newBlankheight);
                    }
                } else {
                    if (null != mResizeView) {
                        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) mResizeView.getLayoutParams();
                        if (lp.bottomMargin != 0) {
                            lp.bottomMargin = 0;
                            mResizeView.setLayoutParams(lp);
                        }
                    }

                    if (mOnKeyBoardListener != null) {
                        mOnKeyBoardListener.onKeyBoardHide(blankHeight);
                    }
                }
            }
            blankHeight = newBlankheight;
        }
    };

    /**
     * 软键盘弹出监听
     */
    public interface OnKeyBoardListener {
        /**
         * 键盘弹出
         * @param keyBoardheight 键盘高度
         */
        void onKeyBoardPop(int keyBoardheight);

        /**
         * 键盘消失
         * @param oldKeyBoardheight 键盘高度
         */
        void onKeyBoardHide(int oldKeyBoardheight);
    }

    /**
     * 底部虚拟按键栏的高度
     * @return
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private int getSoftButtonsBarHeight() {
        DisplayMetrics metrics = new DisplayMetrics();
        //这个方法获取可能不是真实屏幕的高度
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int usableHeight = metrics.heightPixels;
        //获取当前屏幕的真实高度
        activity.getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
        int realHeight = metrics.heightPixels;
        if (realHeight > usableHeight) {
            return realHeight - usableHeight;
        } else {
            return 0;
        }
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时没必要隐藏
     * @param v 当前获得焦点的视图
     * @param event 点击事件
     * @return boolean 是否需要隐藏软键盘
     */
    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 多种隐藏软件盘方法的其中一种
     * @param context 上下文
     * @param token
     */
    public static void hideSoftInput(Context context,IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token,
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

}
