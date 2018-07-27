package com.pakpobox.cleanpro.ui.price;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.application.MyApplication;
import com.pakpobox.cleanpro.base.list.BaseListAdapter;
import com.pakpobox.cleanpro.base.list.ListDataHolder;
import com.pakpobox.cleanpro.bean.TradingRecort;
import com.pakpobox.cleanpro.bean.price.Price;

/**
 * User:Sean.Wei
 * Date:2018/6/27
 * Time:12:14
 */

public class PriceAdapter extends BaseListAdapter<Price> {

    private OnItemClickListener listener;

    public PriceAdapter(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.item_price_list;
    }

    @Override
    public void bindDatas(ListDataHolder holder, final  Price bean, int itemType, int position) {
        TextView titleTv = holder.getView(R.id.item_price_title_tv);
        LinearLayout contentLayout = holder.getView(R.id.item_price_content_layout);

        titleTv.setText(bean.getName_en());

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
        void onItemClick(Price order);
    }
}
