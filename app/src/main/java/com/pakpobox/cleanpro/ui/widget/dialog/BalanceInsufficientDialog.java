package com.pakpobox.cleanpro.ui.widget.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.pakpobox.cleanpro.R;

import java.lang.ref.WeakReference;

/**
 * User:Sean.Wei
 * Date:2019/1/27
 * Time:10:27
 */

public class BalanceInsufficientDialog extends Dialog implements DialogInterface.OnCancelListener, View.OnClickListener {
    private WeakReference<Context> mContext = new WeakReference<>(null);
    private volatile static BalanceInsufficientDialog sDialog;
    private View.OnClickListener mOnClickListener;
    private BalanceInsufficientDialog(Context context, View.OnClickListener onClickListener) {
        super(context, R.style.LoadingDialog);

        mContext = new WeakReference<>(context);
        mOnClickListener = onClickListener;

        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_balance_insufficient, null);
        view.findViewById(R.id.dialog_balance_insufficient_canel_btn).setOnClickListener(this);
        view.findViewById(R.id.dialog_balance_insufficient_recharge_btn).setOnClickListener(this);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addContentView(view, lp);

        setOnCancelListener(this);
    }

    @Override
    public void onClick(View view) {
        stop();
        if (null != mOnClickListener)
            mOnClickListener.onClick(view);
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

    public static synchronized void show(Context context, View.OnClickListener onClickListener) {
        show(context, true, onClickListener);
    }

    public static synchronized void show(Context context, boolean cancelable, View.OnClickListener onClickListener) {
        if (sDialog != null && sDialog.isShowing()) {
            sDialog.dismiss();
        }

        if (context == null || !(context instanceof Activity)) {
            return;
        }
        sDialog = new BalanceInsufficientDialog(context, onClickListener);
        sDialog.setCancelable(cancelable);
        sDialog.setCanceledOnTouchOutside(false);

        if (sDialog != null && !sDialog.isShowing() && !((Activity) context).isFinishing()) {
            sDialog.show();
        }
    }

    public static synchronized void stop() {
        if (sDialog != null && sDialog.isShowing()) {
            sDialog.dismiss();
        }
        sDialog = null;
    }
}
