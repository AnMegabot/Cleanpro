package com.pakpobox.cleanpro.ui.my.personal;


import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.pakpobox.cleanpro.GlideApp;
import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.application.AppSetting;
import com.pakpobox.cleanpro.base.BasePresenterFragment;
import com.pakpobox.cleanpro.bean.ResultBean;
import com.pakpobox.cleanpro.bean.UserBean;
import com.pakpobox.cleanpro.common.Const;
import com.pakpobox.cleanpro.common.UrlConstainer;
import com.pakpobox.cleanpro.ui.mvp.model.impl.BaseModel;
import com.pakpobox.cleanpro.ui.widget.BottomMenuDialog;
import com.pakpobox.cleanpro.ui.widget.GlideCircleTransform;
import com.pakpobox.cleanpro.utils.StatusBarUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 个人信息
 */
public class PersonalInfoFragment extends BasePresenterFragment<PersonalInfoPresenter, PersonalInfoContract.IPersonalInfoView> implements PersonalInfoContract.IPersonalInfoView {
    private static final int RC_CAMERA_PERM = 0x0011;
    private static final int RC_ALBUM_PERM = 0x0012;
    /**
     * 请求相册
     */
    public static final int REQUEST_CODE_GETIMAGE_BYSDCARD = 0;
    /**
     * 请求相机
     */
    public static final int REQUEST_CODE_GETIMAGE_BYCAMERA = 1;

    protected Uri uploadUri = null;//上传照片uri

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

    private TimePickerView birthPickerView;

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
    protected boolean isRegisterEvent() {
        return true;
    }

