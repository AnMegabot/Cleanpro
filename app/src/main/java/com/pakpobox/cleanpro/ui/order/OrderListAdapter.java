package com.pakpobox.cleanpro.ui.order;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.application.MyApplication;
import com.pakpobox.cleanpro.base.list.BaseListAdapter;
import com.pakpobox.cleanpro.base.list.ListDataHolder;
import com.pakpobox.cleanpro.bean.Order;
import com.pakpobox.cleanpro.bean.price.Price;


public class OrderListAdapter extends BaseListAdapter<Order> {

    private OnItemClickListener listener;

    public OrderListAdapter(OnItemClickListener listener) {
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
        orderNoTv.setText(bean.getOrder_no());
        paymentStatusTv.setText("PAID");
        paymentStatusTv.setTextColor(MyApplication.getContext().getResources().getColor(R.color.orderPaidTextColor));
        amountTv.setText(bean.getTotal_amount() + "");

        typeIm.setImageResource(bean.getOrder_type()=="LAUNDRY" ? R.mipmap.icon_laundry_1 : R.mipmap.icon_dryer_1);
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

    public interface OnItemClickListener {
        void onItemClick(Order order);
    }
}
