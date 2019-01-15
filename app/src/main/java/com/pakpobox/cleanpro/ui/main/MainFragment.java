package com.pakpobox.cleanpro.ui.main;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.application.AppSetting;
import com.pakpobox.cleanpro.base.BaseFragment;
import com.pakpobox.cleanpro.common.Const;
import com.pakpobox.cleanpro.ui.booking.preference.SelectPreferenceFragment;
import com.pakpobox.cleanpro.ui.home.HomeFragment;
import com.pakpobox.cleanpro.ui.location.LocationFragment;
import com.pakpobox.cleanpro.ui.logon.LoginActivity;
import com.pakpobox.cleanpro.ui.my.MyFragment;
import com.pakpobox.cleanpro.ui.order.OrdersFragment;
import com.pakpobox.cleanpro.ui.scanner.QRCodeScanActivity;
import com.pakpobox.cleanpro.utils.AesEncryptionUtils;
import com.pakpobox.cleanpro.utils.ToastUtils;
import com.pakpobox.logger.Logger;

import javax.crypto.spec.SecretKeySpec;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * 主页面跟Fragment
 */
public class MainFragment extends BaseFragment {
    public static final int SCAN_REQUEST_CODE = 0x0011;
    public static final int LOGIN_REQUEST_CODE = 0x0012;
    private final String[] cameraPerms = {Manifest.permission.CAMERA};
    private static final int RC_CAMERA_PERM = 0x0101;

    @BindView(R.id.main_bnv)
    RadioGroup mBottomNavigation;

    private SupportFragment[] mFragments = new SupportFragment[4];
    private int mCurrentItem = 0;

    public static MainFragment newInstance() {
        Bundle args = new Bundle();
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initViews(View view) {
    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        SupportFragment homeFragment = findFragment(HomeFragment.class);
        if (homeFragment == null) {
            mFragments[0] = HomeFragment.newInstance();
            mFragments[1] = LocationFragment.newInstance();
            mFragments[2] = OrdersFragment.newInstance();
            mFragments[3] = MyFragment.newInstance();

            loadMultipleRootFragment(R.id.main_container, 0, mFragments);
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题

            // 这里我们需要拿到mFragments的引用
            mFragments[0] = homeFragment;
            mFragments[1] = findFragment(LocationFragment.class);
            mFragments[2] = findFragment(OrdersFragment.class);
            mFragments[3] = findFragment(MyFragment.class);
        }

        initView();
    }

    private void initView() {
        mBottomNavigation.check(R.id.main_bottom_nav_home);
        mBottomNavigation.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int currentItem;
                switch (i) {
                    case R.id.main_bottom_nav_home:
                        currentItem = 0;
                        break;
                    case R.id.main_bottom_nav_nearby:
                        currentItem = 1;
                        break;
                    case R.id.main_bottom_nav_orders:
                        currentItem = 2;
                        break;
                    case R.id.main_bottom_nav_my:
                        currentItem = 3;
                        break;
                    default:
                        currentItem = 0;
                }
                showHideFragment(mFragments[currentItem], mFragments[mCurrentItem]);
                mCurrentItem = currentItem;
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }

    @OnClick(R.id.main_scan_btn)
    public void onClick(View view) {
        if (!AppSetting.isLogin()) {
            getActivity().startActivityForResult(new Intent(getContext(), LoginActivity.class), LOGIN_REQUEST_CODE);
            setClickingView(view);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(cameraPerms, RC_CAMERA_PERM);
                    return;
                }
            }
            startActivityForResult(new Intent(getContext(), QRCodeScanActivity.class), SCAN_REQUEST_CODE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK)
            return;

        switch (requestCode) {
            case SCAN_REQUEST_CODE:
                if (null != data) {
                    String scannerStr = data.getStringExtra("scan_result");
                    if (!TextUtils.isEmpty(scannerStr)) {
                        SecretKeySpec keySpec = AesEncryptionUtils.getSecretKey(Const.QRCODE_AES_KEY.getBytes());
                        String decryptScanStr = AesEncryptionUtils.decrypt(keySpec, scannerStr);
                        Logger.d("解码后：" + decryptScanStr);
                        if (!TextUtils.isEmpty(decryptScanStr)) {
                            String[] actionData = decryptScanStr.split("#");
                            if (null != actionData && actionData.length >= 3) {
                                if (getParentFragment() instanceof MainFragment) {
                                    ((MainFragment) getParentFragment()).start(SelectPreferenceFragment.newInstance(actionData[0], actionData[1], actionData[2]));
                                    return;
                                }
                            }
                        }

                    }
                }
                ToastUtils.showToast(getContext(), R.string.app_unknown_error);

                break;
            case LOGIN_REQUEST_CODE:
                clickClickingView();
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RC_CAMERA_PERM:
                if (grantResults != null && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startActivityForResult(new Intent(getContext(), QRCodeScanActivity.class), SCAN_REQUEST_CODE);
                }
                break;
        }

    }
}