    @Override
    protected PersonalInfoPresenter createPresenter() {
        return new PersonalInfoPresenter(getActivity());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //判断 activity被销毁后 有没有数据被保存下来
        if (savedInstanceState != null) {
            try {
                if (!TextUtils.isEmpty(savedInstanceState.getString("uploadUri"))) {
                    File out = new File(savedInstanceState.getString("uploadUri"));
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                        uploadUri = FileProvider.getUriForFile(getContext(),"com.pakpobox.storhub_app_universal.fileprovider", out);
                    }else{
                        uploadUri = Uri.fromFile(out);//7.0这里会闪退，imgfile是图片文件路径
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (null != uploadUri)
            outState.putString("uploadUri", uploadUri.getPath());

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
            showUserInfo(userBean);

            GlideApp.with(getContext())
                    .load(new BaseModel().getApiUrl(UrlConstainer.GET_HEAD_IMAGE, userBean.getHeadImageUrl()))
                    .skipMemoryCache(true)
                    .placeholder(R.mipmap.icon_avatar)
                    .transform(new GlideCircleTransform())
                    .into(mPortraitIm);

        }
    }

    private void showUserInfo(UserBean userBean) {
        mNameTv.setText(userBean.getFirstName() + " " + userBean.getLastName());
        mPhoneNumberTv.setText(userBean.getPhoneNumber());
        mGenderTv.setText("MALE".equals(userBean.getGender()) ? "Male" : "Female");
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new SimpleDateFormat("yyyyMMdd", Locale.CHINA).parse(String.valueOf(userBean.getBirthday())));
            String birthStr = new SimpleDateFormat("dd/MM/yyyy").format(calendar.getTime());
            mBirthdayTv.setText(birthStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        mPostcodeTv.setText(userBean.getPostCode());
    }

    @Override
    public void updateProfileSuccess(UserBean userBean) {
        if (null != userBean) {
            showUserInfo(userBean);
        }
    }

    @OnClick({R.id.personal_portrait_btn, R.id.personal_name_btn, R.id.personal_phone_number_btn, R.id.personal_gender_btn, R.id.personal_birthday_btn, R.id.personal_postcode_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.personal_portrait_btn: //头像修改
                BottomMenuDialog.showMenu(getContext(), new BottomMenuDialog.OnMenuClickListener() {
                    @Override
                    public void onFirstBtnClick(View v) {
                        String[] cameraPerms = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE};
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                                    || ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                requestPermissions(cameraPerms, RC_CAMERA_PERM);
                                return;
                            }
                        }
                        startTakePhoto();
                    }

                    @Override
                    public void onSecondBtnClick(View v) {
                        String[] albumPerms = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                requestPermissions(albumPerms, RC_ALBUM_PERM);
                                return;
                            }
                        }
                        startImagePick();
                    }

                    @Override
                    public void onCancelBtnClick(View v) {

                    }
                });
                break;
            case R.id.personal_name_btn:
                break;
            case R.id.personal_phone_number_btn:
                break;
            case R.id.personal_gender_btn:
                BottomMenuDialog.showMenu(getContext(), new BottomMenuDialog.OnMenuClickListener() {
                    @Override
                    public void onFirstBtnClick(View v) {
                        mPresenter.updateGender(Const.GENDER.FEMALE.name());
                    }

                    @Override
                    public void onSecondBtnClick(View v) {
                        mPresenter.updateGender(Const.GENDER.FEMALE.name());
                    }

                    @Override
                    public void onCancelBtnClick(View v) {
                    }
                },"Male", "Female");
                break;
            case R.id.personal_birthday_btn:
                if (null == birthPickerView) {
                    Calendar selectedDate = Calendar.getInstance();//系统当前时间
                    selectedDate.set(1991, 10, 29);
                    Calendar startDate = Calendar.getInstance();
                    startDate.set(1990, 1, 1);
                    Calendar endDate = Calendar.getInstance();
                    birthPickerView = new TimePickerBuilder(getContext(), new OnTimeSelectListener() {
                        @Override
                        public void onTimeSelect(Date date, View v) {//选中事件回调
                            String dateStr = getTime(date);
                            if (!TextUtils.isEmpty(dateStr)) {
                                try {
                                    long birthday = Long.parseLong(dateStr);
                                    mPresenter.updateBirthday(birthday);
                                } catch (NumberFormatException e) {
                                    e.printStackTrace();
                                }
                            }

                        }
                    })
//                            .setCancelText("Cancel")
//                            .setSubmitText("Confirm")
//                            .setSubCalSize(SystemUtils.sp2px(getContext(), 13))
//                            .setTitleBgColor(Color.WHITE)
//                            .setBgColor(getContext().getResources().getColor(R.color.windowBackground))//滚轮背景颜色 Night mode
//                            .setSubmitColor(getContext().getResources().getColor(R.color.colorPrimary))
//                            .setCancelColor(getContext().getResources().getColor(R.color.colorPrimary))
                            .setDividerColor(getContext().getResources().getColor(R.color.time_picker_divider_color))
                            .setTextColorCenter(getContext().getResources().getColor(R.color.colorPrimary))
                            .setTextColorOut(getContext().getResources().getColor(R.color.textColorEditHint))
                            .setRangDate(startDate, endDate)
                            .setDate(selectedDate)
                            .setContentTextSize(18)
                            .setType(new boolean[]{true, true, true, false, false, false})
//                .setLabel("年", "月", "日", "时", "分", "秒")
                            .setLabel("", "", "", "", "", "")
                            .setLineSpacingMultiplier(2.2f)
                            .setTextXOffset(0, 0, 0, 40, 0, -40)
                            .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                            .setLayoutRes(R.layout.pickerview_custom_time, new CustomListener() {
                                @Override
                                public void customLayout(View v) {
                                    final LinearLayout titleLayout = (LinearLayout) v.findViewById(R.id.custom_time_title_layout);
                                    titleLayout.setVisibility(View.VISIBLE);
                                    Button cancelBtn = (Button) v.findViewById(R.id.custom_time_btnCancel);
                                    Button confirmBtn = (Button) v.findViewById(R.id.custom_time_btnSubmit);
                                    confirmBtn.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            birthPickerView.returnData();
                                            birthPickerView.dismiss();
                                        }
                                    });
                                    cancelBtn.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            birthPickerView.dismiss();
                                        }
                                    });
                                }
                            })
