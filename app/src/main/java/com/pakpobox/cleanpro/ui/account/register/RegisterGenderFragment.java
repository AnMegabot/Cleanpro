package com.pakpobox.cleanpro.ui.account.register;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioGroup;

import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.base.BaseFragment;
import com.pakpobox.cleanpro.bean.Register;
import com.pakpobox.cleanpro.common.Const;
import com.pakpobox.cleanpro.utils.StatusBarUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 注册-4
 */
public class RegisterGenderFragment extends BaseFragment {


    @BindView(R.id.app_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.register_gender_rg)
    RadioGroup mGenderRg;
    private Register mRegister;

    public static RegisterGenderFragment newInstance(Register register) {
        Bundle args = new Bundle();
        args.putParcelable("register", register);
        RegisterGenderFragment fragment = new RegisterGenderFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (null != bundle) {
            mRegister = bundle.getParcelable("register");
        }
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

        if (null == mRegister)
            return;
        mRegister.setGender(Const.GENDER.FEMALE.name());
        mGenderRg.check(R.id.register_gender_female_rb);
        mGenderRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.register_gender_male_rb:
                        mRegister.setGender(Const.GENDER.MALE.name());
                        break;
                    case R.id.register_gender_female_rb:
                        mRegister.setGender(Const.GENDER.FEMALE.name());
                        break;
                }
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_register_genter;
    }

    @OnClick(R.id.register_gender_next_btn)
    public void onClick() {
        start(RegisterPostcodeFragment.newInstance(mRegister));
    }

}
