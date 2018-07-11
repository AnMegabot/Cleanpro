package com.pakpobox.cleanpro.ui.widget.tabbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pakpobox.cleanpro.R;

import java.util.Locale;


/**
 * @author ChayChan
 * @description: 底部tab条目
 * @date 2017/6/23  9:14
 */

public class TabBarItem extends RelativeLayout {

    private Context mContext;
    private Drawable mTextNormalBg;//普通状态图标的资源id
    private Drawable mTextSelectedBg;//选中状态图标的资源id
    private String mText;//文本
    private int mTextSize = 12;//文字大小 默认为12sp
    private int mTextColorNormal = 0xFF999999;    //描述文本的默认显示颜色
    private int mTextColorSelected = 0xFF46C01B;  //述文本的默认选中显示颜色
    private boolean mOpenTouchBg = false;// 是否开启触摸背景，默认关闭
    private Drawable mTouchDrawable;//触摸时的背景
    private int mItemPadding;//BottomBarItem的padding

    private TextView mTvUnread;
    private TextView mTextView;

    private int mUnreadTextSize = 10; //未读数默认字体大小10sp
    private int unreadNumThreshold = 99;//未读数阈值
    private int mUnreadTextColor;//未读数字体颜色
    private Drawable mUnreadTextBg;

    public TabBarItem(Context context) {
        this(context, null);
    }

    public TabBarItem(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabBarItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mContext = context;

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TabBarItem);

        initAttrs(ta); //初始化属性

        ta.recycle();

        checkValues();//检查值是否合法

        init();//初始化相关操作
    }

    private void initAttrs(TypedArray ta) {
        mTextNormalBg = ta.getDrawable(R.styleable.TabBarItem_textNormalBg);
        mTextSelectedBg = ta.getDrawable(R.styleable.TabBarItem_textSelectedBg);

        mText = ta.getString(R.styleable.TabBarItem_itemText);
        mTextSize = ta.getDimensionPixelSize(R.styleable.TabBarItem_itemTextSize, UIUtils.sp2px(mContext, mTextSize));

        mTextColorNormal = ta.getColor(R.styleable.TabBarItem_textColorNormal, mTextColorNormal);
        mTextColorSelected = ta.getColor(R.styleable.TabBarItem_textColorSelected, mTextColorSelected);

        mOpenTouchBg = ta.getBoolean(R.styleable.TabBarItem_openTouchBg, mOpenTouchBg);
        mTouchDrawable = ta.getDrawable(R.styleable.TabBarItem_touchDrawable);

        mItemPadding = ta.getDimensionPixelSize(R.styleable.TabBarItem_itemPadding, 0);

        mUnreadTextSize = ta.getDimensionPixelSize(R.styleable.TabBarItem_unreadTextSize, UIUtils.sp2px(mContext, mUnreadTextSize));
        mUnreadTextColor = ta.getColor(R.styleable.TabBarItem_unreadTextColor, 0xFFFFFFFF);
        mUnreadTextBg = ta.getDrawable(R.styleable.TabBarItem_unreadTextBg);

        unreadNumThreshold = ta.getInteger(R.styleable.TabBarItem_unreadThreshold,99);
    }

    /**
     * 检查传入的值是否完善
     */
    private void checkValues() {
        if (mTextNormalBg == null) {
            throw new IllegalStateException("您还没有设置默认状态下的背景");
        }

        if (mTextSelectedBg == null) {
            throw new IllegalStateException("您还没有设置选中状态下的背景");
        }

        if (mOpenTouchBg && mTouchDrawable == null) {
            //如果有开启触摸背景效果但是没有传对应的drawable
            throw new IllegalStateException("开启了触摸效果，但是没有指定touchDrawable");
        }

        if (mUnreadTextBg == null){
            mUnreadTextBg = getResources().getDrawable(R.drawable.shape_unread);
        }
    }

    private void init() {
        setGravity(Gravity.CENTER);
        View view = initView();

        mTextView.setBackground(mTextNormalBg);

        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);//设置底部文字字体大小

        mTvUnread.setTextSize(TypedValue.COMPLEX_UNIT_PX, mUnreadTextSize);//设置未读数的字体大小
        mTvUnread.setTextColor(mUnreadTextColor);//设置未读数字体颜色
        mTvUnread.setBackground(mUnreadTextBg);//设置未读数背景

        mTextView.setTextColor(mTextColorNormal);//设置底部文字字体颜色
        mTextView.setText(mText);//设置标签文字

        if (mOpenTouchBg) {
            //如果有开启触摸背景
            setBackground(mTouchDrawable);
        }

        addView(view);
    }

    @NonNull
    private View initView() {
        View view = View.inflate(mContext, R.layout.item_bottom_bar, null);
        if (mItemPadding != 0) {
            //如果有设置item的padding
            view.setPadding(mItemPadding, mItemPadding, mItemPadding, mItemPadding);
        }
        mTvUnread = (TextView) view.findViewById(R.id.tabbar_tv_unred_num);
        mTextView = (TextView) view.findViewById(R.id.tabbar_tv_text);
        return view;
    }

    public TextView getTextView() {
        return mTextView;
    }

    public void setTextNormalBg(Drawable mTextNormalBg) {
        this.mTextNormalBg = mTextNormalBg;
    }

    public void setTextSelectedBg(Drawable mTextSelectedBg) {
        this.mTextSelectedBg = mTextSelectedBg;
    }

    public void setStatus(boolean isSelected) {
        mTextView.setTextColor(isSelected ? mTextColorSelected : mTextColorNormal);
        mTextView.setBackground(isSelected ? mTextSelectedBg : mTextNormalBg);
    }

    private void setTvVisiable(TextView tv) {
        //都设置为不可见
        mTvUnread.setVisibility(GONE);
        tv.setVisibility(VISIBLE);//设置为可见
    }

    public int getUnreadNumThreshold() {
        return unreadNumThreshold;
    }

    public void setUnreadNumThreshold(int unreadNumThreshold) {
        this.unreadNumThreshold = unreadNumThreshold;
    }

    /**
     * 设置未读数
     *
     * @param unreadNum 小于等于{@link TabBarItem#unreadNumThreshold}则隐藏，
     *                  大于0小于{@link TabBarItem#unreadNumThreshold}则显示对应数字，
     *                  超过{@link TabBarItem#unreadNumThreshold}
     *                  显示{@link TabBarItem#unreadNumThreshold}+
     */
    public void setUnreadNum(int unreadNum) {
        setTvVisiable(mTvUnread);
        if (unreadNum <= 0) {
            mTvUnread.setVisibility(GONE);
        } else if (unreadNum <= unreadNumThreshold) {
            mTvUnread.setText(String.valueOf(unreadNum));
        } else {
            mTvUnread.setText(String.format(Locale.CHINA, "%d+", unreadNumThreshold));
        }
    }
}
