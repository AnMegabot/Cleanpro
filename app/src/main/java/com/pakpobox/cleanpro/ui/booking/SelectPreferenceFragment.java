package com.pakpobox.cleanpro.ui.booking;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.base.BaseFragment;
import com.pakpobox.cleanpro.ui.home.HomeFragment;
import com.pakpobox.cleanpro.ui.widget.RadioGroupPro;
import com.pakpobox.cleanpro.utils.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 选择偏好
 */
public class SelectPreferenceFragment extends BaseFragment {

    @BindView(R.id.toolbar_title_tv)
    TextView mTitleTv;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.select_pref_im)
    ImageView typeIm;
    @BindView(R.id.select_pref_machine_tv)
    TextView mMachineTv;
    @BindView(R.id.select_pref_tips_tv)
    TextView mTipsTv;
    @BindView(R.id.select_pref_temp_cold_pay_amount_tv)
    TextView mTempColdPayAmountTv;
    @BindView(R.id.select_pref_temp_cold_lauout)
    LinearLayout mTempColdLauout;
    @BindView(R.id.select_pref_temp_warm_pay_amount_tv)
    TextView mTempWarmPayAmountTv;
    @BindView(R.id.select_pref_temp_warm_lauout)
    LinearLayout mTempWarmLauout;
    @BindView(R.id.select_pref_temp_hot_pay_amount_tv)
    TextView mTempHotPayAmountTv;
    @BindView(R.id.select_pref_temp_hot_lauout)
    LinearLayout mTempHotLauout;
    @BindView(R.id.select_pref_laundry_layout)
    RadioGroupPro mLaundryLayout;
    @BindView(R.id.select_pref_dryer_pay_amount_tv)
    TextView mDryerPayAmountTv;
    @BindView(R.id.select_pref_dryer_time_tv)
    TextView mDryerTimeTv;
    @BindView(R.id.select_pref_dryer_layout)
    LinearLayout mDryerLayout;
    @BindView(R.id.select_pref_dryer_less_btn)
    ImageView selectPrefDryerLessBtn;
    @BindView(R.id.select_pref_dryer_pluss_btn)
    ImageView selectPrefDryerPlussBtn;
    @BindView(R.id.select_pref_content_llt)
    LinearLayout selectPrefContentLlt;
    @BindView(R.id.select_pref_next_btn)
    Button selectPrefNextBtn;

    private int mType = HomeFragment.LAUNDRY_SCAN_REQUEST_CODE;

    private int mTime = 23;

    public static SelectPreferenceFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt("type", type);
        SelectPreferenceFragment fragment = new SelectPreferenceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (null != bundle) {
            mType = bundle.getInt("type");
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_select_preference;
    }

    @Override
    protected void initViews(View view) {
        StatusBarUtil.setHeight(getContext(), mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pop();
            }
        });

        switch (mType) {
            case HomeFragment.LAUNDRY_SCAN_REQUEST_CODE:
                mTitleTv.setText(getString(R.string.home_laundry));
                typeIm.setImageResource(R.mipmap.icon_laundry);
                mTipsTv.setText(getString(R.string.booking_choose_temperature));
                mLaundryLayout.setVisibility(View.VISIBLE);
                mDryerLayout.setVisibility(View.GONE);
                mLaundryLayout.setOnCheckedChangeListener(new RadioGroupPro.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroupPro group, int checkedId) {
                        switch (checkedId) {
                            case R.id.select_pref_temp_cold_rb:
                                setLayoutEnable(mTempColdLauout, true);
                                setLayoutEnable(mTempWarmLauout, false);
                                setLayoutEnable(mTempHotLauout, false);
                                break;
                            case R.id.select_pref_temp_warm_rb:
                                setLayoutEnable(mTempColdLauout, false);
                                setLayoutEnable(mTempWarmLauout, true);
                                setLayoutEnable(mTempHotLauout, false);
                                break;
                            case R.id.select_pref_temp_hot_rb:
                                setLayoutEnable(mTempColdLauout, false);
                                setLayoutEnable(mTempWarmLauout, false);
                                setLayoutEnable(mTempHotLauout, true);
                                break;
                        }
                    }
                });
                break;
            case HomeFragment.DRYER_SCAN_REQUEST_CODE:
                mTitleTv.setText(getString(R.string.home_dryer));
                typeIm.setImageResource(R.mipmap.icon_dryer);
                mTipsTv.setText(getString(R.string.booking_choose_time));
                mLaundryLayout.setVisibility(View.GONE);
                mDryerLayout.setVisibility(View.VISIBLE);
                mDryerTimeTv.setText(mTime + "");
                break;
        }
        mLaundryLayout.check(R.id.select_pref_temp_cold_rb);
        setLayoutEnable(mTempColdLauout, true);
        setLayoutEnable(mTempWarmLauout, false);
        setLayoutEnable(mTempHotLauout, false);
    }

    private void setLayoutEnable(ViewGroup viewGroup, boolean isEnable) {
        int len = viewGroup.getChildCount();
        for (int i = 0; i < len; i++) {
            if (viewGroup.getChildAt(i) instanceof TextView) {
                TextView tv = (TextView) viewGroup.getChildAt(i);
                tv.setEnabled(isEnable);
            } else if (viewGroup.getChildAt(i) instanceof ViewGroup) {
                setLayoutEnable((ViewGroup) viewGroup.getChildAt(i), isEnable);
            }
        }
    }

    @OnClick({R.id.select_pref_temp_cold_lauout, R.id.select_pref_temp_warm_lauout, R.id.select_pref_temp_hot_lauout, R.id.select_pref_dryer_less_btn, R.id.select_pref_dryer_pluss_btn, R.id.select_pref_next_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.select_pref_temp_cold_lauout:
                mLaundryLayout.check(R.id.select_pref_temp_cold_rb);
                break;
            case R.id.select_pref_temp_warm_lauout:
                mLaundryLayout.check(R.id.select_pref_temp_warm_rb);
                break;
            case R.id.select_pref_temp_hot_lauout:
                mLaundryLayout.check(R.id.select_pref_temp_hot_rb);
                break;
            case R.id.select_pref_dryer_less_btn:
                mTime--;
                if (mTime < 23)
                    mTime = 23;
                mDryerTimeTv.setText(mTime + "");
                break;
            case R.id.select_pref_dryer_pluss_btn:
                mTime++;
                if (mTime > 100)
                    mTime = 100;
                mDryerTimeTv.setText(mTime + "");
                break;
            case R.id.select_pref_next_btn:
                start(CreateOrderFragment.newInstance(mType));
                break;
        }
    }
}
