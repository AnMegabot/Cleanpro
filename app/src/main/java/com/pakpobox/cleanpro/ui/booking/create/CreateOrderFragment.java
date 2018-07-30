package com.pakpobox.cleanpro.ui.booking.create;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.base.BaseFragment;
import com.pakpobox.cleanpro.base.BasePresenterFragment;
import com.pakpobox.cleanpro.bean.CreateOrderRequest;
import com.pakpobox.cleanpro.bean.Order;
import com.pakpobox.cleanpro.common.Const;
import com.pakpobox.cleanpro.ui.booking.BookSuccessFragment;
import com.pakpobox.cleanpro.ui.home.HomeFragment;
import com.pakpobox.cleanpro.ui.logon.setpsw.SetPSWContract;
import com.pakpobox.cleanpro.ui.logon.setpsw.SetPSWPresenter;
import com.pakpobox.cleanpro.utils.StatusBarUtil;
import com.timmy.tdialog.TDialog;
import com.timmy.tdialog.base.BindViewHolder;
import com.timmy.tdialog.listener.OnBindViewListener;
import com.timmy.tdialog.listener.OnViewClickListener;
import com.tuo.customview.VerificationCodeView;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.pakpobox.cleanpro.common.Const.CLEAN_TYPE.DRYER;
import static com.pakpobox.cleanpro.common.Const.CLEAN_TYPE.LAUNDRY;

/**
 * 创建订单
 */
public class CreateOrderFragment extends BasePresenterFragment<CreateOrderPresenter, CreateOrderContract.ICreateOrderView> implements CreateOrderContract.ICreateOrderView {

    @BindView(R.id.toolbar_title_tv)
    TextView mTitleTv;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private int mType = LAUNDRY;

    public static CreateOrderFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt("type", type);
        CreateOrderFragment fragment = new CreateOrderFragment();
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

        switch (mType) {
            case LAUNDRY:
                mTitleTv.setText(getString(R.string.home_laundry));
                break;
            case DRYER:
                mTitleTv.setText(getString(R.string.home_dryer));
                break;
        }
    }

    @Override
    public CreateOrderRequest getCreateOrderParam() {
        CreateOrderRequest createOrderRequest = new CreateOrderRequest();
        JSONObject goodsObj = new JSONObject();
        switch (mType) {
            case LAUNDRY:
                createOrderRequest.setOrder_type("LAUNDRY");
                try {
                    goodsObj.put("temperature", "Warm");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case DRYER:
                createOrderRequest.setOrder_type("DRYER");
                try {
                    goodsObj.put("time", "30m");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
        createOrderRequest.setMachine_no("P2018070401");
        createOrderRequest.setClient_type("ANDROID");
        createOrderRequest.setTotal_amount(3);
        createOrderRequest.setGoods_info(goodsObj.toString());
        createOrderRequest.setLocation("Bandar Kinrara");
        createOrderRequest.setClient_version("1.0.4_dev");
        return createOrderRequest;
    }

    @Override
    public void getSuccess(Order data) {
        start(BookSuccessFragment.newInstance(mType));
    }

    @Override
    protected CreateOrderPresenter createPresenter() {
        return new CreateOrderPresenter(getActivity());
    }

    @OnClick(R.id.create_order_pay_btn)
    public void onClick() {

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
    }
}