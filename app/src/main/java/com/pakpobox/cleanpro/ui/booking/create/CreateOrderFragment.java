package com.pakpobox.cleanpro.ui.booking.create;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;

import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.base.BasePresenterFragment;
import com.pakpobox.cleanpro.bean.CreateOrderRequest;
import com.pakpobox.cleanpro.bean.Order;
import com.pakpobox.cleanpro.ui.booking.BookSuccessFragment;
import com.pakpobox.cleanpro.ui.widget.RadioGroupPro;
import com.pakpobox.cleanpro.utils.StatusBarUtil;
import com.pakpobox.cleanpro.utils.SystemUtils;
import com.timmy.tdialog.TDialog;
import com.timmy.tdialog.base.BindViewHolder;
import com.timmy.tdialog.listener.OnBindViewListener;
import com.timmy.tdialog.listener.OnViewClickListener;
import com.tuo.customview.VerificationCodeView;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 创建订单
 */
public class CreateOrderFragment extends BasePresenterFragment<CreateOrderPresenter, CreateOrderContract.ICreateOrderView> implements CreateOrderContract.ICreateOrderView {

    @BindView(R.id.app_toolbar_title_tv)
    TextView mTitleTv;
    @BindView(R.id.app_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.order_create_order_type_tv)
    TextView mOrderTypeTv;
    @BindView(R.id.order_create_machine_no_tv)
    TextView mMachineNoTv;
    @BindView(R.id.order_create_goods_info_title_tv)
    TextView mGoodsInfoTitleTv;
    @BindView(R.id.order_create_goods_info_tv)
    TextView mGoodsInfoTv;
    @BindView(R.id.order_create_amount_tv)
    TextView mAmountTv;
    @BindView(R.id.order_create_payment_rg)
    RadioGroupPro mPaymentRg;
    @BindView(R.id.order_create_credits_tv)
    TextView mCreditsTv;
    @BindView(R.id.order_create_discount_tv)
    TextView mDiscountTv;
    @BindView(R.id.order_create_credits_switch)
    Switch mCreditsSwitch;
    @BindView(R.id.create_order_pay_btn)
    Button mPayBtn;
    Unbinder unbinder;

    private CreateOrderRequest mCreateOrderRequest = null;

    public static CreateOrderFragment newInstance(CreateOrderRequest createOrderRequest) {
        Bundle args = new Bundle();
        args.putParcelable("createOrderRequest", createOrderRequest);
        CreateOrderFragment fragment = new CreateOrderFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (null != bundle) {
            mCreateOrderRequest = bundle.getParcelable("createOrderRequest");
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_create_order;
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
        if (null == mCreateOrderRequest)
            return;

        switch (mCreateOrderRequest.getOrder_type()) {
            case "LAUNDRY":
                mTitleTv.setText(getString(R.string.home_laundry));
                mOrderTypeTv.setText(getString(R.string.home_laundry));
                break;
            case "DRYER":
                mTitleTv.setText(getString(R.string.home_dryer));
                mOrderTypeTv.setText(getString(R.string.home_dryer));
                break;
        }

        mMachineNoTv.setText(mCreateOrderRequest.getMachine_no());
        if ("LAUNDRY".equals(mCreateOrderRequest.getOrder_type())) {
            mGoodsInfoTitleTv.setText(getString(R.string.orders_Temperature));
            try {
                JSONObject laundryObj = new JSONObject(mCreateOrderRequest.getGoods_info());
                mGoodsInfoTv.setText(laundryObj.getString("temperature"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            mGoodsInfoTitleTv.setText(getString(R.string.orders_Time));
            try {
                JSONObject laundryObj = new JSONObject(mCreateOrderRequest.getGoods_info());
                mGoodsInfoTv.setText(laundryObj.getString("time") + "min");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        mAmountTv.setText(String.format(getString(R.string.orders_amount_format), SystemUtils.formatFloat2Str(mCreateOrderRequest.getTotal_amount())));
        mPayBtn.setText(String.format(getString(R.string.booking_pay), SystemUtils.formatFloat2Str(mCreateOrderRequest.getTotal_amount())));
        mCreditsTv.setText(String.format(getString(R.string.booking_credits), 10));
        mDiscountTv.setText(String.format(getString(R.string.booking_Discount), SystemUtils.formatFloat2Str(mCreateOrderRequest.getTotal_amount())));
        mPaymentRg.setOnCheckedChangeListener(new RadioGroupPro.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroupPro group, int checkedId) {
                switch (checkedId) {
                    case R.id.order_create_ipy88_rb:
                        mCreateOrderRequest.setPayment_platform("IPAY88");
                        break;
                    case R.id.order_create_wallet_rb:
                        mCreateOrderRequest.setPayment_platform("WALLET");
                        break;
                }
            }
        });
        mPaymentRg.check(R.id.order_create_ipy88_rb);

        mCreditsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mCreateOrderRequest.setCredits(b ? 1 : 0);
            }
        });
    }

    @Override
    public CreateOrderRequest getCreateOrderParam() {
        return mCreateOrderRequest;
    }

    @Override
    public void createSuccess(Order data) {
        start(BookSuccessFragment.newInstance(data));
        EventBus.getDefault().post(data);
    }

    @Override
    public void checkPayPswSuccess() {
        mPresenter.createOrder();
    }

    @Override
    protected CreateOrderPresenter createPresenter() {
        return new CreateOrderPresenter(getActivity());
    }

    @OnClick({R.id.order_create_ipy88_layout, R.id.order_create_wallet_layout, R.id.create_order_pay_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.order_create_ipy88_layout:
                mPaymentRg.check(R.id.order_create_ipy88_rb);
                break;
            case R.id.order_create_wallet_layout:
                mPaymentRg.check(R.id.order_create_wallet_rb);
                break;
            case R.id.create_order_pay_btn:
                new TDialog.Builder(getActivity().getSupportFragmentManager())
                        .setLayoutRes(R.layout.dialog_payment_psw)
                        .setScreenWidthAspect(getActivity(), 0.8f)
                        .setGravity(Gravity.CENTER)
                        .setCancelableOutside(false)
                        .setCancelable(true)
                        .addOnClickListener(R.id.dialog_payment_psw_close_btn)
                        .setOnBindViewListener(new OnBindViewListener() {
                            @Override
                            public void bindView(BindViewHolder viewHolder) {
                                final View closeView = viewHolder.getView(R.id.dialog_payment_psw_close_btn);
                                final VerificationCodeView pswView = viewHolder.getView(R.id.dialog_payment_psw_et);
                                pswView.setInputCompleteListener(new VerificationCodeView.InputCompleteListener() {
                                    @Override
                                    public void inputComplete() {
                                        if (pswView.getInputContent().length() >= 6) {
                                            closeView.callOnClick();
//                                            mPresenter.checkPayPsw(pswView.getInputContent());
                                            mPresenter.createOrder();
                                        }
                                    }

                                    @Override
                                    public void deleteContent() {

                                    }
                                });
                            }
                        })
                        .setOnViewClickListener(new OnViewClickListener() {
                            @Override
                            public void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog) {
                                switch (view.getId()) {
                                    case R.id.dialog_payment_psw_close_btn:
                                        tDialog.dismiss();
                                        break;
                                }
                            }
                        })
                        .create()
                        .show();
                break;
        }
    }
}