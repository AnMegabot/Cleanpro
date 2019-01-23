package com.pakpobox.cleanpro.ui.my.personal;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;

import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.application.AppSetting;
import com.pakpobox.cleanpro.base.BasePresenterFragment;
import com.pakpobox.cleanpro.bean.UserBean;
import com.pakpobox.cleanpro.utils.KeyBoardHelper;
import com.pakpobox.cleanpro.utils.StatusBarUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpdatePostcodeFragment extends BasePresenterFragment<UpdatePostcodePresenter, PersonalInfoContract.IUpdatePostcodeInfoView> implements PersonalInfoContract.IUpdatePostcodeInfoView {


    @BindView(R.id.app_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.register_postcode_et)
    EditText mPostcodeEt;
    @BindView(R.id.register_postcode_next_btn)
    Button mNextBtn;
    @BindView(R.id.register_content_llt)
    ScrollView mContentLlt;

    private KeyBoardHelper keyBoardHelper;

    public static UpdatePostcodeFragment newInstance() {
        Bundle args = new Bundle();
        UpdatePostcodeFragment fragment = new UpdatePostcodeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected UpdatePostcodePresenter createPresenter() {
        return new UpdatePostcodePresenter(getActivity());
    }

    @Override
    public void updatePostcodeSuccess(UserBean userBean) {
        AppSetting.saveUserInfo(userBean);
        EventBus.getDefault().post(userBean);
        pop();
    }

    @Override
    protected void initViews(View view) {
        StatusBarUtil.setHeight(getContext(), mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pop();
            }
        });

        keyBoardHelper = new KeyBoardHelper(getActivity());
        keyBoardHelper.setKeyboardListener(mContentLlt, null);

        //监听输入框内容变化
        mPostcodeEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mNextBtn.setEnabled(verifyPostcode());
            }
        });
        mNextBtn.setEnabled(verifyPostcode());
    }

    private boolean verifyPostcode() {
        if (!TextUtils.isEmpty(mPostcodeEt.getText().toString().trim()))
            return true;
        return false;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_register_postcode;
    }

    @OnClick(R.id.register_postcode_next_btn)
    public void onClick() {
        mPresenter.updatePostcode(mPostcodeEt.getText().toString().trim());
    }
}
