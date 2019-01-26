package com.pakpobox.cleanpro.ui.account.paypsw;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.application.AppSetting;
import com.pakpobox.cleanpro.base.BasePresenterFragment;
import com.pakpobox.cleanpro.bean.UserBean;
import com.pakpobox.cleanpro.ui.wallet.WalletFragment;
import com.pakpobox.cleanpro.utils.InputUtils;
import com.pakpobox.cleanpro.utils.KeyBoardHelper;
import com.pakpobox.cleanpro.utils.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * User:Sean.Wei
 * Date:2019/1/23
 * Time:18:58
 */

public class CheckOldPaymentPswFragment extends BasePresenterFragment<SetPaymentPswPresenter, PaymentPswContract.IPaymentPswView> implements PaymentPswContract.IPaymentPswView {

    @BindView(R.id.app_toolbar_title_tv)
    TextView mTitleTv;
    @BindView(R.id.app_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.change_psw_title_tv)
    TextView mPswTitleTv;
    @BindView(R.id.change_psw_et)
    EditText mPswEt;
    @BindView(R.id.change_psw_complete_btn)
    Button mCompleteBtn;
    @BindView(R.id.change_psw_scrollview)
    ScrollView mScrollview;
    @BindView(R.id.change_psw_forget_btn)
    TextView mForgetBtn;

    private KeyBoardHelper keyBoardHelper;

    public static CheckOldPaymentPswFragment newInstance() {
        Bundle args = new Bundle();
        CheckOldPaymentPswFragment fragment = new CheckOldPaymentPswFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initViews(View view) {
        StatusBarUtil.setHeight(getContext(), mToolbar);
        mTitleTv.setText(getString(R.string.wallet_payment_setting));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pop();
            }
        });

        keyBoardHelper = new KeyBoardHelper(getActivity());
        keyBoardHelper.setKeyboardListener(mScrollview, null);

        mPswTitleTv.setText(getString(R.string.wallet_payment_check_title));
        mCompleteBtn.setText(getString(R.string.app_next));

        UserBean userBean = AppSetting.getUserInfo();
        if (null != userBean && !TextUtils.isEmpty(userBean.getPayPassword()))
            mForgetBtn.setVisibility(View.VISIBLE);

        InputUtils.setEditFilter(mPswEt, new InputFilter.LengthFilter(6));
        mPswEt.setKeyListener(DigitsKeyListener.getInstance("0123456789"));
        mPswEt.setInputType(InputType.TYPE_CLASS_NUMBER);
        mPswEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mCompleteBtn.setEnabled(verifyPsw());
            }
        });

        mCompleteBtn.setEnabled(verifyPsw());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_change_psw;
    }

    private boolean verifyPsw() {
        if (!TextUtils.isEmpty(mPswEt.getText().toString().trim()))
            return true;
        return false;
    }

    @Override
    public void checkPayPswSuccess() {
        String oldPsw = mPswEt.getText().toString().trim();
        if (!TextUtils.isEmpty(oldPsw))
            start(SetPaymentPswFragment.newInstance(null, null, oldPsw));
    }

    @Override
    public void setPayPswSuccess(UserBean userBean) {

    }

    @Override
    protected SetPaymentPswPresenter createPresenter() {
        return new SetPaymentPswPresenter(getActivity());
    }

    @OnClick(R.id.change_psw_complete_btn)
    public void onClick() {

    }

    @OnClick({R.id.change_psw_forget_btn, R.id.change_psw_complete_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.change_psw_forget_btn:
                start(ForgetPaymentPswFragment.newInstance());
                break;
            case R.id.change_psw_complete_btn:
                mPresenter.checkPayPsw(mPswEt.getText().toString().trim());
                break;
        }
    }
}
