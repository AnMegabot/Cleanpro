package com.pakpobox.cleanpro.ui.booking.preference;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.base.BasePresenterFragment;
import com.pakpobox.cleanpro.bean.CreateOrderRequest;
import com.pakpobox.cleanpro.bean.price.Price;
import com.pakpobox.cleanpro.common.Const;
import com.pakpobox.cleanpro.ui.booking.create.CreateOrderFragment;
import com.pakpobox.cleanpro.ui.widget.RadioGroupPro;
import com.pakpobox.cleanpro.utils.StatusBarUtil;
import com.pakpobox.cleanpro.utils.SystemUtils;
import com.pakpobox.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.pakpobox.cleanpro.common.Const.CLEAN_TYPE.DRYER;
import static com.pakpobox.cleanpro.common.Const.CLEAN_TYPE.LAUNDRY;

/**
 * 选择偏好
 */
public class SelectPreferenceFragment extends BasePresenterFragment<SelectPreferencePresenter, SelectPreferenceContract.ISelectPreferenceView> implements SelectPreferenceContract.ISelectPreferenceView {

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

    private Const.CLEAN_TYPE mType = LAUNDRY;
    private String mOrderNo = null;
    private String mMachineNo = null;

    private String mTemperature = "Cold";
    private int mTime = 23;
    private double coldPrice = 3;
    private double warmPrice = 3;
    private double hotPrice = 3;
    private double dryerBasePrice = 4;
    private double dryerExtraPrice = 1;
    private int dryerExtraTime = 5;

    public static SelectPreferenceFragment newInstance(String orderNo, String machineNo, String type) {
        Bundle args = new Bundle();
        args.putString("orderNo", orderNo);
        args.putString("machineNo", machineNo);
        args.putString("type", type);
        SelectPreferenceFragment fragment = new SelectPreferenceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (null != bundle) {
            String type = bundle.getString("type");
            switch (type.toUpperCase()) {
                case "LAUNDRY":
                    mType = LAUNDRY;
                    break;
                case "DRYER":
                    mType = DRYER;
                    break;
            }
            mOrderNo = bundle.getString("orderNo");
            mMachineNo = bundle.getString("machineNo");
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
            case LAUNDRY:
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
                                mTemperature = "Cold";
                                break;
                            case R.id.select_pref_temp_warm_rb:
                                setLayoutEnable(mTempColdLauout, false);
                                setLayoutEnable(mTempWarmLauout, true);
                                setLayoutEnable(mTempHotLauout, false);
                                mTemperature = "Warm";
                                break;
                            case R.id.select_pref_temp_hot_rb:
                                setLayoutEnable(mTempColdLauout, false);
                                setLayoutEnable(mTempWarmLauout, false);
                                setLayoutEnable(mTempHotLauout, true);
                                mTemperature = "Hot";
                                break;
                        }
                    }
                });
                break;
            case DRYER:
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

        mPresenter.getPrice();
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

    @Override
    public String getMachineNo() {
        return URLEncoder.encode(mOrderNo + "#" + mMachineNo);
    }

    @Override
    public void getSuccess(List<Price> data) {
        if (null != data) {
            switch (mType) {
                case LAUNDRY:
                    for (Price price : data) {
                        if ("Laundry".equals(price.getName_en()) && null!=price.getSku_list() && price.getSku_list().size()>=3) {
                            coldPrice = price.getSku_list().get(0).getPrice();
                            mTempColdPayAmountTv.setText(SystemUtils.formatFloat2Str(coldPrice));
                            warmPrice = price.getSku_list().get(1).getPrice();
                            mTempWarmPayAmountTv.setText(SystemUtils.formatFloat2Str(warmPrice));
                            hotPrice = price.getSku_list().get(2).getPrice();
                            mTempHotPayAmountTv.setText(SystemUtils.formatFloat2Str(hotPrice));
                        }
                    }
                    break;
                case DRYER:
                    for (Price price : data) {
                        if ("Dryer".equals(price.getName_en()) && null!=price.getSku_list() && price.getSku_list().size()>=1) {
                            dryerBasePrice = price.getSku_list().get(0).getPrice();
                            dryerExtraPrice = price.getSku_list().get(0).getContinue_price();
                            dryerExtraTime = price.getSku_list().get(0).getContinue_value();

                            mDryerPayAmountTv.setText(SystemUtils.formatFloat2Str(computeDryerAmount()));
                        }
                    }
                    break;
            }
        }
    }

    private double computeDryerAmount() {
        int extrTime = mTime - 23;
        Logger.d("哈哈：" + extrTime);
        if (extrTime > 0) {
            return dryerBasePrice + (extrTime/dryerExtraTime) * dryerExtraPrice;
        } else {
            return dryerBasePrice;
        }
    }

    @Override
    protected SelectPreferencePresenter createPresenter() {
        return new SelectPreferencePresenter(getActivity());
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
                mTime -= 5;
                if (mTime < 23)
                    mTime = 23;
                mDryerTimeTv.setText(mTime + "");
                mDryerPayAmountTv.setText(SystemUtils.formatFloat2Str(computeDryerAmount()));
                break;
            case R.id.select_pref_dryer_pluss_btn:
                mTime += 5;
                if (mTime > 100)
                    mTime = 100;
                mDryerTimeTv.setText(mTime + "");
                mDryerPayAmountTv.setText(SystemUtils.formatFloat2Str(computeDryerAmount()));
                break;
            case R.id.select_pref_next_btn:
                double mAmount = 0;
                switch (mType) {
                    case LAUNDRY:
                        switch (mTemperature) {
                            case "Cold":
                                mAmount = coldPrice;
                                break;
                            case "Warm":
                                mAmount = warmPrice;
                                break;
                            case "Hot":
                                mAmount = hotPrice;
                                break;
                        }
                        break;
                    case DRYER:
                        mAmount = computeDryerAmount();
                        break;
                }
                CreateOrderRequest createOrderRequest = new CreateOrderRequest();
                JSONObject goodsObj = new JSONObject();
                switch (mType) {
                    case LAUNDRY:
                        createOrderRequest.setOrder_type("LAUNDRY");
                        try {
                            goodsObj.put("temperature", mTemperature);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    case DRYER:
                        createOrderRequest.setOrder_type("DRYER");
                        try {
                            goodsObj.put("time", mTime + "m");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                }
                createOrderRequest.setMachine_no(mOrderNo + "#" + mMachineNo);
                createOrderRequest.setClient_type("ANDROID");
                createOrderRequest.setTotal_amount(mAmount);
                createOrderRequest.setGoods_info(goodsObj.toString());
                createOrderRequest.setClient_version(SystemUtils.getVersionName(getContext()));
                start(CreateOrderFragment.newInstance(createOrderRequest));
                break;
        }
    }
}
