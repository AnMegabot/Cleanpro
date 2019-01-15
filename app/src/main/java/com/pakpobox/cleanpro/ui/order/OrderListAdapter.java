package com.pakpobox.cleanpro.ui.order;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.application.MyApplication;
import com.pakpobox.cleanpro.base.list.BaseListAdapter;
import com.pakpobox.cleanpro.base.list.ListDataHolder;
import com.pakpobox.cleanpro.bean.Order;
import com.pakpobox.cleanpro.bean.price.Price;
import com.pakpobox.cleanpro.utils.SystemUtils;


public class OrderListAdapter extends BaseListAdapter<Order> {

    private OnItemClickListener listener;

    public OrderListAdapter(Context context, OnItemClickListener listener) {
        super(context);
        this.listener = listener;
    }

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.item_order_list;
    }

    @Override
    public void bindDatas(ListDataHolder holder, final  Order bean, int itemType, int position) {
        ImageView typeIm = holder.getView(R.id.item_order_type_icon);
        TextView typeTv = holder.getView(R.id.item_order_type_tv);
        TextView orderNoTv = holder.getView(R.id.item_order_no_tv);
        TextView paymentStatusTv = holder.getView(R.id.item_order_payment_status_tv);
        TextView amountTv = holder.getView(R.id.item_order_amount_tv);

        typeTv.setText(bean.getOrder_type());
        orderNoTv.setText(String.format(MyApplication.getContext().getString(R.string.orders_no_format), bean.getOrder_no()));
        paymentStatusTv.setText(bean.getPay_status());
        paymentStatusTv.setTextColor("UNPAID".equals(bean.getPay_status()) ? mContext.getResources().getColor(R.color.orderUnpaidTextColor) : mContext.getResources().getColor(R.color.orderPaidTextColor));
        amountTv.setText(String.format(mContext.getString(R.string.orders_amount_format), SystemUtils.formatFloat2Str(bean.getTotal_amount())));

        typeIm.setImageResource("LAUNDRY".equals(bean.getOrder_type()) ? R.mipmap.icon_laundry_1 : R.mipmap.icon_dryer_1);
//        Glide.with(mContext).load(context.getResources().getDrawable((int)path)).into(typeIm);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(bean);
                }
            }
        });
    }

    public interface OnItemClickListener {
        void onItemClick(Order order);
    }
}
