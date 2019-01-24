package com.pakpobox.cleanpro.ui.account.paypsw;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.base.BaseFragment;
import com.pakpobox.cleanpro.base.BasePresenterFragment;
import com.pakpobox.cleanpro.ui.setting.SettingFragment;
import com.pakpobox.cleanpro.utils.InputUtils;
import com.pakpobox.cleanpro.utils.KeyBoardHelper;
import com.pakpobox.cleanpro.utils.StatusBarUtil;
import com.pakpobox.cleanpro.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 设置支付密码
 */
public class SetPaymentPswFragment extends BaseFragment {

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

    public static SetPaymentPswFragment newInstance(String token, String oldPsw) {
        Bundle args = new Bundle();
        args.putString("token", token);
        args.putString("oldPsw", oldPsw);
        SetPaymentPswFragment fragment = new SetPaymentPswFragment();
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

        mPswTitleTv.setText(getString(R.string.wallet_payment_set_title));
        mCompleteBtn.setText(getString(R.string.app_next));
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

    @OnClick(R.id.change_psw_complete_btn)
    public void onClick() {
        String newPsw = mPswEt.getText().toString().trim();
        if ( 6 != newPsw.length()) {
            ToastUtils.showToast(getContext(), R.string.wallet_payment_set_title);
            return;
        }

        start(ConfirmPaymentPswFragment.newInstance(token, oldPsw, newPsw));
    }
}
