package com.pakpobox.cleanpro.ui.booking;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.base.BaseFragment;
import com.pakpobox.cleanpro.ui.home.HomeFragment;
import com.pakpobox.cleanpro.ui.widget.RadioGroupPro;
import com.pakpobox.cleanpro.utils.StatusBarUtil;
import com.pakpobox.cleanpro.utils.ToastUtils;
import com.timmy.tdialog.TDialog;
import com.timmy.tdialog.base.BindViewHolder;
import com.timmy.tdialog.listener.OnBindViewListener;
import com.timmy.tdialog.listener.OnViewClickListener;
import com.tuo.customview.VerificationCodeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 创建订单
 */
public class CreateOrderFragment extends BaseFragment {


    @BindView(R.id.toolbar_title_tv)
    TextView mTitleTv;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private int mType = HomeFragment.LAUNDRY_SCAN_REQUEST_CODE;

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
            case HomeFragment.LAUNDRY_SCAN_REQUEST_CODE:
                mTitleTv.setText(getString(R.string.home_laundry));
                break;
            case HomeFragment.DRYER_SCAN_REQUEST_CODE:
                mTitleTv.setText(getString(R.string.home_dryer));
                break;
        }
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
                                    start(BookSuccessFragment.newInstance(mType));
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