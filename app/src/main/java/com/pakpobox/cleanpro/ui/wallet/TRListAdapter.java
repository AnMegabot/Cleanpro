package com.pakpobox.cleanpro.ui.wallet;

import android.view.View;
import android.widget.TextView;

import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.base.MyApplication;
import com.pakpobox.cleanpro.base.list.BaseListAdapter;
import com.pakpobox.cleanpro.base.list.ListDataHolder;
import com.pakpobox.cleanpro.bean.TradingRecort;

/**
 * User:Sean.Wei
 * Date:2018/6/27
 * Time:12:14
 */

public class TRListAdapter extends BaseListAdapter<TradingRecort> {

    private OnItemClickListener listener;

    public TRListAdapter(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.item_tr_list;
    }

    @Override
    public void bindDatas(ListDataHolder holder, final  TradingRecort bean, int itemType, int position) {
        TextView typeTv = holder.getView(R.id.item_tr_type_tv);
        TextView timeTv = holder.getView(R.id.item_tr_time_tv);
        TextView amountTv = holder.getView(R.id.item_tr_amount_tv);

        typeTv.setText(bean.getType());
        timeTv.setText(bean.getTime());
        amountTv.setTextColor(bean.getType().equals("Credit card") || bean.getType().equals("Scan") ? MyApplication.getContext().getResources().getColor(R.color.orderPaidTextColor) : MyApplication.getContext().getResources().getColor(R.color.textColorPrimaryDark));
        amountTv.setText(bean.getTotalAmount());

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
        void onItemClick(TradingRecort order);
    }
}
