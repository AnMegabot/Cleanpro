package com.pakpobox.cleanpro.ui.logon.register;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnTimeSelectChangeListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.base.BaseFragment;
import com.pakpobox.cleanpro.bean.Register;
import com.pakpobox.cleanpro.utils.StatusBarUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 注册-3
 */
public class RegisterBirthdayFragment extends BaseFragment {


    @BindView(R.id.app_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.register_birthday_timepicker)
    LinearLayout mTimepicker;

    private Register mRegister;

    public static RegisterBirthdayFragment newInstance(Register register) {
        Bundle args = new Bundle();
        args.putParcelable("register", register);
        RegisterBirthdayFragment fragment = new RegisterBirthdayFragment();
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

        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        selectedDate.set(1991, 10, 29);
        Calendar startDate = Calendar.getInstance();
        startDate.set(1990, 1, 1);
        Calendar endDate = Calendar.getInstance();
//        endDate.set(2027, 2, 28);
        //时间选择器 ，自定义布局
        TimePickerView pvCustomTime = new TimePickerBuilder(getContext(), new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
//                btn_CustomTime.setText(getTime(date));
            }
        })
//                .setType(TimePickerView.Type.ALL)//default is all
//                .setCancelText("Cancel")
//                .setSubmitText("Sure")
//                .setContentTextSize(18)
//                .setTitleSize(20)
//                .setTitleText("Title")
//                .setTitleColor(Color.BLACK)
//                .setDividerColor(Color.WHITE)//设置分割线的颜色
//                .setTextColorCenter(Color.LTGRAY)//设置选中项的颜色
//                .setLineSpacingMultiplier(1.6f)//设置两横线之间的间隔倍数
//                .setTitleBgColor(Color.DKGRAY)//标题背景颜色 Night mode
                .setBgColor(getContext().getResources().getColor(R.color.windowBackground))//滚轮背景颜色 Night mode
//                .setSubmitColor(Color.WHITE)
//                .setCancelColor(Color.WHITE)
//                .animGravity(Gravity.RIGHT)// default is center
                .setDividerColor(getContext().getResources().getColor(R.color.time_picker_divider_color))
                .setTextColorCenter(getContext().getResources().getColor(R.color.colorPrimary))
                .setTextColorOut(getContext().getResources().getColor(R.color.textColorEditHint))
                .setRangDate(startDate, endDate)
                .setDate(selectedDate)
                .setLayoutRes(R.layout.pickerview_custom_time, new CustomListener() {

                    @Override
                    public void customLayout(View v) {
//                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
//                        ImageView ivCancel = (ImageView) v.findViewById(R.id.iv_cancel);
//                        tvSubmit.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                pvCustomTime.returnData();
//                                pvCustomTime.dismiss();
//                            }
//                        });
//                        ivCancel.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                pvCustomTime.dismiss();
//                            }
//                        });
                    }
                })
                .setContentTextSize(18)
                .setType(new boolean[]{true, true, true, false, false, false})
//                .setLabel("年", "月", "日", "时", "分", "秒")
                .setLabel("", "", "", "", "", "")
                .setLineSpacingMultiplier(2.2f)
                .setTextXOffset(0, 0, 0, 40, 0, -40)
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
//                .setDividerColor(0xFF24AD9D)
                .setDecorView(mTimepicker)
                .setOutSideCancelable(false)
                .setTimeSelectChangeListener(new OnTimeSelectChangeListener() {
                    @Override
                    public void onTimeSelectChanged(Date date) {
                        if (null != mRegister) {
                            String dateStr = getTime(date);
                            if (!TextUtils.isEmpty(dateStr)) {
                                try {
                                    long birthday = Long.parseLong(dateStr);
                                    mRegister.setBirthday(birthday);
                                } catch (NumberFormatException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                })
                .build();

        pvCustomTime.show();
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        return format.format(date);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_register_birthday;
    }

    @OnClick(R.id.register_birthday_next_btn)
    public void onClick() {
        start(RegisterGenterFragment.newInstance(mRegister));
    }
}
