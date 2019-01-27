package com.pakpobox.cleanpro.ui.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.pakpobox.cleanpro.R;

/**
 * 加载各种状态的布局(empty、loading、error、content)
 */

public class StatusLayout extends FrameLayout {

    private Context mContext;
    private LayoutInflater mInflater;
    private View mLoadingView;
    private View mErrorView;
    private View mEmptyView;
    private View mContentView;
    private View mCustomerView;


    public StatusLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        mLoadingView = mInflater.inflate(R.layout.status_loading_layout, this, false);
        mErrorView = mInflater.inflate(R.layout.status_error_layout, this, false);
        mEmptyView = mInflater.inflate(R.layout.status_empty_layout, this, false);
        addView(mLoadingView);
        addView(mErrorView);
        addView(mEmptyView);
        mLoadingView.setVisibility(GONE);
        mErrorView.setVisibility(GONE);
        mEmptyView.setVisibility(GONE);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            int count = getChildCount();
            for (int i = 0; i < count; i++) {
                mContentView = getChildAt(i);
                if (mContentView instanceof RecyclerView)
                    break;
            }
        }
    }

    //loading
    public void showLoding() {
        mLoadingView.setVisibility(VISIBLE);
        mErrorView.setVisibility(GONE);
        mEmptyView.setVisibility(GONE);
        if (mContentView != null)
            mContentView.setVisibility(GONE);
    }

    //error
    public void showError() {
        mErrorView.setVisibility(VISIBLE);
        mLoadingView.setVisibility(GONE);
        mEmptyView.setVisibility(GONE);
        if (mContentView != null)
            mContentView.setVisibility(GONE);
    }

    public void setError(View view) {
        if (null != view && null != mErrorView) {
            view.setVisibility(mErrorView.getVisibility());
            removeView(mErrorView);
            mErrorView = view;
            addView(mErrorView);
        }
    }

    //empty
    public void setEmpty() {
        mEmptyView.setVisibility(VISIBLE);
        mLoadingView.setVisibility(GONE);
        mErrorView.setVisibility(GONE);
        if (mContentView != null)
            mContentView.setVisibility(GONE);
    }

    public void setEmpty(View view) {
        if (null != view && null != mEmptyView) {
            view.setVisibility(mEmptyView.getVisibility());
            removeView(mEmptyView);
            mEmptyView = view;
            addView(mEmptyView);
        }
    }

    //content
    public void showContent() {
        if (mContentView != null)
            mContentView.setVisibility(VISIBLE);
        mLoadingView.setVisibility(GONE);
        mErrorView.setVisibility(GONE);
        mEmptyView.setVisibility(GONE);
    }

    public void setOnErrorClickListener(OnClickListener listener) {
        mErrorView.setOnClickListener(listener);
    }

    public void setErrorText(String text) {
        ((TextView)mErrorView.findViewById(R.id.studio_error_tv)).setText(text);
    }

    public void setEmptyText(String text) {
        ((TextView)mEmptyView.findViewById(R.id.studio_empty_tv)).setText(text);
    }
}
