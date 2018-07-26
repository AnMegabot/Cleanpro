package com.pakpobox.cleanpro.ui.logon.changepsw;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;

import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.base.BaseFragment;
import com.pakpobox.cleanpro.base.BasePresenterFragment;
import com.pakpobox.cleanpro.ui.logon.login.LoginFragment;
import com.pakpobox.cleanpro.utils.KeyBoardHelper;
import com.pakpobox.cleanpro.utils.StatusBarUtil;
import com.pakpobox.cleanpro.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 修改登录密码
 */
public class ChangePSWFragment extends BasePresenterFragment<ChangePSWPresenter, ChangePSWContract.IChangePSWView> implements ChangePSWContract.IChangePSWView {

    @BindView(R.id.change_psw_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.change_psw_et)
    EditText mPswEt;
    @BindView(R.id.change_psw_complete_btn)
    Button mCompleteBtn;
    @BindView(R.id.change_psw_scrollview)
    ScrollView mContentLlt;
    private KeyBoardHelper keyBoardHelper;

    private String mToken;
    public static ChangePSWFragment newInstance(String token) {
        Bundle args = new Bundle();
        args.putString("token", token);
        ChangePSWFragment fragment = new ChangePSWFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_change_psw;
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

        mPswEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mCompleteBtn.setEnabled(mPresenter.verifyPassword());
            }
        });

        mCompleteBtn.setEnabled(mPresenter.verifyPassword());    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (null != bundle) {
            mToken = bundle.getString("token");
        }
    }

    @Override
    public String getPassWord() {
        return mPswEt.getText().toString().trim();
    }

    @Override
    public String getToken() {
        return mToken;
    }

    @Override
    public void changeSuccess() {
        popTo(LoginFragment.class, false);
        ToastUtils.showToast(getContext(), R.string.login_reset_psw_success_warn);
    }

    @Override
    protected ChangePSWPresenter createPresenter() {
        return new ChangePSWPresenter(getActivity());
    }

    @OnClick(R.id.change_psw_complete_btn)
    public void onClick() {
        mPresenter.changePSW();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        keyBoardHelper.removeKeyboardListener();
    }
}
