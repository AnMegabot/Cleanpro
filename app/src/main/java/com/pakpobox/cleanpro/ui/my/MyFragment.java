package com.pakpobox.cleanpro.ui.my;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pakpobox.cleanpro.GlideApp;
import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.application.AppSetting;
import com.pakpobox.cleanpro.base.BaseFragment;
import com.pakpobox.cleanpro.bean.UserBean;
import com.pakpobox.cleanpro.common.UrlConstainer;
import com.pakpobox.cleanpro.ui.account.LoginActivity;
import com.pakpobox.cleanpro.ui.main.MainFragment;
import com.pakpobox.cleanpro.ui.mvp.model.impl.BaseModel;
import com.pakpobox.cleanpro.ui.my.personal.PersonalInfoFragment;
import com.pakpobox.cleanpro.ui.setting.SettingFragment;
import com.pakpobox.cleanpro.ui.setting.feedback.FeedbackFragment;
import com.pakpobox.cleanpro.ui.wallet.WalletFragment;
import com.pakpobox.cleanpro.ui.widget.GlideCircleTransform;
import com.pakpobox.cleanpro.utils.StatusBarUtil;
import com.pakpobox.cleanpro.utils.SystemUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我的
 */
public class MyFragment extends BaseFragment {

    @BindView(R.id.my_info_btn)
    RelativeLayout mInfoBtn;
    @BindView(R.id.my_account_tv)
    TextView mAccountTv;
    @BindView(R.id.my_portrait_im)
    ImageView mPortraitIm;
    @BindView(R.id.my_credits_tv)
    TextView mCreditsTv;
    @BindView(R.id.my_vip_layout)
    LinearLayout mVipLayout;
    @BindView(R.id.my_wallet_tv)
    TextView mWalletTv;
    @BindView(R.id.my_coupons_tv)
    TextView mCouponsTv;
    @BindView(R.id.my_unred_view)
    TextView mUnredView;

    public static MyFragment newInstance() {
        Bundle args = new Bundle();
        MyFragment fragment = new MyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected boolean isRegisterEvent() {
        return true;
    }

    @Override
    protected void initViews(View view) {
        StatusBarUtil.setHeight(getContext(), mInfoBtn);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        setAccountText(AppSetting.getUserInfo());
    }

    @OnClick({R.id.my_vip_layout, R.id.my_account_layout, R.id.my_wallet, R.id.my_coupons, R.id.my_get_rm2, R.id.my_introduction, R.id.my_feedback, R.id.my_settings, R.id.my_info_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.my_vip_layout:
                break;
            case R.id.my_account_layout:
                if (!AppSetting.isLogin()) {
                    getActivity().startActivity(new Intent(getContext(), LoginActivity.class));
                    setClickingView(view);
                } else {
                    if (getParentFragment() instanceof MainFragment) {
                        ((MainFragment) getParentFragment()).start(PersonalInfoFragment.newInstance());
                    }
                }
                break;
            case R.id.my_wallet:
                if (!AppSetting.isLogin()) {
                    getActivity().startActivity(new Intent(getContext(), LoginActivity.class));
                    setClickingView(view);
                } else {
                    if (getParentFragment() instanceof MainFragment) {
                        ((MainFragment) getParentFragment()).start(WalletFragment.newInstance());
                    }
                }
                break;
            case R.id.my_coupons:
                break;
            case R.id.my_get_rm2:
                break;
            case R.id.my_introduction:
                break;
            case R.id.my_feedback:
                if (getParentFragment() instanceof MainFragment) {
                    ((MainFragment) getParentFragment()).start(FeedbackFragment.newInstance());
                }
                break;
            case R.id.my_settings:
                if (getParentFragment() instanceof MainFragment) {
                    ((MainFragment) getParentFragment()).start(SettingFragment.newInstance());
                }
                break;
            case R.id.my_info_btn:
                break;
        }
    }

    public void setAccountText(UserBean data) {
        if (AppSetting.isLogin() && null != data) {
            mAccountTv.setText(data.getLoginName());
            mCreditsTv.setVisibility(View.VISIBLE);
            mCreditsTv.setText(String.format(getString(R.string.my_credits), data.getCredit()));
            mWalletTv.setVisibility(View.VISIBLE);
            mWalletTv.setText(String.format(getString(R.string.my_wallet_info), SystemUtils.formatFloat2Str(45.00)));
            mCouponsTv.setVisibility(View.VISIBLE);
            mCouponsTv.setText(String.format(getString(R.string.my_coupons_info), data.getCouponCount()));
            mVipLayout.setVisibility(View.VISIBLE);
            GlideApp.with(getContext())
                    .load(new BaseModel().getApiUrl(UrlConstainer.GET_HEAD_IMAGE, data.getHeadImageUrl()))
                    .skipMemoryCache(true)
                    .placeholder(R.mipmap.icon_avatar)
                    .transform(new GlideCircleTransform())
                    .into(mPortraitIm);
            clickClickingView();
        } else {
            mAccountTv.setText(getString(R.string.my_no_login_tips));
            mCreditsTv.setVisibility(View.GONE);
            mWalletTv.setVisibility(View.GONE);
            mCouponsTv.setVisibility(View.GONE);
            mVipLayout.setVisibility(View.GONE);}
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginEvent(UserBean event) {
        setAccountText(event);
    }

}
