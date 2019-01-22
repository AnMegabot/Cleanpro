package com.pakpobox.cleanpro.ui.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.pakpobox.cleanpro.R;

import java.lang.ref.WeakReference;

/**
 * User:Sean.Wei
 * Date:2019/1/22
 * Time:19:17
 */

public class BottomMenuDialog extends Dialog implements DialogInterface.OnCancelListener {
    private WeakReference<Context> mContext = new WeakReference<>(null);
    private volatile static BottomMenuDialog sDialog;
    private TextView firstBtn, secondBtn, cancelBtn;

    private BottomMenuDialog(Context context, CharSequence... menus) {
        super(context, R.style.LoadingDialog);

        mContext = new WeakReference<>(context);

        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_bottom_menu_layout, null);
        firstBtn = (TextView) view.findViewById(R.id.dialog_bottom_menu_first_btn);
        secondBtn = (TextView) view.findViewById(R.id.dialog_bottom_menu_second_btn);
        cancelBtn = (TextView) view.findViewById(R.id.dialog_bottom_menu_cancel_btn);
        if (menus.length > 0)
            firstBtn.setText(menus[0]);
        if (menus.length > 1)
            secondBtn.setText(menus[1]);
        if (menus.length > 2)
            cancelBtn.setText(menus[2]);
        setContentView(view);

        Window window = this.getWindow();
        if (window != null) {
            window.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
            window.setGravity(Gravity.BOTTOM);
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(params);
        }

        setOnCancelListener(this);
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        // 点手机返回键等触发Dialog消失，应该取消正在进行的网络请求等
        Context context = mContext.get();
        if (context != null) {
//            Toast.makeText(context, "cancel", Toast.LENGTH_SHORT).show();
//            MyHttpClient.cancelRequests(context);
        }
    }

    public static synchronized void showMenu(Context context, CharSequence... menus) {
        if (sDialog != null && sDialog.isShowing()) {
            sDialog.dismiss();
        }

        if (context == null || !(context instanceof Activity)) {
            return;
        }
        sDialog = new BottomMenuDialog(context, menus);
        sDialog.setCancelable(true);
        sDialog.setCanceledOnTouchOutside(false);

        if (sDialog != null && !sDialog.isShowing() && !((Activity) context).isFinishing()) {
            sDialog.show();
        }
    }

    public static synchronized void stopMenu() {
        if (sDialog != null && sDialog.isShowing()) {
            sDialog.dismiss();
        }
        sDialog = null;
    }
}
