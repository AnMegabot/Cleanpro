package com.pakpobox.cleanpro.ui.login;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.base.BaseFragment;
import com.pakpobox.cleanpro.model.AppSetting;
import com.pakpobox.cleanpro.utils.KeyBoardHelper;
import com.pakpobox.cleanpro.utils.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 登录
 */
public class LoginFragment extends BaseFragment {

    @BindView(R.id.login_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.login_mobile_et)
    EditText mMobileEt;
    @BindView(R.id.login_password_et)
    EditText mPasswordEt;
    @BindView(R.id.login_sign_in_btn)
    Button mSignInBtn;
    @BindView(R.id.login_scrollview)
    LinearLayout mScrollview;

    private KeyBoardHelper keyBoardHelper;
    private int bottomHeight;

    public static LoginFragment newInstance() {
        Bundle args = new Bundle();
        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    protected void initViews(View view) {
        StatusBarUtil.setHeight(getContext(), mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        keyBoardHelper = new KeyBoardHelper(getActivity());
        keyBoardHelper.onCreate();
        keyBoardHelper.setOnKeyBoardStatusChangeListener(new KeyBoardHelper.OnKeyBoardStatusChangeListener() {
            @Override
            public void OnKeyBoardPop(int keyBoardheight) {
                DisplayMetrics dm = getResources().getDisplayMetrics();
                final int screenHeight = dm.heightPixels;
                final int height = keyBoardheight;
                int[] array = new int[2];
                mSignInBtn.getLocationOnScreen(array);
                int bottom = mSignInBtn.getBottom();
//                int offset = array[1] - height;
                int offset = (screenHeight - (array[1] + bottomHeight)) - height;
                final ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) mScrollview.getLayoutParams();
                lp.topMargin = offset;
                mScrollview.setLayoutParams(lp);
            }

            @Override
            public void OnKeyBoardClose(int oldKeyBoardheight) {
                final ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) mScrollview.getLayoutParams();
                if (lp.topMargin != 0) {
                    lp.topMargin = 0;
                    mScrollview.setLayoutParams(lp);
                }
            }
        });
        mSignInBtn.post(new Runnable() {
            @Override
            public void run() {
                bottomHeight = mSignInBtn.getHeight() + 16;
            }
        });
    }

    @OnClick({R.id.login_register_btn, R.id.login_sign_in_btn, R.id.login_forget_password_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_register_btn:
                start(RegisterFragment.newInstance());
                break;
            case R.id.login_sign_in_btn:
                AppSetting.getInstance().setHasLogin(true);
                Intent intent = getActivity().getIntent();
                getActivity().setResult(RESULT_OK, intent);
                getActivity().finish();
                break;
            case R.id.login_forget_password_btn:
                start(ForgetPSWFragment.newInstance());
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        keyBoardHelper.onDestory();
    }
}
