package com.pakpobox.cleanpro.base.list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.pakpobox.cleanpro.ui.widget.LMRecyclerView;

import java.util.List;

/**
 * author: 康栋普
 * date: 2018/2/12
 */

public abstract class BaseListAdapter<T> extends RecyclerView.Adapter<ListDataHolder> {
    protected Context mContext;
    private List<T> mList;

    public BaseListAdapter(Context context) {
        mContext = context;
    }

    //刷新所有数据
    public void notifyAllDatas(List<T> mList, RecyclerView recyclerView) {
        this.mList = mList;
        if (recyclerView instanceof LMRecyclerView)
            ((LMRecyclerView)recyclerView).notifyDataSetChanged();
        else
            notifyDataSetChanged();
    }

    /**
     * 获取数据列表
     * @return List<T>
     */
    public List<T> getList() {
        return mList;
    }

    //刷新单条数据
    public void notifyItemDataChanged(int position, RecyclerView recyclerView) {
//        if (null != mList && position <= mList.size())
//            mList.set(position, data);
        if (recyclerView instanceof LMRecyclerView)
            ((LMRecyclerView)recyclerView).notifyItemChanged(position);
        else
            notifyItemChanged(position);
    }

    //移除单条数据
    public void notifyItemDataRemove(int position, RecyclerView recyclerView) {
//        if (null != mList && position < mList.size())
//            mList.remove(position);
        if (recyclerView instanceof LMRecyclerView) {
            ((LMRecyclerView) recyclerView).notifyItemRemoved(position);
        } else {
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, getItemCount());
        }
    }


    @Override
    public ListDataHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ListDataHolder listDataHolder = ListDataHolder.createViewHolder(parent, getLayoutId(viewType));
        return listDataHolder;
    }

    protected abstract int getLayoutId(int viewType);


    @Override
    public void onBindViewHolder(ListDataHolder holder, int position) {
        //初始化View
        T bean = mList.get(position);
        //绑定数据
        bindDatas(holder, bean, holder.getItemViewType(), position);
    }


    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    public abstract void bindDatas(ListDataHolder holder, T bean, int itemType, int position);

}