//                            .setDecorView(mTimepicker)
                            .isDialog(true)
                            .setOutSideCancelable(true)
                            .build();

                    Dialog mDialog = birthPickerView.getDialog();
                    if (mDialog != null) {
                        FrameLayout.LayoutParams contentParams = new FrameLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT,
                                Gravity.BOTTOM);

                        contentParams.leftMargin = 0;
                        contentParams.rightMargin = 0;
                        birthPickerView.getDialogContainerLayout().setLayoutParams(contentParams);

                        Window window = mDialog.getWindow();
                        if (window != null) {
                            window.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
                            window.setGravity(Gravity.BOTTOM);
                            WindowManager.LayoutParams params = window.getAttributes();
                            params.width = WindowManager.LayoutParams.MATCH_PARENT;
                            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
                            window.setAttributes(params);
                            window.setDimAmount(0.5f);
                        }
                    }
                }


                birthPickerView.show();
                break;
            case R.id.personal_postcode_btn:
                start(UpdatePostcodeFragment.newInstance());
                break;
        }
    }

    //拍照
    private void startTakePhoto() {
        // 判断是否挂载了SD卡
        String savePath = "";
        String storageState = Environment.getExternalStorageState();
        if (storageState.equals(Environment.MEDIA_MOUNTED)) {
            savePath = Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + "/StorHub/Camera/";
            File savedir = new File(savePath);
            if (!savedir.exists()) {
                savedir.mkdirs();
            }
        }

        // 没有挂载SD卡，无法保存文件
        if (TextUtils.isEmpty(savePath)) {
//            Toast.makeText(getContext(), "无法保存照片，请检查SD卡是否挂载", Toast.LENGTH_LONG).show();
            return;
        }

        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss")
                .format(new Date());
        String fileName = "upload_" + timeStamp + ".jpg";// 照片命名
        File out = new File(savePath, fileName);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri uri;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            uri = FileProvider.getUriForFile(getContext(),"com.pakpobox.storhub_app_universal.fileprovider", out);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION );//添加这一句表示对目标应用临时授权该Uri所代表的文件
        }else{
            uri = Uri.fromFile(out);//7.0这里会闪退，imgfile是图片文件路径
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        }
        startActivityForResult(intent, REQUEST_CODE_GETIMAGE_BYCAMERA);

        uploadUri = uri;
    }

    //选择图片
    private void startImagePick() {
        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, getString(R.string.photo_open_album)), REQUEST_CODE_GETIMAGE_BYSDCARD);
        } else {
            intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, getString(R.string.photo_open_album)), REQUEST_CODE_GETIMAGE_BYSDCARD);
        }
    }

    @Override
    public void uploadHeadImageSuccess(ResultBean result) {
        if (null == result)
            return;

        UserBean userBean = AppSetting.getUserInfo();
        if (null != userBean) {
            userBean.setHeadImageUrl(result.getResult());
            AppSetting.saveUserInfo(userBean);
            EventBus.getDefault().post(userBean);
        }
        GlideApp.with(getContext())
                .load(new BaseModel().getApiUrl(UrlConstainer.GET_HEAD_IMAGE, result.getResult()))
                .skipMemoryCache(true)
                .placeholder(R.mipmap.icon_avatar)
                .transform(new GlideCircleTransform())
                .into(mPortraitIm);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnIntent) {
        if (resultCode != Activity.RESULT_OK)
            return;

        switch (requestCode) {
            case REQUEST_CODE_GETIMAGE_BYCAMERA:
                break;
            case REQUEST_CODE_GETIMAGE_BYSDCARD:
                uploadUri = imageReturnIntent.getData();
                break;
        }

        if (null != uploadUri) {
            mPresenter.uploadHeadImage(getRealFilePath(getContext(), uploadUri ));
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RC_CAMERA_PERM:
                if (grantResults != null && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    startTakePhoto();
                }
                break;
            case RC_ALBUM_PERM:
                if (grantResults != null && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startImagePick();
                }
                break;
        }

    }

    public String getRealFilePath( final Context context, final Uri uri ) {
        if ( null == uri ) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if ( scheme == null )
            data = uri.getPath();
        else if ( ContentResolver.SCHEME_FILE.equals( scheme ) ) {
            data = uri.getPath();
        } else if ( ContentResolver.SCHEME_CONTENT.equals( scheme ) ) {
            Cursor cursor = context.getContentResolver().query( uri, new String[] { MediaStore.Images.ImageColumns.DATA }, null, null, null );
            if ( null != cursor ) {
                if ( cursor.moveToFirst() ) {
                    int index = cursor.getColumnIndex( MediaStore.Images.ImageColumns.DATA );
                    if ( index > -1 ) {
                        data = cursor.getString( index );
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        return format.format(date);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginEvent(UserBean event) {
        showUserInfo(event);
    }
}
