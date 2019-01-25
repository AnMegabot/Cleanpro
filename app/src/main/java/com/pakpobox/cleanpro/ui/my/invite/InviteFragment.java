package com.pakpobox.cleanpro.ui.my.invite;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.application.AppSetting;
import com.pakpobox.cleanpro.base.BaseFragment;
import com.pakpobox.cleanpro.bean.UserBean;
import com.pakpobox.cleanpro.utils.StatusBarUtil;
import com.pakpobox.cleanpro.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 邀请
 */
public class InviteFragment extends BaseFragment {

    @BindView(R.id.app_toolbar_title_tv)
    TextView mTitleTv;
    @BindView(R.id.app_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.invite_code_tv)
    TextView inviteCodeTv;

    public static InviteFragment newInstance() {
        Bundle args = new Bundle();
        InviteFragment fragment = new InviteFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initViews(View view) {
        StatusBarUtil.setHeight(getContext(), mToolbar);
        mTitleTv.setText(getString(R.string.invite_friends));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pop();
            }
        });

        UserBean userBean = AppSetting.getUserInfo();
        if (null != userBean) {
            inviteCodeTv.setText(userBean.getMyInviteCode());
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_invite;
    }

    @OnClick(R.id.invite_code_tv)
    public void onClick() {
        ClipboardManager cmb = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setPrimaryClip(ClipData.newPlainText(null, inviteCodeTv.getText().toString().trim()));
        ToastUtils.showToast(getContext(), getString(R.string.invite_copied_toast));
    }
}
