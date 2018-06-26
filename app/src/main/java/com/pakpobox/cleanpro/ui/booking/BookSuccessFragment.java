package com.pakpobox.cleanpro.ui.booking;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.base.BaseFragment;
import com.pakpobox.cleanpro.ui.home.HomeFragment;
import com.pakpobox.cleanpro.utils.StatusBarUtil;
import com.timmy.tdialog.TDialog;
import com.timmy.tdialog.base.BindViewHolder;
import com.timmy.tdialog.listener.OnBindViewListener;
import com.timmy.tdialog.listener.OnViewClickListener;
import com.tuo.customview.VerificationCodeView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookSuccessFragment extends BaseFragment {
    @BindView(R.id.toolbar_title_tv)
    TextView mTitleTv;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.book_success_im)
    ImageView imView;

    private int mType = HomeFragment.LAUNDRY_SCAN_REQUEST_CODE;

    public static BookSuccessFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt("type", type);
        BookSuccessFragment fragment = new BookSuccessFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (null != bundle) {
            mType = bundle.getInt("type");
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_book_success;
    }

    @Override
    protected void initViews(View view) {
        StatusBarUtil.setHeight(getContext(), mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popTo(SelectPreferenceFragment.class, true);
            }
        });

        switch (mType) {
            case HomeFragment.LAUNDRY_SCAN_REQUEST_CODE:
                mTitleTv.setText(getString(R.string.home_laundry));
                imView.setImageResource(R.mipmap.illustrator_laundry);
                break;
            case HomeFragment.DRYER_SCAN_REQUEST_CODE:
                mTitleTv.setText(getString(R.string.home_dryer));
                imView.setImageResource(R.mipmap.illustrator_dry);
                break;
        }
    }

    @OnClick(R.id.book_success_done_btn)
    public void onClick() {
        popTo(SelectPreferenceFragment.class, true);
    }

    @Override
    public boolean onBackPressedSupport() {
        popTo(SelectPreferenceFragment.class, true);
        return true;
    }
}
