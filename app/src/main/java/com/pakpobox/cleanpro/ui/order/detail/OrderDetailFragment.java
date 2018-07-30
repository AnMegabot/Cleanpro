package com.pakpobox.cleanpro.ui.order.detail;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.base.BaseFragment;
import com.pakpobox.cleanpro.bean.Order;
import com.pakpobox.cleanpro.utils.StatusBarUtil;
import com.pakpobox.cleanpro.utils.SystemUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 订单详情
 */
public class OrderDetailFragment extends BaseFragment {

    @BindView(R.id.toolbar_title_tv)
    TextView mTitleTv;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.order_detail_order_no_tv)
    TextView mOrderNoTv;
    @BindView(R.id.order_detail_location_tv)
    TextView mLocationTv;
    @BindView(R.id.order_detail_machine_no_tv)
    TextView mMachineNoTv;
    @BindView(R.id.order_detail_goods_info_title_tv)
    TextView mGoodsInfoTitleTv;
    @BindView(R.id.order_detail_temperature_tv)
    TextView mGoodsInfoTv;
    @BindView(R.id.order_detail_time_tv)
    TextView mTimeTv;
    @BindView(R.id.order_detail_amount_tv)
    TextView mAmountTv;

    private Order mOrder = null;

    public static OrderDetailFragment newInstance(Order order) {
        Bundle args = new Bundle();
        args.putParcelable("order", order);
        OrderDetailFragment fragment = new OrderDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (null != bundle) {
            mOrder = bundle.getParcelable("order");
        }
    }

    @Override
    protected void initViews(View view) {
        StatusBarUtil.setHeight(getContext(), mToolbar);
        mTitleTv.setText(getString(R.string.orders));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pop();
            }
        });

        if (null != mOrder) {
            mOrderNoTv.setText(mOrder.getOrder_no());
            mLocationTv.setText(mOrder.getLocation());
            mMachineNoTv.setText(mOrder.getMachine_no());
            if ("LAUNDRY".equals(mOrder.getOrder_type())) {
                mGoodsInfoTitleTv.setText(getString(R.string.orders_Temperature));
                try {
                    JSONObject laundryObj = new JSONObject(mOrder.getGoods_info());
                    mGoodsInfoTv.setText(laundryObj.getString("temperature"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                mGoodsInfoTitleTv.setText(getString(R.string.orders_Duration));
                try {
                    JSONObject laundryObj = new JSONObject(mOrder.getGoods_info());
                    mGoodsInfoTv.setText(laundryObj.getString("time"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            Date date = new Date();
            date.setTime(mOrder.getCreate_time());
            String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date);
            mTimeTv.setText(timeStamp);
            mAmountTv.setText(String.format(getString(R.string.orders_amount_format), SystemUtils.formatFloat2Str(mOrder.getTotal_amount())));
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order_detail;
    }

}