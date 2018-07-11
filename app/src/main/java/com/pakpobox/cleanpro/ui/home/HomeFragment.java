package com.pakpobox.cleanpro.ui.home;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.base.BaseFragment;
import com.pakpobox.cleanpro.model.AppSetting;
import com.pakpobox.cleanpro.ui.booking.SelectPreferenceFragment;
import com.pakpobox.cleanpro.ui.login.LoginActivity;
import com.pakpobox.cleanpro.ui.main.MainFragment;
import com.pakpobox.cleanpro.ui.scanner.QRCodeScanActivity;
import com.pakpobox.cleanpro.ui.widget.tabbar.UIUtils;
import com.pakpobox.cleanpro.utils.StatusBarUtil;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;
import com.youth.banner.loader.ImageLoaderInterface;
import com.zhy.magicviewpager.transformer.AlphaPageTransformer;
import com.zhy.magicviewpager.transformer.RotateDownPageTransformer;
import com.zhy.magicviewpager.transformer.ScaleInTransformer;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 首页
 */
public class HomeFragment extends BaseFragment {
    public static final int LAUNDRY_SCAN_REQUEST_CODE = 0x0011;
    public static final int DRYER_SCAN_REQUEST_CODE = 0x0012;
    public static final int LOGIN_REQUEST_CODE = 0x0013;

    private final String[] cameraPerms = {Manifest.permission.CAMERA};
    private static final int RC_CAMERA_PERM = 0x0101;

    @BindView(R.id.home_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.home_banner)
    Banner homeBanner;
    @BindView(R.id.comment_banner)
    ViewPager commentBanner;

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initViews(View view) {
        StatusBarUtil.setHeight(getContext(), mToolbar);
        initBanner();
        initCommentBanner();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @OnClick({R.id.home_laundry_btn, R.id.home_dryer_btn, R.id.home_service_btn, R.id.home_location_btn, R.id.home_price_btn, R.id.home_promotion_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home_laundry_btn:
//                if (!AppSetting.getInstance().isHasLogin()) {
//                    getActivity().startActivityForResult(new Intent(getContext(), LoginActivity.class), LOGIN_REQUEST_CODE);
//                    setClickingView(view);
//                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            requestPermissions(cameraPerms, RC_CAMERA_PERM);
                            return;
                        }
                    }
                    startActivityForResult(new Intent(getContext(), QRCodeScanActivity.class), LAUNDRY_SCAN_REQUEST_CODE);
//                }
                break;
            case R.id.home_dryer_btn:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(cameraPerms, RC_CAMERA_PERM);
                        return;
                    }
                }
                startActivityForResult(new Intent(getContext(), QRCodeScanActivity.class), DRYER_SCAN_REQUEST_CODE);
                break;
            case R.id.home_service_btn:
                break;
            case R.id.home_location_btn:
                break;
            case R.id.home_price_btn:
                break;
            case R.id.home_promotion_btn:
                break;
        }
    }

    private void initBanner() {
        //设置banner样式
        homeBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        homeBanner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        Integer[] sh_images = {R.mipmap.hp_banner, R.mipmap.hp_banner, R.mipmap.hp_banner};
        homeBanner.setImages(Arrays.asList(sh_images));
        //设置banner动画效果
//        homeBanner.setBannerAnimation(Transformer.ForegroundToBackground);
        //设置标题集合（当banner样式有显示title时）
//        homeBanner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        homeBanner.isAutoPlay(true);
        //设置轮播时间
        homeBanner.setDelayTime(3000);
        //设置指示器位置（当banner模式中有指示器时）
        homeBanner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置点击监听
        homeBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {

            }
        });
        //banner设置方法全部调用完毕时最后调用
        homeBanner.start();
    }

    private void initCommentBanner() {
        commentBanner.setPageMargin(UIUtils.dip2Px(getContext(), 28));//设置page间间距，自行根据需求设置
        commentBanner.setOffscreenPageLimit(3);//>=3
        commentBanner.setAdapter(mCommentAdapter);

        //setPageTransformer 决定动画效果
        commentBanner.setPageTransformer(true, new AlphaPageTransformer(new ScaleInTransformer()));
    }

    @Override
    public void onStart() {
        super.onStart();
        //开始轮播
        homeBanner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        //结束轮播
        homeBanner.stopAutoPlay();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK)
            return;

        switch (requestCode) {
            case LAUNDRY_SCAN_REQUEST_CODE:
                if (getParentFragment() instanceof MainFragment) {
                    ((MainFragment) getParentFragment()).start(SelectPreferenceFragment.newInstance(LAUNDRY_SCAN_REQUEST_CODE));
                }
                break;
            case DRYER_SCAN_REQUEST_CODE:
                if (getParentFragment() instanceof MainFragment) {
                    ((MainFragment) getParentFragment()).start(SelectPreferenceFragment.newInstance(DRYER_SCAN_REQUEST_CODE));
                }
                break;
            case LOGIN_REQUEST_CODE:
                clickClickingView();
                break;
        }
    }

    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            /**
             注意：
             1.图片加载器由自己选择，这里不限制，只是提供几种使用方法
             2.返回的图片路径为Object类型，由于不能确定你到底使用的那种图片加载器，
             传输的到的是什么格式，那么这种就使用Object接收和返回，你只需要强转成你传输的类型就行，
             切记不要胡乱强转！
             */

            //Glide 加载图片简单用法
//            Glide.with(context).load(path).into(imageView);
            Glide.with(context).load(context.getResources().getDrawable((int)path)).into(imageView);

            //Picasso 加载图片简单用法
//            Picasso.with(context).load(path).into(imageView);

            //用fresco加载图片简单用法，记得要写下面的createImageView方法
//            Uri uri = Uri.parse((String) path);
//            imageView.setImageURI(uri);
        }

        //提供createImageView 方法，如果不用可以不重写这个方法，主要是方便自定义ImageView的创建
        @Override
        public ImageView createImageView(Context context) {
            //使用fresco，需要创建它提供的ImageView，当然你也可以用自己自定义的具有图片加载功能的ImageView
//            SimpleDraweeView simpleDraweeView=new SimpleDraweeView(context);
//            return simpleDraweeView;
            return super.createImageView(context);
        }
    }

    private PagerAdapter mCommentAdapter = new PagerAdapter() {
        private Integer[] sh_images = {R.mipmap.hp_banner, R.mipmap.hp_banner, R.mipmap.hp_banner};
        @Override
        public Object instantiateItem(ViewGroup container, int position)
        {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_comment_banner, commentBanner, false);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object)
        {
            container.removeView((View) object);
        }

        @Override
        public int getCount()
        {
            return sh_images.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object o)
        {
            return view == o;
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RC_CAMERA_PERM:
                if (grantResults != null && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startActivityForResult(new Intent(getContext(), QRCodeScanActivity.class), LAUNDRY_SCAN_REQUEST_CODE);
                }
                break;
        }

    }
}
