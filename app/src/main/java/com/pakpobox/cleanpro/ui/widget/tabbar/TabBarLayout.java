package com.pakpobox.cleanpro.ui.widget.tabbar;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ChayChan
 * @description: 底部页签根节点
 * @date 2017/6/23  11:02
 */
public class TabBarLayout extends ConstraintLayout {

    private static final String STATE_INSTANCE = "instance_state";
    private static final String STATE_ITEM = "state_item";

    private int mChildCount;//子条目个数
    private List<TabBarItem> mItemViews = new ArrayList<>();
    private int mCurrentItem;//当前条目的索引

    public TabBarLayout(Context context) {
        this(context, null);
    }

    public TabBarLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabBarLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        init();
    }

    private void init() {
        mChildCount = getChildCount();

        for (int i = 0; i < mChildCount; i++) {
            if (getChildAt(i) instanceof TabBarItem) {
                TabBarItem bottomBarItem = (TabBarItem) getChildAt(i);
                mItemViews.add(bottomBarItem);
                //设置点击监听
                bottomBarItem.setOnClickListener(new MyOnClickListener(i));
            } else {
                throw new IllegalArgumentException("BottomBarLayout的子View必须是BottomBarItem");
            }
        }

        mItemViews.get(mCurrentItem).setStatus(true);//设置选中项
    }

    private class MyOnClickListener implements OnClickListener {

        private int currentIndex;

        public MyOnClickListener(int i) {
            this.currentIndex = i;
        }

        @Override
        public void onClick(View v) {
            if (onItemSelectedListener != null) {
                onItemSelectedListener.onItemSelected(getBottomItem(currentIndex),mCurrentItem,currentIndex);
            }
            updateTabState(currentIndex);
        }
    }

    private void updateTabState(int position){
        resetState();
        mCurrentItem = position;
        mItemViews.get(mCurrentItem).setStatus(true);
    }

    /**
     * 重置当前按钮的状态
     */
    private void resetState() {
        if (mCurrentItem < mItemViews.size()){
            mItemViews.get(mCurrentItem).setStatus(false);
        }
    }

    public void setCurrentItem(int currentItem) {
        updateTabState(currentItem);
    }

    /**
     * 设置未读数
     * @param position 底部标签的下标
     * @param unreadNum 未读数
     */
    public void setUnread(int position,int unreadNum){
        mItemViews.get(position).setUnreadNum(unreadNum);
    }

    public int getCurrentItem() {
        return mCurrentItem;
    }

    public TabBarItem getBottomItem(int position){
        return mItemViews.get(position);
    }

    /**
     * @return 当View被销毁的时候，保存数据
     */
    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(STATE_INSTANCE, super.onSaveInstanceState());
        bundle.putInt(STATE_ITEM, mCurrentItem);
        return bundle;
    }

    /**
     * @param state 用于恢复数据使用
     */
    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            mCurrentItem = bundle.getInt(STATE_ITEM);
            //重置所有按钮状态
            resetState();
            //恢复点击的条目颜色
            mItemViews.get(mCurrentItem).setStatus(true);
            super.onRestoreInstanceState(bundle.getParcelable(STATE_INSTANCE));
        } else {
            super.onRestoreInstanceState(state);
        }
    }

    private OnItemSelectedListener onItemSelectedListener;

    public interface OnItemSelectedListener {
        void onItemSelected(TabBarItem bottomBarItem, int previousPosition, int currentPosition);
    }

    public void setOnItemSelectedListener(OnItemSelectedListener onItemSelectedListener) {
        this.onItemSelectedListener = onItemSelectedListener;
    }
}
