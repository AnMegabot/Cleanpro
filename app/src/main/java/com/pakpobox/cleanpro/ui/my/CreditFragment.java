package com.pakpobox.cleanpro.ui.my;


import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * VIP
 */
public class CreditFragment extends BaseFragment {
    @BindView(R.id.app_toolbar_title_tv)
    TextView mTitleTv;
    @BindView(R.id.app_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.vip_credit_tv)
    TextView creditTv;

    public static CreditFragment newInstance() {
        Bundle args = new Bundle();
        CreditFragment fragment = new CreditFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initViews(View view) {
        StatusBarUtil.setHeight(getContext(), mToolbar);
        mTitleTv.setText(getString(R.string.vip));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pop();
            }
        });

        UserBean userBean = AppSetting.getUserInfo();
        if (null != userBean)
            creditTv.setText(userBean.getCredit() + "");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_credit;
    }
}
