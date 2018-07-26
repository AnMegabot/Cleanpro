package com.pakpobox.cleanpro.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.timmy.tdialog.TDialog;

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
//    private TDialog mLoadingDialog;
//    private TDialog mMsgDialog;
    private View clickingView;//如果用户尚未登录，则保存需要登录才能下一步的被点击的视图，登录成功后自动调用该视图的点击事件

    private ProgressDialog loadingDialog = null;

//    private boolean hasPop = false;

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
        createLoadingDialog();
        loadingDialog.setMessage(title);
        if (!loadingDialog.isShowing())
            loadingDialog.show();
    }

    /**
     * 显示进度框
     */
    protected void showLoadingDialog() {
        createLoadingDialog();
        if (!loadingDialog.isShowing())
            loadingDialog.show();
    }

    //创建LodingDialog
    private void createLoadingDialog() {
        if (loadingDialog == null) {
            loadingDialog = new ProgressDialog(getContext());
            loadingDialog.setCancelable(true);
            loadingDialog.setCanceledOnTouchOutside(false);
        }
    }

    /**
     * 隐藏进度框
     */
    protected void hideLoadingDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

//    protected void showMsgDialog(final String msg) {
//        if (TextUtils.isEmpty(msg))
//            return;
//
//        if (mMsgDialog != null) {
//            mMsgDialog.dismiss();
//        }
//        try {
//            mMsgDialog = new TDialog.Builder(getActivity().getSupportFragmentManager())
//                    .setLayoutRes(R.layout.dialog_message)
//                    .setScreenWidthAspect(getActivity(), 0.8f)
//                    .setGravity(Gravity.CENTER)
//                    .setCancelableOutside(false)
//                    .setCancelable(true)
//                    .addOnClickListener(R.id.dialog_msg_confirm)
//                    .setOnBindViewListener(new OnBindViewListener() {
//                        @Override
//                        public void bindView(BindViewHolder viewHolder) {
//                            TextView tipsTv = viewHolder.getView(R.id.dialog_msg_tv);
//                            tipsTv.setText(msg);
//                        }
//                    })
//                    .setOnViewClickListener(new OnViewClickListener() {
//                        @Override
//                        public void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog) {
//                            switch (view.getId()) {
//                                case R.id.dialog_msg_confirm:
//                                    tDialog.dismiss();
//                                    break;
//                            }
//                        }
//                    })
//                    .create()
//                    .show();
//        } catch (IllegalStateException e) {
//            e.printStackTrace();
//        }
//    }

//    protected void showLoadingDialog() {
//        if (mLoadingDialog != null) {
//            mLoadingDialog.dismiss();
//        }
//        if (null==getActivity())
//            return;
//
//        try {
//            mLoadingDialog = new TDialog.Builder(getActivity().getSupportFragmentManager())
//                    .setLayoutRes(R.layout.dialog_loading)
//                    .setHeight(300)
//                    .setWidth(300)
//                    .setCancelableOutside(false)
//                    .setCancelable(true)
//                    .create()
//                    .show();
//        } catch (IllegalStateException e) {
//            e.printStackTrace();
//        }
//    }

//    protected void dimissLoading() {
//        if (mLoadingDialog != null) {
//            mLoadingDialog.dismiss();
//            mLoadingDialog = null;
//        }
//    }

//    @Override
//    public void onPause() {
//        super.onPause();
//        dimissLoading();
//    }

//    @Override
//    public void pop() {
//        if (!hasPop) {
//            super.pop();
//            hasPop = true;
//        }
//    }

    protected void setClickingView(View view) {
        clickingView = view;
    }

    protected void clickClickingView() {
        if (null != clickingView) {
            clickingView.callOnClick();
            clickingView = null;
        }
    }

}
