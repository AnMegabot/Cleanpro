package com.pakpobox.cleanpro.ui.my.personal;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.view.BasePickerView;
import com.pakpobox.cleanpro.GlideApp;
import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.application.AppSetting;
import com.pakpobox.cleanpro.base.BasePresenterFragment;
import com.pakpobox.cleanpro.bean.UserBean;
import com.pakpobox.cleanpro.ui.widget.BottomMenuDialog;
import com.pakpobox.cleanpro.utils.StatusBarUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 个人信息
 */
public class PersonalInfoFragment extends BasePresenterFragment<PersonalInfoPresenter, PersonalInfoContract.IPersonalInfoView> implements PersonalInfoContract.IPersonalInfoView {


    @BindView(R.id.app_toolbar_title_tv)
    TextView mTitleTv;
    @BindView(R.id.app_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.personal_portrait_im)
    ImageView mPortraitIm;
    @BindView(R.id.personal_name_tv)
    TextView mNameTv;
    @BindView(R.id.personal_phone_number_tv)
    TextView mPhoneNumberTv;
    @BindView(R.id.personal_gender_tv)
    TextView mGenderTv;
    @BindView(R.id.personal_birthday_tv)
    TextView mBirthdayTv;
    @BindView(R.id.personal_postcode_tv)
    TextView mPostcodeTv;

    private Dialog mDialog;

    public static PersonalInfoFragment newInstance() {
        Bundle args = new Bundle();
        PersonalInfoFragment fragment = new PersonalInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_personal_info;
    }

    @Override
    protected PersonalInfoPresenter createPresenter() {
        return new PersonalInfoPresenter(getActivity());
    }

    @Override
    protected void initViews(View view) {
        StatusBarUtil.setHeight(getContext(), mToolbar);
        mTitleTv.setText(getString(R.string.personal_Personal_Infomation));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pop();
            }
        });

        UserBean userBean = AppSetting.getUserInfo();
        if (null != userBean) {
            mNameTv.setText(userBean.getFirstName() + " " + userBean.getLastName());
            mPhoneNumberTv.setText(userBean.getPhoneNumber());
            mGenderTv.setText(userBean.getGender());
            try {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new SimpleDateFormat("yyyyMMdd", Locale.CHINA).parse(String.valueOf(userBean.getBirthday())));
                String birthStr = new SimpleDateFormat("dd/MM/yyyy").format(calendar.getTime());
                mBirthdayTv.setText(birthStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            mPostcodeTv.setText(userBean.getPostCode());

//            GlideApp.with(getContext())
//                    .load(userBean.get)
//                    .skipMemoryCache(true)
//                    .placeholder(R.mipmap.icon_avatar)
//                    .into(mPortraitIm);

        }
    }

    @OnClick({R.id.personal_portrait_btn, R.id.personal_name_btn, R.id.personal_phone_number_btn, R.id.personal_gender_btn, R.id.personal_birthday_btn, R.id.personal_postcode_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.personal_portrait_btn:
                BottomMenuDialog.showMenu(getContext());
//                if (dialogView != null) {
//                    mDialog = new Dialog(context, com.bigkoo.pickerview.R.style.custom_dialog2);
//                    mDialog.setCancelable(mPickerOptions.cancelable);//不能点外面取消,也不能点back取消
//                    mDialog.setContentView(dialogView);
//
//                    Window dialogWindow = mDialog.getWindow();
//                    if (dialogWindow != null) {
//                        dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_scale_anim);
//                        dialogWindow.setGravity(Gravity.CENTER);//可以改成Bottom
//                    }
//
//                    mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//                        @Override
//                        public void onDismiss(DialogInterface dialog) {
//                            if (onDismissListener != null) {
//                                onDismissListener.onDismiss(BasePickerView.this);
//                            }
//                        }
//                    });
//                }
                break;
            case R.id.personal_name_btn:
                break;
            case R.id.personal_phone_number_btn:
                break;
            case R.id.personal_gender_btn:
                break;
            case R.id.personal_birthday_btn:
                break;
            case R.id.personal_postcode_btn:
                break;
        }
    }
}
