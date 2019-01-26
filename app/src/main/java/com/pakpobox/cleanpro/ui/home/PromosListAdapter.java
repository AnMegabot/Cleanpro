package com.pakpobox.cleanpro.ui.home;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.base.list.BaseListAdapter;
import com.pakpobox.cleanpro.base.list.ListDataHolder;
import com.pakpobox.cleanpro.bean.Promos;
import com.pakpobox.cleanpro.bean.location.Site;


public class PromosListAdapter extends BaseListAdapter<Promos> {

    private OnItemClickListener listener;

    public PromosListAdapter(Context context, OnItemClickListener listener) {
        super(context);
        this.listener = listener;
    }

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.item_promos_list;
    }

    @Override
    public void bindDatas(ListDataHolder holder, final  Promos bean, int itemType, int position) {
        ImageView typeIm = holder.getView(R.id.item_promos_im);

//        typeIm.setImageResource(R.mipmap.collections_1);
        Glide.with(mContext).load(mContext.getResources().getIdentifier(bean.getUrl(), "mipmap", mContext.getPackageName())).into(typeIm);

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
        void onItemClick(Promos order);
    }
}
