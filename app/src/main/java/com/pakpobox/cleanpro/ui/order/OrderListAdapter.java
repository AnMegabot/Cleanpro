package com.pakpobox.cleanpro.ui.order;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.application.MyApplication;
import com.pakpobox.cleanpro.base.list.BaseListAdapter;
import com.pakpobox.cleanpro.base.list.ListDataHolder;
import com.pakpobox.cleanpro.bean.Order;


public class OrderListAdapter extends BaseListAdapter<Order> {

    private OnOrderItemClickListener listener;

    public OrderListAdapter(OnOrderItemClickListener listener) {
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

        typeTv.setText(bean.getType());
        orderNoTv.setText(bean.getOrderNo());
        paymentStatusTv.setText(bean.getPaymentStatus());
        paymentStatusTv.setTextColor(bean.getPaymentStatus()=="PAID" ? MyApplication.getContext().getResources().getColor(R.color.orderPaidTextColor) : MyApplication.getContext().getResources().getColor(R.color.orderUnpaidTextColor));
        amountTv.setText(bean.getTotalAmount());

        typeIm.setImageResource(bean.getType()=="Laundry" ? R.mipmap.icon_laundry_1 : R.mipmap.icon_dryer_1);
//        Glide.with(MyApplication.getContext()).load(context.getResources().getDrawable((int)path)).into(typeIm);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(bean);
                }
            }
        });
    }

}
