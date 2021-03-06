package com.pakpobox.cleanpro.ui.wallet;


import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.application.AppSetting;
import com.pakpobox.cleanpro.base.BasePresenterFragment;
import com.pakpobox.cleanpro.bean.UserBean;
import com.pakpobox.cleanpro.bean.Wallet;
import com.pakpobox.cleanpro.ui.account.paypsw.CheckOldPaymentPswFragment;
import com.pakpobox.cleanpro.ui.account.paypsw.ForgetPaymentPswFragment;
import com.pakpobox.cleanpro.ui.wallet.creditcard.CreditCardRcFragment;
import com.pakpobox.cleanpro.ui.wallet.scan.ScanRcFragment;
import com.pakpobox.cleanpro.ui.wallet.trlist.TRListFragment;
import com.pakpobox.cleanpro.utils.StatusBarUtil;
import com.pakpobox.cleanpro.utils.SystemUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我的钱包
 */
public class WalletFragment extends BasePresenterFragment<WalletPresenter, WalletContract.IWalletView> implements WalletContract.IWalletView {

    @BindView(R.id.app_toolbar_title_tv)
    TextView mTitleTv;
    @BindView(R.id.app_toolbar_action_tv)
    TextView mActionTv;
    @BindView(R.id.app_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.wallet_balance_tv)
    TextView mBalanceTv;

    public static WalletFragment newInstance() {
        Bundle args = new Bundle();
        WalletFragment fragment = new WalletFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initViews(View view) {
        StatusBarUtil.setHeight(getContext(), mToolbar);
        mTitleTv.setText(getString(R.string.wallet_my));
        mActionTv.setVisibility(View.VISIBLE);
        mActionTv.setTextColor(getContext().getResources().getColor(R.color.my_mvp_bg_color));
        mActionTv.setText(R.string.wallet_details);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pop();
            }
        });

        mPresenter.getWallet();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_wallet;
    }

    @Override
    public void getSuccess(Wallet data) {
//        mBalanceTv.setText(data.getCurrencyUnit() + SystemUtils.formatFloat2Str(data.getBalance()/100.0));
        mBalanceTv.setText(SystemUtils.formatFloat2Str(data.getBalance()/100.0));
    }

    @Override
    protected WalletPresenter createPresenter() {
        return new WalletPresenter(getActivity());
    }

    @OnClick({R.id.app_toolbar_action_tv, R.id.wallet_recharge, R.id.wallet_payment_setting})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.app_toolbar_action_tv:
                start(TRListFragment.newInstance());
                break;
            case R.id.wallet_recharge:
                start(CreditCardRcFragment.newInstance());
                break;
            case R.id.wallet_payment_setting:
                UserBean userBean = AppSetting.getUserInfo();
                if (null != userBean & TextUtils.isEmpty(userBean.getPayPassword()))
                    start(ForgetPaymentPswFragment.newInstance());
                else
                    start(CheckOldPaymentPswFragment.newInstance());
                break;
        }
    }
}