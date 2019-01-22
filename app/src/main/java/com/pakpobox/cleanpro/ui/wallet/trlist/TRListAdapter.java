package com.pakpobox.cleanpro.ui.wallet.trlist;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.application.MyApplication;
import com.pakpobox.cleanpro.base.list.BaseListAdapter;
import com.pakpobox.cleanpro.base.list.ListDataHolder;
import com.pakpobox.cleanpro.bean.TradingRecort;
import com.pakpobox.cleanpro.utils.SystemUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * User:Sean.Wei
 * Date:2018/6/27
 * Time:12:14
 */

public class TRListAdapter extends BaseListAdapter<TradingRecort> {

    private OnItemClickListener listener;

    public TRListAdapter(Context context, OnItemClickListener listener) {
        super(context);
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

        typeTv.setText(bean.getTransactionType());

        Date date = new Date();
        date.setTime(bean.getCreateTime());
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date);
        timeTv.setText(timeStamp);
        amountTv.setTextColor("IN".equals(bean.getIncomeType()) ? mContext.getResources().getColor(R.color.colorPrimary) : mContext.getResources().getColor(R.color.textColorEditHint));
        amountTv.setText(("IN".equals(bean.getIncomeType()) ? "+" : "-") + SystemUtils.formatFloat2Str(bean.getAmount()/100.0));

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
