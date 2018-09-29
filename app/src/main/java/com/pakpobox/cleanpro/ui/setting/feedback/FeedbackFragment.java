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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.base.BaseFragment;
import com.pakpobox.cleanpro.ui.widget.tabbar.TabBarItem;
import com.pakpobox.cleanpro.ui.widget.tabbar.TabBarLayout;
import com.pakpobox.cleanpro.utils.StatusBarUtil;
import com.pakpobox.cleanpro.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Feedback
 */
public class FeedbackFragment extends BaseFragment {
    private static final int RC_LOCATION_PERM = 0x0011;
    @BindView(R.id.toolbar_title_tv)
    TextView mTitleTv;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.feedback_option_tabbar_layout)
    TabBarLayout mTabbarLayout;
    @BindView(R.id.feedback_comment_et)
    EditText mCommentEt;
    @BindView(R.id.feedback_submit_btn)
    Button mSubmitBtn;
    @BindView(R.id.feedback_hotline_tv)
    TextView mHotlineTv;
    Unbinder unbinder;

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

        mTabbarLayout.setOnItemSelectedListener(new TabBarLayout.OnItemSelectedListener() {
            @Override
            public void onItemSelected(TabBarItem bottomBarItem, int previousPosition, int currentPosition) {

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.feedback_submit_btn, R.id.feedback_hotline_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.feedback_submit_btn:
                ToastUtils.showToast(getContext(), R.string.app_coming_soon);
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
        builder.setMessage("Call tel:123465");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent = new Intent();
                intent.setAction("android.intent.action.CALL");
                intent.setData(Uri.parse("tel:" + "123456"));
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