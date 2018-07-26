package com.pakpobox.cleanpro.ui.wallet;


import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.base.BaseFragment;
import com.pakpobox.cleanpro.base.BasePresenterFragment;
import com.pakpobox.cleanpro.bean.Wallet;
import com.pakpobox.cleanpro.ui.logon.changepsw.ChangePSWContract;
import com.pakpobox.cleanpro.ui.logon.changepsw.ChangePSWPresenter;
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

    @BindView(R.id.toolbar_title_tv)
    TextView mTitleTv;
    @BindView(R.id.toolbar_action_tv)
    TextView mActionTv;
    @BindView(R.id.toolbar)
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
        mBalanceTv.setText(data.getCurrencyUnit() + SystemUtils.formatFloat2Str(data.getBalance()));
    }

    @Override
    protected WalletPresenter createPresenter() {
        return new WalletPresenter(getActivity());
    }

    @OnClick({R.id.toolbar_action_tv, R.id.wallet_rc_creditcard, R.id.wallet_rc_scan})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_action_tv:
                start(TRListFragment.newInstance());
                break;
            case R.id.wallet_rc_creditcard:
                start(CreditCardRcFragment.newInstance());
                break;
            case R.id.wallet_rc_scan:
                start(ScanRcFragment.newInstance());
                break;
        }
    }
}