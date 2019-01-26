package com.pakpobox.cleanpro.ui.setting.feedback;


import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.application.AppSetting;
import com.pakpobox.cleanpro.base.BaseFragment;
import com.pakpobox.cleanpro.base.BasePresenterFragment;
import com.pakpobox.cleanpro.bean.Feedback;
import com.pakpobox.cleanpro.bean.FeedbackReq;
import com.pakpobox.cleanpro.bean.UserBean;
import com.pakpobox.cleanpro.ui.widget.tabbar.TabBarItem;
import com.pakpobox.cleanpro.ui.widget.tabbar.TabBarLayout;
import com.pakpobox.cleanpro.utils.StatusBarUtil;
import com.pakpobox.cleanpro.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Feedback
 */
public class FeedbackFragment extends BasePresenterFragment<FeedbackPresenter, FeedbackContract.IFeedbackView> implements FeedbackContract.IFeedbackView {
    private static final int RC_LOCATION_PERM = 0x0011;
    @BindView(R.id.app_toolbar_title_tv)
    TextView mTitleTv;
    @BindView(R.id.app_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.feedback_option_tabbar_layout)
    TabBarLayout mTabbarLayout;
    @BindView(R.id.feedback_comment_et)
    EditText mCommentEt;
    @BindView(R.id.feedback_submit_btn)
    Button mSubmitBtn;
    @BindView(R.id.feedback_hotline_tv)
    TextView mHotlineTv;

    private String hotling = "12345678";
    private String feedbackType;

    public static FeedbackFragment newInstance() {
        Bundle args = new Bundle();
        FeedbackFragment fragment = new FeedbackFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initViews(View view) {
        StatusBarUtil.setHeight(getContext(), mToolbar);
        mTitleTv.setText(getString(R.string.feedback));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pop();
            }
        });

        feedbackType = getString(R.string.feedback_option_laundry);
        mTabbarLayout.setOnItemSelectedListener(new TabBarLayout.OnItemSelectedListener() {
            @Override
            public void onItemSelected(TabBarItem bottomBarItem, int previousPosition, int currentPosition) {
                switch (currentPosition) {
                    case 0:
                        feedbackType = getString(R.string.feedback_option_laundry);
                        break;
                    case 1:
                        feedbackType = getString(R.string.feedback_option_dryer);
                        break;
                    case 2:
                        feedbackType = getString(R.string.feedback_option_price);
                        break;
                    case 3:
                        feedbackType = getString(R.string.feedback_option_quality);
                        break;
                    case 4:
                        feedbackType = getString(R.string.feedback_option_useprocess);
                        break;
                    case 5:
                        feedbackType = getString(R.string.feedback_option_others);
                        break;
                }
            }
        });

        //监听输入框内容变化
        mCommentEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mSubmitBtn.setEnabled(!TextUtils.isEmpty(mCommentEt.getText().toString().trim()));
            }
        });

        mSubmitBtn.setEnabled(!TextUtils.isEmpty(mCommentEt.getText().toString().trim()));
        String hotline = "Hotline：<font color='#29d1ff'>12345678</font>";
        mHotlineTv.setText(Html.fromHtml(hotline));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_feedback;
    }

    @Override
    protected FeedbackPresenter createPresenter() {
        return new FeedbackPresenter(getActivity());
    }

    @Override
    public void feedbackSuccess(Feedback data) {
        ToastUtils.showToast(getContext(), getString(R.string.feedback_success));
    }

    @OnClick({R.id.feedback_submit_btn, R.id.feedback_hotline_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.feedback_submit_btn:
                FeedbackReq feedbackReq = new FeedbackReq();
                UserBean userBean = AppSetting.getUserInfo();
                if (null != userBean)
                    feedbackReq.setLoginName(userBean.getLoginName());
                feedbackReq.setType(feedbackType);
                feedbackReq.setContent(mCommentEt.getText().toString());
                mPresenter.createFeedback(feedbackReq);
                break;
            case R.id.feedback_hotline_tv:
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    String[] locationPerms = {Manifest.permission.CALL_PHONE};
                    requestPermissions(locationPerms, RC_LOCATION_PERM);
                    return;
                } else {
                    callHotline();
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RC_LOCATION_PERM:
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    callHotline();
                }
                break;
        }

    }

    private void callHotline() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Call tel:" + hotling);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent = new Intent();
                intent.setAction("android.intent.action.CALL");
                intent.setData(Uri.parse("tel:" + hotling));
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();
            }
        });
        builder.show();

    }
}