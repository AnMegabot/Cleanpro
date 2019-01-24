package com.pakpobox.cleanpro.ui.account.paypsw;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.base.BasePresenterFragment;
import com.pakpobox.cleanpro.utils.InputUtils;
import com.pakpobox.cleanpro.utils.KeyBoardHelper;
import com.pakpobox.cleanpro.utils.StatusBarUtil;
import com.pakpobox.cleanpro.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * User:Sean.Wei
 * Date:2019/1/23
 * Time:19:21
 */

public class ConfirmPaymentPswFragment extends BasePresenterFragment<SetPaymentPswPresenter, PaymentPswContract.IPaymentPswView> implements PaymentPswContract.IPaymentPswView {

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

    private KeyBoardHelper keyBoardHelper;

    private String token;
    private String oldPsw;
    private String newPsw;

    public static ConfirmPaymentPswFragment newInstance(String token, String oldPsw, String newPsw) {
        Bundle args = new Bundle();
        args.putString("token", token);
        args.putString("oldPsw", oldPsw);
        args.putString("newPsw", newPsw);
        ConfirmPaymentPswFragment fragment = new ConfirmPaymentPswFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (null != bundle) {
            token = bundle.getParcelable("token");
            oldPsw = bundle.getParcelable("oldPsw");
            newPsw = bundle.getParcelable("newPsw");
        }
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

        mPswTitleTv.setText(getString(R.string.wallet_payment_confirm_title));
        mCompleteBtn.setText(getString(R.string.app_complete));
        InputUtils.setEditFilter(mPswEt, new InputFilter.LengthFilter(6));
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

    }

    @Override
    public void resetPayPswSuccess(String result) {

    }

    @Override
    public void changePayPswSuccess(String result) {

    }

    @Override
    protected SetPaymentPswPresenter createPresenter() {
        return new SetPaymentPswPresenter(getActivity());
    }

    @OnClick(R.id.change_psw_complete_btn)
    public void onClick() {
        String confirmPsw = mPswEt.getText().toString().trim();
        if (!confirmPsw.equals(newPsw)) {
            ToastUtils.showToast(getContext(), R.string.register_set_psw_inconsistent_error);
            return;
        }

        if (!TextUtils.isEmpty(token))
            mPresenter.resetPayPsw(token, confirmPsw);
        else
            mPresenter.changePayPsw(oldPsw, confirmPsw);
    }
}
